package com.example.mygains.dashboard.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mygains.R

@Composable
fun BannerInfo(image:Int,onclick:() -> Unit){
    Card(
        modifier= Modifier
            .height(60.dp)
            .fillMaxWidth()
            .clickable { onclick },
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(2.dp, colorResource(id = R.color.orange))
    ){
        Box(modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.orange_low))
        ){
            
            Row(modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    painter = painterResource(id = image),
                    contentDescription ="imageBanner" ,
                    modifier = Modifier.size(24.dp)
                )

                Column(Modifier.padding(start = 8.dp)) {
                    Text(
                        text = "Rutina programada para hoy",
                        color = colorResource(id = R.color.orange),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold
                    )

                    Text(
                        text = "Toca para ver los detalles del entrenamiento.",
                        color = colorResource(id = R.color.orange).copy(alpha = 0.9f),
                        fontSize = 12.sp
                    )
                }


                Icon(
                    painter = painterResource(id = R.drawable.right_row ),
                    contentDescription ="imageBanner" ,
                    modifier = Modifier
                        .size(16.dp)
                        .weight(1f)
                )
            }
        }
    }
}