package com.example.mygains.scanproducts.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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



@Composable
fun BarCodeComposable(viewModel: ScanBarCodeViewModel){

    var barcode by remember {
        mutableStateOf("")
    }


    Box(Modifier.fillMaxSize()) {
        Card(
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.orange_low))) {
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
                viewModel.getProduct(barcode)
            }, modifier= Modifier
                .padding(start = 24.dp, end = 24.dp, bottom = 24.dp)
                .fillMaxWidth()
                ,enabled = barcode.length==13,
                colors = ButtonDefaults.buttonColors(containerColor = Color(color = 0xFFCA5300 ))) {
                Text(text = "Buscar")
            }

        }
    }

}