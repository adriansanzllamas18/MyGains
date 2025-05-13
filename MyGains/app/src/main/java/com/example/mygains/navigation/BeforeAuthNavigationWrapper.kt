package com.example.mygains.navigation


import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
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

    NavHost(
        navController = nav,
        startDestination = Routes.Splash.routes,
        modifier = modifier
    )
    {

        composable(Routes.Splash.routes){ SplashScreenComposable(navHostController = nav) }
        composable(
            Routes.Login.routes,
            exitTransition = {
                slideOutHorizontally(targetOffsetX = { -it }, animationSpec = tween(500))
            }
        ){ LoginScreen(nav = nav ) }
        composable(
            Routes.NewUser.routes,
            enterTransition = {
                slideInVertically(initialOffsetY = { it }, animationSpec = tween(500))
            },
            exitTransition = {
                slideOutHorizontally(targetOffsetX = { -it }, animationSpec = tween(500))
            }
        ){ NewUserComposable(navHostController = nav) }

        //composable(Routes.Home.routes){ HomeScreen()}
    }
}