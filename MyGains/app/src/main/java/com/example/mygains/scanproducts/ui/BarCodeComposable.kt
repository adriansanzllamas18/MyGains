package com.example.mygains.scanproducts.ui

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mygains.R
import com.example.mygains.extras.navigationroutes.Routes


@Composable
fun BarCodeComposable(viewModel: ScanBarCodeViewModel, navController: NavController){

    val infiniteTransition = rememberInfiniteTransition(label = "AnimatedGradient")

    val color1 by infiniteTransition.animateColor(
        initialValue = Color(0xFFFCE5D8),
        targetValue = colorResource(id = R.color.blue_icon),
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 3000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ), label = "Color1"
    )

    val color2 by infiniteTransition.animateColor(
        initialValue = Color(0xFFFCE5D8),
        targetValue = colorResource(id = R.color.purple_200),
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 5000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ), label = "Color2"
    )

    val animatedBrush = Brush.linearGradient(
        colors = listOf(color1, color2),
        start = Offset.Zero,
        end = Offset.Infinite
    )

    var barcode by remember {
        mutableStateOf("")
    }


    Box(Modifier.fillMaxSize()) {
        Card(
            Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(animatedBrush),
            colors = CardDefaults.cardColors(containerColor = Color.Transparent)) {
            Column( modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp)) {
                Image(painter = painterResource(id = R.drawable.barcode), contentDescription ="barcode",
                    modifier = Modifier.align(Alignment.CenterHorizontally))

                TextField(
                    value = barcode,
                    onValueChange = {  if (it.length<=13) { barcode = it} },
                    modifier = Modifier
                        .padding(horizontal = 16.dp),
                    maxLines = 1,
                    textStyle = TextStyle(
                        fontSize = 30.sp,
                        textAlign = TextAlign.Center
                    ),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    placeholder = {
                        Text(modifier = Modifier.fillMaxWidth(),
                            text = "*************",
                            fontSize = 30.sp,
                            textAlign = TextAlign.Center
                        )
                    },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,   // Fondo blanco cuando está enfocado
                        unfocusedContainerColor = Color.White, // Fondo blanco cuando no está enfocado
                        focusedIndicatorColor = Color.Transparent,  // Sin la línea de indicador inferior
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    shape = RoundedCornerShape(38.dp)
                )
            }

            Button(onClick = {
                navController.navigate(Routes.FoodDeatil.createRout(barcode))
            }, modifier= Modifier
                .padding(start = 24.dp, end = 24.dp, bottom = 24.dp)
                .fillMaxWidth()
                ,enabled = barcode.length==13,
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black)) {
                Text(text = "Buscar")
            }

        }
    }

}