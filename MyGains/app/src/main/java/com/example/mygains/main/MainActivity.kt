package com.example.mygains.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.example.mygains.extras.navigationroutes.Routes
import com.example.mygains.navigation.BeforeAuthNavigationWrapper
import com.example.mygains.splashscreen.ui.SplashScreenComposable
import com.example.mygains.ui.theme.MyGainsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {



    private lateinit var signInLauncher: ActivityResultLauncher<Intent>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()

        // Registro del launcher para el resultado de inicio de sesión

        //Modificar inyectando el viewmodel dentro de esta activity
        /*
        signInLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val data: Intent? = result.data
                val account = GoogleSignIn.getSignedInAccountFromIntent(data).result
                newUserViewmodel.handleGoogleSignInResult(account) // Maneja el resultado en el ViewModel
            }else{
                newUserViewmodel.handleGoogleSignInResult(null)
            }

        }*/

        setContent {
            MyGainsTheme {
                MyApp()
            }
        }

    }

}

@Composable
fun MyApp() {

    val navController = rememberNavController()
    // El wrapper de antes de la autenticación (Splash, Login, etc.)

    BeforeAuthNavigationWrapper(
        modifier = Modifier.fillMaxWidth(),
        nav = navController
    )
}

