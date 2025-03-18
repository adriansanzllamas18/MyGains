package com.example.mygains.extras.globalcomponents

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import com.example.mygains.R
import com.example.mygains.base.response.BaseResponseUi

@Composable
fun CustomAlertDialog(responseUi: BaseResponseUi, actionDismiss:()->Unit , actionConfirm:()->Unit){
    AlertDialog(modifier = Modifier.fillMaxWidth(),onDismissRequest = actionDismiss,
        confirmButton = {
            Button(onClick = { actionConfirm}
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