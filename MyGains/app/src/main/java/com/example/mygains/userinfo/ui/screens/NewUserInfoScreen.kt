package com.example.mygains.userinfo.ui.screens

import android.widget.GridLayout.Spec
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mygains.R

@Preview(showBackground = true)
@Composable
fun NewUserInfoScreen() {

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    )
    {
        item {
           UserInfoHeaderSection()
        }

        item {
            MyHealthSection()
        }
        item {
            MyNutritionSection()
        }
    }
}

@Composable
fun MyNutritionSection() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp))
    {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
        {
            Text(
                text = "Datos  Nutricionales",
                textAlign = TextAlign.Center,
                fontSize = 18.sp,
                fontFamily = FontFamily(Font( R.font.montserratbold))
            )
            CaloriesSection()
            ProgressBarNutritionIndicator()
            ProgressBarNutritionIndicator()
            ProgressBarNutritionIndicator()
        }
    }
}

@Composable
fun CaloriesSection() {
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    )
    {
        Text(
            text = "2300",
            textAlign = TextAlign.Center,
            fontSize = 14.sp,
            fontFamily = FontFamily(Font( R.font.montserratregular))
        )
        Text(
            text = "/"+"2300",
            textAlign = TextAlign.Center,
            fontSize = 14.sp,
            fontFamily = FontFamily(Font( R.font.montserratbold))
        )
    }
}

@Composable
fun ProgressBarNutritionIndicator(){


    var initAnimation by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(Unit) {
        initAnimation = true
    }

    val progresanimation by animateFloatAsState(
        targetValue =  if (initAnimation)0.45f else 0f,
        label = "",
        animationSpec = tween(
            durationMillis = 2000,
            delayMillis = 1000,
            easing = FastOutSlowInEasing
        )
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    )
    {
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            Row {
                Text(
                    text = "Proteinas",
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font( R.font.montserratregular))
                )
                Text(
                    modifier = Modifier.weight(1f),
                    text = "45%",
                    textAlign = TextAlign.End,
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font( R.font.montserratregular))
                )
            }
            LinearProgressIndicator(
                progress = { progresanimation },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .padding(horizontal = 8.dp),
                color = Color(0xFF00b894),
                trackColor = Color(0xFFe0e0e0),
                strokeCap = StrokeCap.Butt,
                drawStopIndicator = {}
            )
        }
    }
}

@Composable
fun MyHealthSection() {

Box(
    modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
        .shadow(8.dp, shape = RectangleShape)
        .background(
            colorResource(id = R.color.white)
        ))
{
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    )
    {
        Text(
            text = "Datos de salud",
            textAlign = TextAlign.Center,
            fontSize = 18.sp,
            fontFamily = FontFamily(Font( R.font.montserratbold))
        )

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        )
        {
            Column(
                Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            )
            {
                Text(
                    text = "89",
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp,
                    fontFamily = FontFamily(Font( R.font.montserratbold))
                )
                Text(
                    text = "peso",
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font( R.font.montserratregular))
                )
            }
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            )
            {
                Text(
                    text = "89",
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp,
                    fontFamily = FontFamily(Font( R.font.montserratbold))
                )
                Text(
                    text = "peso",
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font( R.font.montserratregular))
                )
            }
        }

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        )
        {
            Column(
                Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            )
            {
                Text(
                    text = "89",
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp,
                    fontFamily = FontFamily(Font( R.font.montserratbold))
                )
                Text(
                    text = "peso",
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font( R.font.montserratregular))
                )
            }
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            )
            {
                Text(
                    text = "89",
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp,
                    fontFamily = FontFamily(Font( R.font.montserratbold))
                )

                Text(
                    text = "peso",
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font( R.font.montserratregular))
                )
            }
        }
    }
}
}

@Composable
fun UserInfoHeaderSection() {


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
        targetValue = colorResource(id = R.color.orange),
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

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(animatedBrush),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    )
    {
        Image(imageVector = Icons.Filled.AccountCircle, contentDescription = "image",
            Modifier
                .size(140.dp)
                .padding(vertical = 8.dp))

        Text(
            text = "Carlos Mendoza",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontSize = 28.sp,
            fontFamily = FontFamily(Font( R.font.montserratbold))
        )
        Text(
            text = "@CarlosFit39",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontSize = 14.sp,
            fontFamily = FontFamily(Font( R.font.montserratregular))
        )
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(30.dp))
    }
}
