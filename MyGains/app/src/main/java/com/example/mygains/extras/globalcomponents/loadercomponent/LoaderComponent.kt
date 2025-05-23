package com.example.mygains.extras.globalcomponents.loadercomponent

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.mygains.R


@Preview(showBackground = true)
@Composable
fun LoaderComponent() {


    // Carga la composición del recurso Lottie
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loaderanimation))

    var isPlaying by remember {
        mutableStateOf(true)
    }
    // Animación con progreso controlado
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever,
        isPlaying = isPlaying // Añadido el control de reproducción
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ){
        // Renderiza la animación
        LottieAnimation(
            composition = composition,
            progress = progress,
            modifier = Modifier
                .padding(8.dp)
                .size(300.dp)
        )
    }
}