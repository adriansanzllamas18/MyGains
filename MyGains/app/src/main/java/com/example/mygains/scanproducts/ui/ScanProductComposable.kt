package com.example.mygains.scanproducts.ui

import android.Manifest
import android.app.Activity
import android.content.ClipData.Item
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.captionBarPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mygains.R
import com.example.mygains.scanproducts.data.ProductResponse
import com.example.mygains.userinfo.CameraPreview
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@Preview(showBackground = true)
@Composable
fun ScanProductComposable() {

    var viewModel:ScanBarCodeViewModel = hiltViewModel()
    val scanIcon by viewModel._ShowScanIconLife.observeAsState(initial = false)

    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .windowInsetsPadding(WindowInsets.systemBars)) {

        item {
            MyScanHeader(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),viewModel,scanIcon)
        }

        item{
            MyTitleInfo(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp))
            MyScaner(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),viewModel,scanIcon)
        }

        item {
            ModalProductComposable(viewModel)
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModalProductComposable(viewModel: ScanBarCodeViewModel) {
    val product by viewModel._ProductResponseLife.observeAsState(initial = null)
    val showProduct by viewModel._ShowProductLife.observeAsState(initial = false)

    if (showProduct){
        ModalBottomSheet(onDismissRequest = { viewModel.showProduct(false)}, containerColor =Color.White) {
            product?.let { ProductInfoBottomSheetComposable(it) }
        }
    }
}

@Composable
fun MyTitleInfo(modifier: Modifier) {
    Column(modifier) {
        Text(text = "Scanea el codigo de barras del producto",
            modifier = Modifier.fillMaxWidth(),
            lineHeight = 32.sp,
            fontSize = 32.sp,
            textAlign = TextAlign.Center,
            )

        Text(text = "Coloca el codigo de barras del producto entre el recuadro.",
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            color = Color.Gray)
    }
}

@Composable
fun MyScaner(modifier: Modifier,viewModel: ScanBarCodeViewModel,scanIcon: Boolean) {

    Box(modifier) {
        getCameraPermissions(viewModel,scanIcon)
    }
}

@Composable
private fun getCameraPermissions(viewModel: ScanBarCodeViewModel,scanIcon: Boolean) {

    val context = LocalContext.current
    var hasCameraPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
        )
    }

    val shouldShowRationale = remember {
        ActivityCompat.shouldShowRequestPermissionRationale(context as Activity, Manifest.permission.CAMERA)
    }

    val cameraExecutor: ExecutorService = remember { Executors.newSingleThreadExecutor() }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted ->
            hasCameraPermission = granted
        }
    )

    LaunchedEffect(Unit) {
        if (!hasCameraPermission && !scanIcon) {
            launcher.launch(Manifest.permission.CAMERA)
        }
    }
    LaunchedEffect(hasCameraPermission) {
        viewModel.showScanIcon(true)
    }

    if (hasCameraPermission) {
        if (scanIcon){
            CameraPreview(cameraExecutor, viewModel = viewModel)
        }else{
            BarCodeComposable(viewModel)
        }

    } else {
        if (shouldShowRationale) {
            AlertDialog(
                onDismissRequest = { /* Close dialog if dismissed */ },
                title = { Text("Permiso Necesario") },
                text = { Text("La cámara es necesaria para escanear códigos de barras.") },
                confirmButton = {
                    TextButton(onClick = { launcher.launch(Manifest.permission.CAMERA) }) {
                        Text("Permitir")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { }) {
                        Text("Cancelar")
                    }
                }
            )
        } else if (scanIcon) {
            launcher.launch(Manifest.permission.CAMERA)
        } else {
            BarCodeComposable(viewModel)
        }
    }
}


@Composable
fun MyScanHeader(modifier: Modifier,viewModel: ScanBarCodeViewModel, scanIcon:Boolean) {

    Row(modifier= modifier) {
        ConstraintLayout(Modifier.fillMaxWidth()) {
            val (close,title,scan) = createRefs()

            Icon(imageVector =  Icons.Filled.Close, contentDescription ="atras",
                Modifier
                    .constrainAs(close) {
                        start.linkTo(parent.start)
                        bottom.linkTo(parent.bottom)
                        top.linkTo(parent.top)
                    }
                    .clickable { }
            )


            Icon(painter = if (scanIcon)painterResource(id = R.drawable.scanner_bar_code)else{painterResource(id = R.drawable.keyboard)}
                , contentDescription ="atras",
                Modifier
                    .constrainAs(scan) {
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                        top.linkTo(parent.top)
                    }
                    .size(24.dp)
                    .clickable {
                        viewModel.showScanIcon(!scanIcon)
                    }
            )
            Text(text = "GainsScaner", modifier = Modifier.constrainAs(title){
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
                top.linkTo(parent.top)
            },
                fontSize = 18.sp, fontWeight = FontWeight.Bold
            )

        }

    }
}
