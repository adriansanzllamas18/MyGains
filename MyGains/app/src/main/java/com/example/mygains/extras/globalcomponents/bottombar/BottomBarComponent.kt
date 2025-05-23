package com.example.mygains.extras.globalcomponents.bottombar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import com.example.mygains.R
import com.example.mygains.extras.globalcomponents.bottombar.model.BottomBarItem
import com.example.mygains.extras.navigationroutes.Routes
import com.example.mygains.userinfo.data.models.UserData

@Composable
fun CustomBottomNavigationBar(nav: NavHostController, currentDestination: NavDestination?){


    var selectedItemIndex by remember { mutableStateOf(currentDestination?.route?:"") }

    var bottombarViewModel: BottombarViewModel = hiltViewModel()
    val imageUser  by bottombarViewModel.userDataLive.observeAsState(UserData())

    val itemList = mutableListOf(
        BottomBarItem.Profile(isSelected = selectedItemIndex == Routes.Perfil.routes, image = imageUser.image?:""),
        BottomBarItem.Home(isSelected = selectedItemIndex ==  Routes.Home.routes),
        BottomBarItem.Plan(isSelected = selectedItemIndex ==  Routes.Plan.routes)
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .background(
                color = Color(0xFF1C1C1C),
                shape = RoundedCornerShape(24.dp)
            )
            .height(80.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            itemList.forEachIndexed { index, item ->
                BottomNavItem(
                    item = item,
                    isSelected = item.rout == selectedItemIndex,
                    onClick = {
                        selectedItemIndex = item.rout
                        nav.navigate(item.rout) {
                            nav.graph.startDestinationRoute?.let { rout ->
                                popUpTo(Routes.Home.routes) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    }
                )
            }
        }
    }


}

@Composable
fun BottomNavItem(item: BottomBarItem, isSelected: Boolean, onClick: () -> Unit) {

    val iconSize by animateDpAsState(
        targetValue = if (isSelected) 30.dp else 24.dp,
        animationSpec = tween(500)
    )
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp)
    ) {
        Box(
            modifier = Modifier
                .height(4.dp)
                .width(32.dp)
                .background(
                    brush = if (isSelected) {
                        Brush.horizontalGradient(
                            colors = listOf(
                                colorResource(id = R.color.orange).copy(alpha = 0.8f),
                                colorResource(id = R.color.orange),
                                colorResource(id = R.color.orange).copy(alpha = 0.8f)
                            )
                        )
                    } else {
                        Brush.horizontalGradient(listOf(Color.Transparent, Color.Transparent))
                    },
                    shape = RoundedCornerShape(2.dp)
                )
        )

        Spacer(modifier = Modifier.height(4.dp))

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
        ) {
            Box(
                modifier = Modifier.size(iconSize),
                contentAlignment = Alignment.Center
            )
            {
                item.icon.invoke()
            }

        }

        AnimatedVisibility(visible = isSelected) {
            Text(
                text = item.title,
                color = if (isSelected) colorResource(id = R.color.orange_low) else Color(0xFF9CA3AF),
                fontSize = 12.sp,
                fontWeight = if (isSelected) FontWeight.Medium else FontWeight.Normal,
                maxLines = 1,
                modifier = Modifier.padding(top = 2.dp)
            )
        }
    }
}