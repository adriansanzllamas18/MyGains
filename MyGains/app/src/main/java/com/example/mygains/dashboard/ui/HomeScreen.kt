package com.example.mygains.dashboard.ui

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
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
import com.example.mygains.extras.navigationroutes.Routes
import com.example.mygains.navigation.AfterAuthNavigationWrapper
import com.example.mygains.userinfo.data.models.UserData


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {

    val navHostController= rememberNavController()

    var dashBoardViewModel:DashBoardViewModel= hiltViewModel()
    val imageUser  by dashBoardViewModel.userDataLive.observeAsState(UserData())
    val listScreens= mutableListOf(Routes.Perfil.routes,Routes.Home.routes,Routes.Plan.routes)
    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val currentDestination= navBackStackEntry ?.destination

    Scaffold(bottomBar = {
        if (currentDestination?.route in listScreens)
        MyBottomNavigation(navHostController,imageUser.image?:"",currentDestination)
    },
        topBar = {if (currentDestination?.route !in listScreens)
            CustomTopAppBar(navHostController,currentDestination)
        }
    ) {innerPading->
        AfterAuthNavigationWrapper(nav = navHostController, modifier = Modifier
            .fillMaxSize()
            .padding(innerPading))
    }
}

@Composable
fun MyBottomNavigation(nav: NavHostController, image: String, currentDestination: NavDestination?) {


    val itemList = mutableListOf(
        BottomBarItem.Profile(image),
        BottomBarItem.Home(),
        BottomBarItem.Plan()
    )
    val floatingItemList= mutableListOf(
        BottomFloatingItemBar.Profile(image),
        BottomFloatingItemBar.Home(),
        BottomFloatingItemBar.Plan()
    )

    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.TopCenter
    ) {
        // FAB dinámico basado en currentComposable
        floatingItemList.forEach {
                if (currentDestination?.route== it.rout){
                    FloatingActionButton(
                        onClick = { },
                        modifier = it.modifier.align(it.alignment),
                        containerColor = MaterialTheme.colorScheme.primary,
                        shape = CircleShape
                    ){
                        it.icon.invoke()
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
                itemList.forEach {
                    if (currentDestination?.route == it.rout) {
                        NavigationBarItem(
                            selected = false,
                            onClick = { },
                            icon = { Box(modifier = Modifier.size(24.dp)) }
                        )
                    } else {
                        NavigationBarItem(
                            selected = false,
                            onClick = {
                                nav.navigate(it.rout) {
                                    // Solo elimina pantallas de la pila que estén por encima
                                    nav.graph.startDestinationRoute?.let { rout ->
                                        popUpTo(rout) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                                nav.currentBackStackEntry?.destination?.hierarchy?.forEach {
                                    Log.i("entry", it.route.toString())
                                }

                            },

                            icon = it.icon
                        )
                    }
                }
            }
        }
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
        else->{""}
    }
}
private fun setTopBarActionBack(rout:String, nav: NavController):()->Unit{
    return when(rout){
        Routes.InfoTypeOfWorkout.routes -> {{}}
        Routes.TypesWorkOuts.routes -> {{}}
        Routes.Exercises.routes -> {{}}
        else->{{nav.popBackStack()}}
    }
}