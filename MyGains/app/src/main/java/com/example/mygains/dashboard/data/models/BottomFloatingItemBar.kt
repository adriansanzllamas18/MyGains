package com.example.mygains.dashboard.data.models

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import com.example.mygains.R
import com.example.mygains.extras.navigationroutes.Routes

sealed class BottomFloatingItemBar {
    abstract val rout:String
    abstract val modifier:Modifier
    abstract val alignment:Alignment
    abstract val icon:@Composable ()->Unit

    data class Home(

        override val rout: String = Routes.Home.routes,
        override val icon:@Composable () -> Unit={
            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(id = R.drawable.hogar),
                contentDescription = ""
            )
        },
        override val modifier: Modifier = Modifier
            .offset(y = (-25).dp)
            .zIndex(1f)
            .size(80.dp)
            .border(
                width = 4.dp,
                color = Color.White,
                shape = CircleShape
            ),
        override val alignment: Alignment= Alignment.TopCenter,
    ): BottomFloatingItemBar()


    data class Profile(
        val image:String="",
        override val rout: String= Routes.Perfil.routes,
        override val icon: @Composable () -> Unit= {

            if (image.isEmpty()){
                Icon(
                    modifier = Modifier.size(24.dp),
                    imageVector = Icons.Default.Person,
                    contentDescription = ""
                )
            }else{
                AsyncImage(
                    modifier = Modifier.fillMaxSize(),
                    model = image,
                    contentDescription = "imageUser",
                    contentScale = ContentScale.Fit
                )
            }
        },
        override val modifier: Modifier = Modifier
            .offset(x = 40.dp, y = (-25).dp)
            .zIndex(1f)
            .size(80.dp)
            .border(
                width = 4.dp,
                color = Color.White,
                shape = CircleShape
            ),
        override val alignment: Alignment= Alignment.TopStart,
    ):BottomFloatingItemBar()

    data class Plan(
        override val rout: String= Routes.Plan.routes,
        override val icon: @Composable () -> Unit= {
            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(id = R.drawable.calendario_lineas_boligrafo),
                contentDescription = ""
            )
        },
        override val modifier: Modifier= Modifier
            .offset(x = 120.dp, y = (-25).dp)
            .zIndex(1f)
            .size(80.dp)
            .border(
                width = 4.dp,
                color = Color.White,
                shape = CircleShape
            ),
        override val alignment: Alignment= Alignment.TopCenter,
    ):BottomFloatingItemBar()
}