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
import com.example.mygains.plan.ui.PlanCompossable
import com.example.mygains.routinedetail.ui.RoutineDetailCompossable
import com.example.mygains.userinfo.ui.screens.UserInfoComposable


@Composable
fun AfterAuthNavigationWrapper(
    modifier: Modifier,
    nav: NavHostController
) {


    NavHost(navController = nav, startDestination = Routes.Home.routes, modifier = modifier) {
        composable(Routes.Home.routes){ MyDashBoard(nav) }
        composable(Routes.Perfil.routes){ UserInfoComposable(nav = nav) }
        //refactorizar el apartado del resultactivity para el inicio de sesion con google
        //composable(Routes.NewUser.routes){ NewUserComposable(newUserViewModel = newUserViewmodel, navigationController, onSignInClick = { newUserViewmodel.signInWithGoogle(this@MainActivity,signInLauncher, this@MainActivity)  }) }
        composable(route = Routes.Plan.routes) { backStackEntry->
            PlanCompossable(nav = nav)
        }
        composable(Routes.ExcercisesPlan.routes){ backStackEntry->
            ExcercisesPlanCompossable(nav = nav, backStackEntry.arguments?.getString("date").orEmpty())
        }
        composable(Routes.GainsScanner.routes){ RoutineDetailCompossable() }
    }
}