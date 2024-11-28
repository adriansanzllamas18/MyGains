package com.example.mygains.scanproducts.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mygains.R


@Preview(showBackground = true)
@Composable
fun BarCodeComposable(){

    Box(Modifier.fillMaxSize()) {
        Card(
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.orange_low))) {
            Column( modifier = Modifier.align(Alignment.CenterHorizontally)) {
                Image(painter = painterResource(id = R.drawable.barcode), contentDescription ="barcode",
                    modifier = Modifier.align(Alignment.CenterHorizontally))

                TextField(value = "8999999999999", onValueChange = { },
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    maxLines = 1,
                    textStyle = TextStyle(
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center // Alineación horizontal centrada
                    ),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions( keyboardType = KeyboardType.Email),
                    placeholder = {Text(text = "Correo")},
                    colors = TextFieldDefaults.colors(focusedContainerColor = Color(0xFFFCE5D8),   // Color de fondo cuando está enfocado
                        unfocusedContainerColor = Color(0xFFFCE5D8), // Color de fondo cuando no está enfocado
                        focusedIndicatorColor = Color.Transparent,   // Eliminar la línea inferior cuando está enfocado
                        unfocusedIndicatorColor = Color.Transparent ),
                    shape = RoundedCornerShape(38.dp)
                )
            }

        }
    }
}