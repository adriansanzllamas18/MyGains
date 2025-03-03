package com.example.mygains.createroutineprocess.ui.components

import android.icu.text.CaseMap.Title
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mygains.R


/*Componentes pantalla detalle de ejercicio*/

@Composable
fun TitleAndImageIconComponent(title:String, icon:Int){
    Row(horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.CenterVertically) {
        Image(
            modifier = Modifier.size(24.dp),
            painter = painterResource(id = icon),
            contentDescription = "icon image")
        Text(
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(8.dp),
            text = title
        )
    }
}


@Composable
fun ListInfoComponents(list:List<String>){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardColors(
            containerColor = colorResource(id = R.color.orange_low),
            contentColor = Color.Black,
            disabledContainerColor = Color.Black,
            disabledContentColor = Color.LightGray
        )
    ) {
        Column(modifier = Modifier.padding(start = 16.dp)) {
           list.forEach {
                Text(text = "${it}.", fontSize = 14.sp)
            }
        }
    }
}