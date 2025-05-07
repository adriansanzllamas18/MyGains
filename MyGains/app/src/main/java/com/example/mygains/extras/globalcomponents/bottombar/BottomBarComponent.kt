package com.example.mygains.extras.globalcomponents.bottombar

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
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
import androidx.navigation.NavHostController
import com.example.mygains.R
import com.example.mygains.dashboard.data.models.BottomBarItem
import com.example.mygains.extras.navigationroutes.Routes

@Composable
fun CustomBottomNavigationBar(nav: NavHostController){


    var selectedItemIndex by remember { mutableStateOf(1) }

    val itemList = mutableListOf(
        BottomBarItem.Profile(isSelected = selectedItemIndex == 0),
        BottomBarItem.Home(isSelected = selectedItemIndex == 1),
        BottomBarItem.Plan(isSelected = selectedItemIndex == 2)
    )

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        color =  colorResource(id = R.color.grey_deep),
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            itemList.forEachIndexed { index, item ->
                BottomNavItem(
                    item = item,
                    isSelected = index == selectedItemIndex,
                    onClick = {
                        selectedItemIndex = index
                        nav.navigate(item.rout) {
                            // Solo elimina pantallas de la pila que estén por encima
                            nav.graph.startDestinationRoute?.let { rout ->
                                popUpTo(Routes.Home.routes) {//dentro del grafo hacemos que home sea la ruta de inicio en vez de startdestination
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
            item.icon.invoke()
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