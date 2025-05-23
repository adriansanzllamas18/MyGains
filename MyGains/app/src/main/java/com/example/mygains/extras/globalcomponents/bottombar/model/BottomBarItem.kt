package com.example.mygains.extras.globalcomponents.bottombar.model

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.mygains.R
import com.example.mygains.extras.navigationroutes.Routes



sealed class BottomBarItem  {
    abstract val title:String
    abstract val rout:String
    abstract val icon:@Composable ()->Unit
    abstract val isSelected:Boolean

    data class Home(
        override val isSelected: Boolean = false,
        override val title: String = "Home",
        override val rout: String = Routes.Home.routes,
        override val icon:@Composable () -> Unit={
            Icon(
                modifier = Modifier
                    .size(24.dp)
                    .shadow(
                    elevation = if (isSelected) 16.dp else 0.dp,
                    shape = RoundedCornerShape(2.dp),
                    spotColor = if (isSelected) colorResource(id = R.color.orange_low) else Color.Transparent
                ),
                painter = painterResource(id = R.drawable.hogar),
                contentDescription = "",
                tint = Color.White
            )
        },

    ): BottomBarItem()


    data class Profile(
        override val isSelected: Boolean = false,
        override val title: String = "Perfil",
        val image:String="",
        override val rout: String=Routes.Perfil.routes,
        override val icon: @Composable () -> Unit= {

            if (image.isEmpty()){
                Icon(
                    modifier = Modifier
                        .size(24.dp)
                        .shadow(
                            elevation = if (isSelected) 16.dp else 0.dp,
                            shape = RoundedCornerShape(2.dp),
                            spotColor = if (isSelected) colorResource(id = R.color.orange_low) else Color.Transparent
                        ),
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
        },
    ): BottomBarItem()

    data class Plan(
        override val isSelected: Boolean = false,
        override val title: String = "Plan",
        override val rout: String=Routes.Plan.routes,
        override val icon: @Composable () -> Unit= {
            Icon(
                modifier =Modifier
                    .size(24.dp)
                    .shadow(
                        elevation = if (isSelected) 16.dp else 0.dp,
                        shape = RoundedCornerShape(2.dp),
                        spotColor = if (isSelected) colorResource(id = R.color.orange_low) else Color.Transparent
                    ),
                painter = painterResource(id = R.drawable.calendario_lineas_boligrafo),
                contentDescription = "",
                tint = Color.White
            )
        },
    ): BottomBarItem()
}