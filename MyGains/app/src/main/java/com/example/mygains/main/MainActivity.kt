package com.example.mygains.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mygains.dashboard.ui.DashBoardViewModel
import com.example.mygains.dashboard.ui.MyDashBoard
import com.example.mygains.exercisesplan.ui.ExcercisesPlanCompossable
import com.example.mygains.extras.navigationroutes.Routes
import com.example.mygains.login.ui.LoginScreen
import com.example.mygains.login.ui.LoginViewModel
import com.example.mygains.newuser.ui.NewUserComposable
import com.example.mygains.newuser.ui.NewUserViewModel
import com.example.mygains.plan.ui.PlanCompossable
import com.example.mygains.scanproducts.ui.ScanProductComposable
import com.example.mygains.splashscreen.ui.SplashScreenComposable
import com.example.mygains.splashscreen.ui.SplashViewModel
import com.example.mygains.ui.theme.MyGainsTheme
import com.example.mygains.userinfo.ui.UserInfoComposable
import com.example.mygains.userinfo.ui.UserInfoViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val logingViewmodel: LoginViewModel by viewModels()
    private val newUserViewmodel: NewUserViewModel by viewModels()
    private val userInfoViewModel: UserInfoViewModel by viewModels()
    private val splashViewModel: SplashViewModel by viewModels()
    private val dashBoardViewModel: DashBoardViewModel by viewModels()

    private lateinit var signInLauncher: ActivityResultLauncher<Intent>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()

        // Registro del launcher para el resultado de inicio de sesión
        signInLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val data: Intent? = result.data
                val account = GoogleSignIn.getSignedInAccountFromIntent(data).result
                newUserViewmodel.handleGoogleSignInResult(account) // Maneja el resultado en el ViewModel
            }else{
                newUserViewmodel.handleGoogleSignInResult(null)
            }
        }

        setContent {
            MyGainsTheme {
                val navigationController= rememberNavController()
                NavHost(navController = navigationController, startDestination = "splash") {
                    composable(Routes.Splash.routes){ SplashScreenComposable(navHostController = navigationController, splashViewModel = splashViewModel) }
                    composable(Routes.Home.routes){ MyDashBoard(nav = navigationController,dashBoardViewModel) }
                    composable(Routes.Login.routes){ LoginScreen(loginViewModel = logingViewmodel, nav = navigationController ) }
                    composable(Routes.Perfil.routes){ UserInfoComposable(nav = navigationController, userInfoViewModel = userInfoViewModel) }
                    composable(Routes.NewUser.routes){ NewUserComposable(newUserViewModel = newUserViewmodel, navigationController, onSignInClick = { newUserViewmodel.signInWithGoogle(this@MainActivity,signInLauncher, this@MainActivity)  }) }
                    composable(route = Routes.Plan.routes) { backStackEntry->
                        PlanCompossable(nav = navigationController)
                    }
                    composable(Routes.ExcercisesPlan.routes){backStackEntry->
                        ExcercisesPlanCompossable(nav = navigationController, backStackEntry.arguments?.getString("date").orEmpty())
                    }

                    composable(Routes.GainsScanner.routes){ ScanProductComposable()}

                }
            }
        }

    }

}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyGainsTheme {
        Greeting("Android")
    }
}