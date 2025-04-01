package com.example.mygains.extras.globalcomponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mygains.R
import com.example.mygains.base.response.BaseResponseUi
import com.example.mygains.configuration.data.model.ConfigurationItemModel

@Composable
fun CustomAlertDialog(responseUi: BaseResponseUi, actionDismiss:()->Unit , actionConfirm:()->Unit){
    AlertDialog(modifier = Modifier.fillMaxWidth(),onDismissRequest = actionDismiss,
        confirmButton = {
            Button(onClick = { actionConfirm.invoke()}
        ) {
            Text(text = "Cerrar")
        }
        }
        , title = { Text(text = "Error") },
        text = { Box(){
            Text(text = responseUi.message, textAlign = TextAlign.Center)
        }
        },
        icon = { Icon(imageVector = Icons.Default.Info , contentDescription ="error",
            tint = Color(LocalContext.current.getColor(R.color.orange))
        )
        }, containerColor = Color(0xFFFCE5D8)
    )
}


@Composable
fun ItemListComponent(
    configModel: ConfigurationItemModel
){

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 4.dp,
                shape = CircleShape,
                brush = Brush.linearGradient(
                    colors = listOf(
                        colorResource(id = R.color.orange),
                        colorResource(id = R.color.orange_low),
                    ),
                    start = Offset(1000f, -100f),
                    end = Offset(-100f, 100f),
                    tileMode = TileMode.Clamp
                )
            )
            .clickable {
                configModel.action.invoke()
            }
    ){
        Row(
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier.size(24.dp),
                painter = painterResource(id = configModel.leftIcon),
                contentDescription = "iconImage")

            Text(
                modifier = Modifier
                    .padding(8.dp)
                    .weight(1f),
                text = configModel.text,
                fontSize = 18.sp
            )

            Image(
                modifier = Modifier.size(18.dp),
                painter = painterResource(id = R.drawable.right_row),
                contentDescription = "iconImage")
        }
    }
}