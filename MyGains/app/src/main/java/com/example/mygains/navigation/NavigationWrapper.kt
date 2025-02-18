package com.example.mygains.navigation


import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mygains.dashboard.ui.MyDashBoard
import com.example.mygains.exercisesplan.ui.ExcercisesPlanCompossable
import com.example.mygains.extras.navigationroutes.Routes
import com.example.mygains.login.ui.LoginScreen
import com.example.mygains.newuser.ui.NewUserComposable
import com.example.mygains.plan.ui.PlanCompossable
import com.example.mygains.routinedetail.ui.RoutineDetailCompossable
import com.example.mygains.splashscreen.ui.SplashScreenComposable
import com.example.mygains.userinfo.ui.screens.UserInfoComposable


//En esta pantalla alojamos toda la navegacion de las pantallas de la app
@Composable
fun  NavigationWrapper(modifier: Modifier, navigationController:NavHostController) {


    NavHost(navController = navigationController, startDestination = "splash", modifier = modifier) {
        composable(Routes.Splash.routes){ SplashScreenComposable(navHostController = navigationController) }
        composable(Routes.Home.routes){ MyDashBoard(nav = navigationController) }
        composable(Routes.Login.routes){ LoginScreen(nav = navigationController ) }
        composable(Routes.Perfil.routes){ UserInfoComposable(nav = navigationController) }
        composable(Routes.NewUser.routes){ NewUserComposable(navHostController = navigationController) }

        //refactorizar el apartado del resultactivity para el inicio de sesion con google
        //composable(Routes.NewUser.routes){ NewUserComposable(newUserViewModel = newUserViewmodel, navigationController, onSignInClick = { newUserViewmodel.signInWithGoogle(this@MainActivity,signInLauncher, this@MainActivity)  }) }
        composable(route = Routes.Plan.routes) { backStackEntry->
            PlanCompossable(nav = navigationController)
        }
        composable(Routes.ExcercisesPlan.routes){ backStackEntry->
            ExcercisesPlanCompossable(nav = navigationController, backStackEntry.arguments?.getString("date").orEmpty())
        }
        composable(Routes.GainsScanner.routes){ RoutineDetailCompossable() }
    }
}