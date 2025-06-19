package com.example.mygains.camerascaner.ui
import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.OptIn
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.mygains.R
import com.example.mygains.extras.navigationroutes.Routes
import com.example.mygains.scanproducts.ui.ScanBarCodeViewModel
import com.google.common.util.concurrent.ListenableFuture
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


@OptIn(ExperimentalGetImage::class)
@Composable
fun CameraPreview(nav: NavHostController) {

    var cameraScanViewModel:ScanBarCodeViewModel = hiltViewModel()

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }

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
        if (!hasCameraPermission) {
            launcher.launch(Manifest.permission.CAMERA)
        }
    }

    if (hasCameraPermission) {
        CameraExecutorComponentSection(cameraExecutor = cameraExecutor,cameraProviderFuture,lifecycleOwner, nav = nav)
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

@OptIn(ExperimentalGetImage::class)
@Composable
private fun CameraExecutorComponentSection(
    cameraExecutor: ExecutorService,
    cameraProviderFuture: ListenableFuture<ProcessCameraProvider>,
    lifecycleOwner: LifecycleOwner,
    nav: NavHostController
) {

    val code = remember {
        mutableStateOf("")
    }

    Box(Modifier.fillMaxSize()) {
        // Variable para controlar si ya se ha navegado
        var hasNavigated by remember { mutableStateOf(false) }

        AndroidView(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            factory = { ctx ->
                val previewView = PreviewView(ctx)
                cameraProviderFuture.addListener({
                    val cameraProvider = cameraProviderFuture.get()

                    val preview = Preview.Builder().build().also {
                        it.setSurfaceProvider(previewView.surfaceProvider)
                    }

                    val imageAnalysis = ImageAnalysis.Builder()
                        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                        .build()

                    imageAnalysis.setAnalyzer(cameraExecutor, { imageProxy ->
                        // ✅ Verificar si ya se navegó ANTES de procesar
                        if (hasNavigated) {
                            imageProxy.close()
                            return@setAnalyzer
                        }

                        val mediaImage = imageProxy.image
                        val image = mediaImage?.let {
                            InputImage.fromMediaImage(
                                it,
                                imageProxy.imageInfo.rotationDegrees
                            )
                        }

                        val scanner: BarcodeScanner = BarcodeScanning.getClient()
                        if (image != null) {
                            scanner.process(image)
                                .addOnSuccessListener { barcodes ->
                                    // ✅ Verificar de nuevo antes de navegar
                                    if (!hasNavigated) {
                                        for (barcode in barcodes) {
                                            val valueType = barcode.valueType
                                            if (valueType == Barcode.TYPE_PRODUCT) {
                                                val productId = barcode.displayValue
                                                if (productId != null) {
                                                    // ✅ Marcar como navegado ANTES de navegar
                                                    hasNavigated = true
                                                    nav.navigate(
                                                        Routes.FoodDeatil.createRout(
                                                            productId
                                                        )
                                                    )
                                                    break // Salir del bucle
                                                }
                                            }
                                        }
                                    }
                                }
                                .addOnFailureListener {
                                    Log.e("BarcodeScanner", "Error al escanear: ${it.message}")
                                }
                                .addOnCompleteListener {
                                    imageProxy.close()
                                }
                        } else {
                            imageProxy.close()
                        }
                    })

                    val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
                    try {
                        cameraProvider.unbindAll()
                        cameraProvider.bindToLifecycle(
                            lifecycleOwner, cameraSelector, preview, imageAnalysis
                        )
                    } catch (exc: Exception) {
                        Log.e("CameraPreview", "Error al abrir la cámara", exc)
                    }
                }, ContextCompat.getMainExecutor(ctx))

                previewView
            }
        )

        // Carga la composición del recurso Lottie
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.scananimation))

        var isPlaying by remember {
            mutableStateOf(true)
        }
        // Animación con progreso controlado
        val progress by animateLottieCompositionAsState(
            composition = composition,
            iterations = LottieConstants.IterateForever,
            isPlaying = isPlaying // Añadido el control de reproducción
        )

        LottieAnimation(
            composition = composition,
            progress = progress,
            modifier = Modifier
                .size(200.dp)
                .align(Alignment.Center)
        )
    }
}

