package com.example.mygains.scanproducts.ui

import android.Manifest
import android.content.ClipData.Item
import android.content.pm.PackageManager
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.content.ContextCompat
import com.example.mygains.userinfo.CameraPreview
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@Preview(showBackground = true)
@Composable
fun ScanProductComposable() {
    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .windowInsetsPadding(WindowInsets.systemBars)) {

        item {
            MyScanHeader(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp))
        }

        item{
            MyTitleInfo(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp))
            MyScaner(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .size(400.dp))
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
fun MyScaner(modifier: Modifier) {

    Box(modifier) {
        getCameraPermissions()
    }
}

@Composable
private fun getCameraPermissions(){
    val context = LocalContext.current
    var hasCameraPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
        )
    }

    val cameraExecutor: ExecutorService = remember { Executors.newSingleThreadExecutor() }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted ->
            hasCameraPermission = granted
        }
    )

    LaunchedEffect(Unit) {
        if (!hasCameraPermission) {
            launcher.launch(Manifest.permission.CAMERA)
        }
    }

    if (hasCameraPermission) {
        CameraPreview(cameraExecutor)
    } else {
       BarCodeComposable()
    }
    
}

@Composable
fun MyScanHeader(modifier: Modifier) {


    Row(modifier= modifier) {
        ConstraintLayout(Modifier.fillMaxWidth()) {
            val (close,title) = createRefs()

            Icon(imageVector =  Icons.Filled.Close, contentDescription ="atras",
                Modifier
                    .constrainAs(close) {
                        start.linkTo(parent.start)
                        bottom.linkTo(parent.bottom)
                        top.linkTo(parent.top)
                    }
                    .clickable { }
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
