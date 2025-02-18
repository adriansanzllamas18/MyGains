package com.example.mygains.dashboard.ui

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.mygains.R
import com.example.mygains.extras.navigationroutes.Routes
import com.example.mygains.navigation.AfterAuthNavigationWrapper


@Composable
fun HomeScreen() {

    val navHostController= rememberNavController()
    Scaffold(bottomBar = {
        MyBottomNavigation(navHostController )
    }) {innerPading->
        Box{ AfterAuthNavigationWrapper(nav = navHostController, modifier = Modifier.padding(innerPading)) }
    }
}


@Composable
fun MyBottomNavigation(nav: NavHostController) {


    val navBackStackEntry by nav.currentBackStackEntryAsState()
    val currentDestination= navBackStackEntry ?.destination

    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.BottomCenter
    ) {
        // FAB dinámico basado en currentComposable
        when (currentDestination?.route) {

            Routes.Perfil.routes -> {
                FloatingActionButton(
                    onClick = { },
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .offset(x = 40.dp, y = (-25).dp)
                        .zIndex(1f)
                        .size(80.dp)
                        .border(
                            width = 4.dp,
                            color = Color.White,
                            shape = CircleShape
                        ),
                    containerColor = MaterialTheme.colorScheme.primary,
                    shape = CircleShape
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "perfil",
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
            Routes.Home.routes -> {
                FloatingActionButton(
                    onClick = { },
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .offset(y = (-25).dp)
                        .zIndex(1f)
                        .size(80.dp)
                        .border(
                            width = 4.dp,
                            color = Color.White,
                            shape = CircleShape
                        ),
                    containerColor = MaterialTheme.colorScheme.primary,
                    shape = CircleShape
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.hogar),
                        contentDescription = "home",
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
            Routes.Plan.routes -> {
                FloatingActionButton(
                    onClick = { },
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .offset(x = 120.dp, y = (-25).dp)
                        .zIndex(1f)
                        .size(80.dp)
                        .border(
                            width = 4.dp,
                            color = Color.White,
                            shape = CircleShape
                        ),
                    containerColor = MaterialTheme.colorScheme.primary,
                    shape = CircleShape
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.calendario_lineas_boligrafo),
                        contentDescription = "plan",
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }

        // BottomBar
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp)
        ) {
            NavigationBar(
                containerColor = Color(0xFFFCE5D8)
            ) {
                // Perfil
                if (currentDestination?.route == Routes.Perfil.routes) {
                    NavigationBarItem(
                        selected = false,
                        onClick = { },
                        icon = { Box(modifier = Modifier.size(24.dp)) }
                    )
                } else {
                    NavigationBarItem(
                        selected = false,
                        onClick = {
                            nav.navigate(Routes.Perfil.routes){
                                // Solo elimina pantallas de la pila que estén por encima
                                nav.graph.startDestinationRoute?.let { rout ->
                                    popUpTo(rout) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                            nav.currentBackStackEntry?.destination?.hierarchy?.forEach {
                                Log.i("entry",it.route.toString())
                            }

                        },
                        icon = {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "perfil"
                            )
                        }
                    )
                }

                // Home
                if (currentDestination?.route== Routes.Home.routes) {
                    NavigationBarItem(
                        selected = false,
                        onClick = { },
                        icon = { Box(modifier = Modifier.size(24.dp)) }
                    )
                } else {
                    NavigationBarItem(
                        selected = false,
                        onClick = {
                            nav.navigate(Routes.Home.routes){
                                // Solo elimina pantallas de la pila que estén por encima
                                nav.graph.startDestinationRoute?.let { rout ->
                                    popUpTo(rout) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }


                        },
                        icon = {
                            Icon(
                                painter = painterResource(id = R.drawable.hogar),
                                contentDescription = "home",
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    )
                }

                // Plan
                if (currentDestination?.route == Routes.Plan.routes) {
                    NavigationBarItem(
                        selected = false,
                        onClick = { },
                        icon = { Box(modifier = Modifier.size(24.dp)) }
                    )
                } else {
                    NavigationBarItem(
                        selected = false,
                        onClick = {
                            nav.navigate(Routes.Plan.routes) {
                                // Solo elimina pantallas de la pila que estén por encima
                                nav.graph.startDestinationRoute?.let { rout ->
                                    popUpTo(rout) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        },
                        icon = {
                            Icon(
                                modifier = Modifier.size(24.dp),
                                painter = painterResource(id = R.drawable.calendario_lineas_boligrafo),
                                contentDescription = "plan"
                            )
                        }
                    )
                }
            }
        }
    }
}