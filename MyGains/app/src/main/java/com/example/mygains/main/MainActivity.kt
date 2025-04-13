package com.example.mygains.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.mygains.R
import com.example.mygains.dashboard.data.models.BottomBarItem
import com.example.mygains.dashboard.data.models.BottomFloatingItemBar
import com.example.mygains.dashboard.ui.DashBoardViewModel
import com.example.mygains.extras.globalcomponents.CustomBottomNavigationBar
import com.example.mygains.extras.navigationroutes.Routes
import com.example.mygains.navigation.AfterAuthNavigationWrapper
import com.example.mygains.navigation.BeforeAuthNavigationWrapper
import com.example.mygains.navigation.GlobalNavigationWrapper
import com.example.mygains.splashscreen.ui.SplashScreenComposable
import com.example.mygains.ui.theme.MyGainsTheme
import com.example.mygains.userinfo.data.models.UserData
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

    var dashBoardViewModel: DashBoardViewModel = hiltViewModel()
    val imageUser  by dashBoardViewModel.userDataLive.observeAsState(UserData())

    val listScreensTopBar= mutableListOf(Routes.NewUser.routes,Routes.GainsScanner.routes,Routes.InfoTypeOfWorkout.routes,
        Routes.TypesWorkOuts.routes,Routes.Exercises.routes,Routes.Configuration.routes)

    val listScreensBottomBar = mutableListOf(Routes.Perfil.routes,Routes.Home.routes,Routes.Plan.routes)


    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination= navBackStackEntry ?.destination

    val scrollstate = rememberScrollState()


    Scaffold(
        modifier = Modifier.systemBarsPadding(),
        bottomBar = {
        if (currentDestination?.route in listScreensBottomBar)
            CustomBottomNavigationBar(navController)
    },
        topBar = {
            if (currentDestination?.route in listScreensTopBar)
            CustomTopAppBar(navController,currentDestination)
        }
    ) {innerPading->
        GlobalNavigationWrapper(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPading),
            nav = navController
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar(navController: NavController, currentDestination: NavDestination?) {
    TopAppBar(
        navigationIcon = {
            IconButton(modifier = Modifier.size(24.dp), onClick = { setTopBarActionBack(currentDestination?.route?:"",navController).invoke()}) {
                Icon(painter = painterResource(id = R.drawable.angulo_izquierdo), contentDescription = "Back")
            }
        },
        title = {
            Text(
                text = setTopBarByCurrentScreen(currentDestination?.route?:""),
                Modifier.padding(start = 16.dp),
                fontSize = 24.sp
            )
        },
        modifier = Modifier.fillMaxWidth()
    )
}

private fun setTopBarByCurrentScreen(rout:String):String{
    return when(rout){
        Routes.InfoTypeOfWorkout.routes -> "Grupos Musculares"
        Routes.TypesWorkOuts.routes -> "Entrenos"
        Routes.Exercises.routes -> "Ejercicios"
        Routes.Configuration.routes -> "Configuración"
        else->{""}
    }
}
private fun setTopBarActionBack(rout: String, nav: NavController): () -> Unit {
    return when(rout) {
        Routes.InfoTypeOfWorkout.routes -> {{
            // Navegación normal hacia atrás
            nav.popBackStack()
        }}
        Routes.TypesWorkOuts.routes -> {{
            // Al volver a TypesWorkOuts, elimina InfoType y Exercise y la misma, tambn guardamos el estado para que no se haga la llamada todo el rato
            nav.popBackStack(Routes.TypesWorkOuts.routes, inclusive = true,saveState = true)
        }}
        Routes.Exercises.routes -> {{
            // Navegación normal hacia atrás
            nav.popBackStack()
        }}
        else -> {{
            nav.popBackStack()
        }}
    }
}

