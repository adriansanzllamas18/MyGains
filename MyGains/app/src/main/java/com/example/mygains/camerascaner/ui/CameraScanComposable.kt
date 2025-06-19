package com.example.mygains.camerascaner.ui

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.mygains.scanproducts.ui.ScanBarCodeViewModel
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


@Composable
fun CameraScanComposable(name: NavController) {

    var cameraScanViewModel:ScanBarCodeViewModel = hiltViewModel()

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

    LaunchedEffect(hasCameraPermission) {
        cameraScanViewModel.showScanIcon(true)
    }

    if (hasCameraPermission) {
       //todo
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
        }
    }
}