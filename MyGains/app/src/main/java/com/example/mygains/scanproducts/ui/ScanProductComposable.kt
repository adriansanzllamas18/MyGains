package com.example.mygains.scanproducts.ui

import android.Manifest
import android.app.Activity
import android.content.ClipData.Item
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageShader
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
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
import androidx.navigation.NavController
import com.example.mygains.R
import com.example.mygains.camerascaner.ui.CameraPreview
import com.example.mygains.extras.navigationroutes.Routes
import com.example.mygains.scanproducts.data.ProductResponse
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@Composable
fun ScanProductComposable(navController: NavController) {

    var viewModel:ScanBarCodeViewModel = hiltViewModel()
    MyScaner(navController = navController, viewModel =viewModel )
}

@Composable
fun MyScaner(navController: NavController,viewModel: ScanBarCodeViewModel) {

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "Escanee el código de barras del alimento o ingrese el número de codigo de barras a continuación.",
            textAlign = TextAlign.Center,
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(R.font.montserratregular))
        )
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            onClick = { navController.navigate(Routes.CameraScaner.routes) },
            colors = ButtonColors(containerColor = Color.Black,
                contentColor = Color.White,
                disabledContentColor = Color.White,
                disabledContainerColor = Color.LightGray)
        ) {
            Row (
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            )
            {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(id = R.drawable.scanner_bar_code),
                    contentDescription = "scaner icon"
                )
                Text(
                    modifier = Modifier.fillMaxWidth().weight(1f),
                    text = "Usar cámara",
                    textAlign = TextAlign.End,
                    fontFamily = FontFamily(Font( R.font.montserratbold))
                )
            }
        }

        BarCodeComposable(viewModel = viewModel, navController = navController)
    }
}
