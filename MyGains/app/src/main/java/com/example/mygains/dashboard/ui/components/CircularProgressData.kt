package com.example.mygains.dashboard.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mygains.R
import com.example.mygains.extras.utils.FormatterUtils


@Composable
fun CircularProgressData(goal:Int, current:Int, type:String){

    val targetProgress = current.toFloat() / goal.toFloat() // Calcula el progreso como un porcentaje (0.0f a 1.0f)

    // Anima el progreso usando `animateFloatAsState`
    val animatedProgress by animateFloatAsState(
        targetValue = targetProgress.coerceIn(0f, 1f), // Asegura que esté entre 0 y 1
        animationSpec = tween(
            durationMillis = 1500,
            easing = FastOutSlowInEasing
        ), label = ""
    )

    Column(
        modifier =Modifier.padding(8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally)
    {

        Box(modifier = Modifier.size(60.dp), contentAlignment = Alignment.Center) {
            Canvas(modifier = Modifier.size(60.dp)) {
                val strokeWidth = 8.dp.toPx() // Ancho del círculo y arco
                val radius = size.minDimension / 2 - strokeWidth / 2 // Calcula el radio

                // Fondo del círculo (track)
                drawCircle(
                    color = Color.LightGray,
                    radius = radius,
                    style = Stroke(width = strokeWidth)
                )

                // Arco de progreso (progress arc)
                drawArc(
                    color = Color(0xFFCA5300),
                    startAngle = -90f, // Inicia desde la parte superior del círculo
                    sweepAngle = animatedProgress * 360, // Multiplica el progreso por 360 para obtener el ángulo
                    useCenter = false,
                    style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                )

            }
            Image(
                modifier = Modifier.size(20.dp),
                painter = painterResource(id =FormatterUtils().getImageCardByType(type)),
                contentDescription = "icon" )
        }
        Text(
            modifier = Modifier.padding(top = 16.dp),
            text = "$current/$goal",
            color = Color.Black,
            fontSize = 12.sp,
            textAlign = TextAlign.Center
        )
    }

}