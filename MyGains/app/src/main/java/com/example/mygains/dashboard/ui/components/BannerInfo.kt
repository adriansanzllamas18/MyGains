package com.example.mygains.dashboard.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mygains.R

@Composable
fun BannerInfo(image:Int,onclick:() -> Unit){
    Card(
        modifier= Modifier
            .height(80.dp)
            .fillMaxWidth()
            .clickable { onclick },
         shape = RoundedCornerShape(16.dp)
    ){
        Box(modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        Color(0xFFFCE5D8), // Naranja claro
                        Color(0xFF3498db), // Gris suave

                    )
                )
            )){
            
            Row(modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 8.dp ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = image),
                    contentDescription ="imageBanner" ,
                    modifier = Modifier.size(50.dp)
                )

                // todo aqui deberia ir el tipo de entreno que le toca
                Text(text = "Día de Pecho! Tu entreno de fuerza te espera.",
                    textAlign = TextAlign.Center,
                    fontSize = 12.sp,
                    modifier = Modifier.weight(1f).padding(vertical = 12.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.proximo ),
                    contentDescription ="imageBanner" ,
                    modifier = Modifier.size(16.dp),
                    alignment = Alignment.Center
                )
            }
        }
    }
}