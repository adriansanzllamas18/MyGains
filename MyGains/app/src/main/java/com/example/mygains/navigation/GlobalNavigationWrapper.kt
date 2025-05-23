package com.example.mygains.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.mygains.configuration.ui.ConfigurationComposable
import com.example.mygains.createroutineprocess.ui.screens.stregnth.CreateRoutineViewModel
import com.example.mygains.createroutineprocess.ui.screens.stregnth.ExercisesToAddRoutine
import com.example.mygains.createroutineprocess.ui.screens.stregnth.InfoTypeOfWorkout
import com.example.mygains.createroutineprocess.ui.screens.workouts.TypeOfWorkoutsScreen
import com.example.mygains.dashboard.ui.NewHomeScreen
import com.example.mygains.exercisesplan.ui.ExcercisesPlanCompossable
import com.example.mygains.extras.navigationroutes.Routes
import com.example.mygains.login.ui.LoginScreen
import com.example.mygains.newuser.ui.NewUserComposable
import com.example.mygains.plan.ui.PlanCompossable
import com.example.mygains.scanproducts.ui.ScanProductComposable
import com.example.mygains.splashscreen.ui.SplashScreenComposable
import com.example.mygains.userinfo.ui.screens.UserInfoComposable


@Composable
fun GlobalNavigationWrapper(
    modifier:Modifier,
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


        composable(Routes.Home.routes){ NewHomeScreen(nav) }
        composable(Routes.Perfil.routes){ UserInfoComposable(nav = nav) }
        //refactorizar el apartado del resultactivity para el inicio de sesion con google
        //composable(Routes.NewUser.routes){ NewUserComposable(newUserViewModel = newUserViewmodel, navigationController, onSignInClick = { newUserViewmodel.signInWithGoogle(this@MainActivity,signInLauncher, this@MainActivity)  }) }
        composable(route = Routes.Plan.routes) { backStackEntry->
            PlanCompossable(nav = nav)
        }
        composable(Routes.ExcercisesPlan.routes){ backStackEntry->
            ExcercisesPlanCompossable(nav = nav, backStackEntry.arguments?.getString("date").orEmpty())
        }
        composable(Routes.GainsScanner.routes){ ScanProductComposable() }
        composable(Routes.TypesWorkOuts.routes){ TypeOfWorkoutsScreen(nav) }

        // Usamos remember para obtener y conservar la referencia al BackStackEntry del grafo de navegación.
        // Esto es crucial por varios motivos:
        // 1. Evita búsquedas repetidas en la pila de navegación durante recomposiciones
        // 2. Garantiza que siempre usemos la misma instancia del ViewModel en todas las pantallas del flujo
        // 3. Previene errores de "No destination with route X is on the NavController's back stack"
        //    que ocurrirían si intentamos buscar la entrada en cada recomposición
        // El ViewModel se mantendrá vivo mientras exista este grafo de navegación y se destruirá
        // automáticamente cuando salgamos completamente de este flujo.

        navigation(startDestination = Routes.InfoTypeOfWorkout.routes, route = "create_rotine_flow") {

            composable(Routes.InfoTypeOfWorkout.routes) { backStackEntry ->
                // Use the current navigation graph's entry instead:
                val parentEntry = remember { nav.getBackStackEntry("create_rotine_flow") }
                val createRoutineViewModel: CreateRoutineViewModel = hiltViewModel(
                    viewModelStoreOwner = parentEntry
                )
                InfoTypeOfWorkout(nav, backStackEntry.arguments?.getString("workout_id").orEmpty(), createRoutineViewModel)
            }

            composable(Routes.Exercises.routes) { backStackEntry ->
                // Use the same parent entry again:
                val parentEntry = remember { nav.getBackStackEntry("create_rotine_flow") }
                val createRoutineViewModel: CreateRoutineViewModel = hiltViewModel(
                    viewModelStoreOwner = parentEntry
                )
                ExercisesToAddRoutine(nav, backStackEntry.arguments?.getString("muscle_id").orEmpty(), createRoutineViewModel)
            }
        }

        composable(Routes.Configuration.routes){ ConfigurationComposable(nav) }
    }
}