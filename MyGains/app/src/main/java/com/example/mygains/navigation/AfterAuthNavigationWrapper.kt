package com.example.mygains.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mygains.createroutineprocess.ui.CreateRoutineViewModel
import com.example.mygains.createroutineprocess.ui.screens.ExercisesToAddRoutine
import com.example.mygains.createroutineprocess.ui.screens.InfoTypeOfWorkout
import com.example.mygains.createroutineprocess.ui.screens.TypeOfTrainingScreen
import com.example.mygains.dashboard.ui.MyDashBoard
import com.example.mygains.exercisesplan.ui.ExcercisesPlanCompossable
import com.example.mygains.extras.navigationroutes.Routes
import com.example.mygains.plan.ui.PlanCompossable
import com.example.mygains.userinfo.ui.screens.UserInfoComposable


@Composable
fun AfterAuthNavigationWrapper(
    modifier: Modifier,
    nav: NavHostController
) {

    val createRoutineViewModel: CreateRoutineViewModel = hiltViewModel()

    NavHost(
        navController = nav,
        startDestination = Routes.Home.routes,
        modifier = modifier,
        enterTransition = {
            slideInHorizontally(initialOffsetX = { it }, animationSpec = tween(500)) + fadeIn(tween(500))
        },
        exitTransition = {
            slideOutHorizontally(targetOffsetX = { -it }, animationSpec = tween(500)) + fadeOut(tween(500))
        },
        popEnterTransition = {
            slideInHorizontally(initialOffsetX = { -it }, animationSpec = tween(500)) + fadeIn(tween(500))
        },
        popExitTransition = {
            slideOutHorizontally(targetOffsetX = { it }, animationSpec = tween(500)) + fadeOut(tween(500))
        }
    ) {
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
        composable(Routes.GainsScanner.routes){ TypeOfTrainingScreen(nav,createRoutineViewModel = createRoutineViewModel)}
        composable(Routes.TypesWorkOuts.routes){ TypeOfTrainingScreen(nav,createRoutineViewModel) }
        composable(Routes.InfoTypeOfWorkout.routes) { backStackEntry->
            InfoTypeOfWorkout(nav , backStackEntry.arguments?.getString("workout_id").orEmpty(),createRoutineViewModel)
        }

        composable(Routes.Exercises.routes) { backStackEntry->
            ExercisesToAddRoutine(nav , backStackEntry.arguments?.getString("muscle_id").orEmpty(),createRoutineViewModel)
        }
    }
}