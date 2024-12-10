package com.example.mygains.userinfo
import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.OptIn
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.mygains.R
import com.example.mygains.scanproducts.ui.ScanBarCodeViewModel
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


@OptIn(ExperimentalGetImage::class)
@Composable
fun CameraPreview(cameraExecutor: ExecutorService,viewModel: ScanBarCodeViewModel) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }
    var code= remember {
        mutableStateOf("")
    }


    Box() {
        AndroidView(
            modifier = Modifier
                .size(400.dp)
                .fillMaxWidth(),
            factory = { ctx ->
                val previewView = PreviewView(ctx) // Aquí aseguramos que PreviewView se detecta
                cameraProviderFuture.addListener({
                    val cameraProvider = cameraProviderFuture.get()

                    val preview = Preview.Builder().build().also {
                        it.setSurfaceProvider(previewView.surfaceProvider)
                    }

                    val imageAnalysis = ImageAnalysis.Builder()
                        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                        .build()

                    imageAnalysis.setAnalyzer(cameraExecutor, { imageProxy ->
                        // Analizar la imagen usando ML Kit


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
                                    // Aquí puedes procesar los códigos de barras detectados
                                    for (barcode in barcodes) {
                                        val valueType = barcode.valueType
                                        if (valueType == Barcode.TYPE_PRODUCT) {
                                            val productId = barcode.displayValue
                                            Log.d("Barcode", "Producto EAN: $productId")
                                            if (productId != null) {
                                                code.value = productId
                                                viewModel.getProduct(productId)
                                                imageProxy.close()
                                            }
                                        }
                                    }
                                }
                                .addOnFailureListener {
                                    Log.e(
                                        "BarcodeScanner",
                                        "Error al escanear el código de barras: ${it.message}"
                                    )
                                }
                                .addOnCompleteListener {
                                    imageProxy.close()
                                }
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
                .size(200.dp).align(Alignment.Center)
        )


    }
}

