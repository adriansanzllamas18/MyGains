package com.example.mygains.navigation


import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mygains.dashboard.ui.HomeScreen
import com.example.mygains.dashboard.ui.MyDashBoard
import com.example.mygains.extras.navigationroutes.Routes
import com.example.mygains.login.ui.LoginScreen
import com.example.mygains.newuser.ui.NewUserComposable
import com.example.mygains.splashscreen.ui.SplashScreenComposable


//En esta pantalla alojamos toda la navegacion de las pantallas de la app
@Composable
fun  BeforeAuthNavigationWrapper(
    modifier: Modifier,
    nav: NavHostController
) {

    NavHost(navController = nav, startDestination = Routes.Splash.routes) {
        composable(Routes.Splash.routes){ SplashScreenComposable(navHostController = nav) }
        composable(Routes.Login.routes){ LoginScreen(nav = nav ) }
        composable(Routes.NewUser.routes){ NewUserComposable(navHostController = nav) }
        composable(Routes.Home.routes){ HomeScreen()}

    }
}