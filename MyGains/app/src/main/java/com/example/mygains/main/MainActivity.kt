package com.example.mygains.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.mygains.R
import com.example.mygains.extras.navigationroutes.Routes
import com.example.mygains.navigation.NavigationWrapper
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
    Scaffold(
        bottomBar = { MyBottomNavigation(navController)} // Agregamos la BottomBar
    ) { innerPadding ->
        NavigationWrapper(
            modifier = Modifier.padding(innerPadding),
            navigationController = navController
        )
    }
}

@Composable
fun MyBottomNavigation(nav: NavHostController) {
    var currentComposable by remember {
        mutableStateOf(Routes.Home.routes)
    }

    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.BottomCenter
    ) {
        // FAB dinámico basado en currentComposable
        when (currentComposable) {

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
                if (currentComposable == Routes.Perfil.routes) {
                    NavigationBarItem(
                        selected = false,
                        onClick = { },
                        icon = { Box(modifier = Modifier.size(24.dp)) }
                    )
                } else {
                    NavigationBarItem(
                        selected = false,
                        onClick = {
                            nav.navigate(Routes.Perfil.routes)
                            currentComposable = Routes.Perfil.routes
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
                if (currentComposable == Routes.Home.routes) {
                    NavigationBarItem(
                        selected = false,
                        onClick = { },
                        icon = { Box(modifier = Modifier.size(24.dp)) }
                    )
                } else {
                    NavigationBarItem(
                        selected = false,
                        onClick = {
                            nav.navigate(Routes.Home.routes)
                            currentComposable = Routes.Home.routes
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
                if (currentComposable == Routes.Plan.routes) {
                    NavigationBarItem(
                        selected = false,
                        onClick = { },
                        icon = { Box(modifier = Modifier.size(24.dp)) }
                    )
                } else {
                    NavigationBarItem(
                        selected = false,
                        onClick = {
                            nav.navigate(Routes.Plan.routes)
                            currentComposable = Routes.Plan.routes
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