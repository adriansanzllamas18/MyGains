package com.example.mygains.dashboard.data.models

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.mygains.R
import com.example.mygains.extras.navigationroutes.Routes



sealed class BottomBarItem  {
    abstract val rout:String
    abstract val icon:@Composable ()->Unit

    data class Home(
        override val rout: String = Routes.Home.routes,
        override val icon:@Composable () -> Unit={
            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(id = R.drawable.hogar),
                contentDescription = "",
                tint = Color.White
            )
        }
    ): BottomBarItem()


    data class Profile(
        val image:String="",
        override val rout: String=Routes.Perfil.routes,
        override val icon: @Composable () -> Unit= {

            if (image.isEmpty()){
                Icon(
                    modifier =Modifier.size(24.dp),
                    imageVector = Icons.Default.Person,
                    contentDescription = "",
                    tint = Color.White
                )
            }else{

               AsyncImage(
                   modifier  = Modifier
                       .size(48.dp)
                       .clip(CircleShape),
                   model = image,
                   contentDescription = "imageUser",
                   contentScale = ContentScale.Fit
               )
            }
        }
    ):BottomBarItem()

    data class Plan(
        override val rout: String=Routes.Plan.routes,
        override val icon: @Composable () -> Unit= {
            Icon(
                modifier =Modifier.size(24.dp),
                painter = painterResource(id = R.drawable.calendario_lineas_boligrafo),
                contentDescription = "",
                tint = Color.White
            )
        }
    ):BottomBarItem()
}