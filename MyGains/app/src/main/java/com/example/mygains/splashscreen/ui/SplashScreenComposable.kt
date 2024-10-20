package com.example.mygains.splashscreen.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.example.mygains.R
import com.example.mygains.extras.navigationroutes.Routes
import kotlinx.coroutines.delay


@Composable
fun SplashScreenComposable(navHostController: NavHostController, splashViewModel: SplashViewModel) {

    val isAlreadyLoged:Boolean?  by splashViewModel.isAlreadyLogedLive.observeAsState(initial = null)
    splashViewModel.isAlreadyLoged()
    LaunchedEffect(isAlreadyLoged) {

        if (isAlreadyLoged==true){
            delay(2000)
            navHostController.navigate(Routes.Home.routes){
                popUpTo(Routes.Splash.routes) { inclusive = true }
            }
        }else if (isAlreadyLoged==false){
            delay(2000)
            navHostController.navigate(Routes.Login.routes){
                popUpTo(Routes.Splash.routes) { inclusive = true }
            }
        }

    }
    ConstraintLayout(
        Modifier
            .background(Color(0xFFFCE5D8))
            .fillMaxSize()) {
        val text= createRef()
        Text(text = "MyGains", fontSize = 34.sp, fontFamily = FontFamily(Font(R.font.poppins)), color = Color(0xFFCA5300)
            , modifier = Modifier.constrainAs(text){
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
            })
    }
}