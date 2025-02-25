package com.example.mygains.dashboard.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.mygains.R
import com.example.mygains.dashboard.data.models.CaloriesStats
import com.example.mygains.dashboard.data.models.MacronutrientStats
import com.example.mygains.dashboard.ui.components.BannerInfo
import com.example.mygains.dashboard.ui.components.CircularProgressData
import com.example.mygains.extras.navigationroutes.Routes
import com.example.mygains.extras.utils.FormatterUtils
import com.example.mygains.navigation.AfterAuthNavigationWrapper
import com.example.mygains.userinfo.data.models.UserData



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyDashBoard(nav: NavHostController) {

    val dashBoardViewModel: DashBoardViewModel = hiltViewModel()
    val userData: UserData by dashBoardViewModel.userDataLive.observeAsState(initial = UserData())
    val state= rememberPullToRefreshState()


    PullToRefreshBox(
            modifier = Modifier
                .fillMaxSize(),
            isRefreshing = false, // Simula el estado de carga
            state = state,
            onRefresh = {
                // TODO: Implementa la lógica de actualización
            }

        ) {
            // Estructura principal
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                LazyColumn(
                    modifier = Modifier
                        .weight(1f), // Esto hace que el LazyColumn ocupe  el espacio restante
                    state = rememberLazyListState()
                ) {
                    item {
                        MyDashBoardHeader(
                            modifier = Modifier
                                .padding(horizontal = 16.dp),
                            userData = userData,
                            viewModel = dashBoardViewModel
                        )
                    }
                    item {
                        MyDashBoardBody(
                            modifier = Modifier
                                .padding(16.dp),
                            nav = nav
                        )
                    }
                }
            }
        }
}


@Composable
fun MyDashBoardBody(modifier: Modifier,nav: NavHostController) {
    Box(modifier) {
        ConstraintLayout(Modifier.fillMaxSize()) {
            val (dailyPlan, title,currentTrain, gymrat) = createRefs()

            Text(text = "Hoy", fontSize = 24.sp,fontFamily = FontFamily(Font(R.font.poppinsbold)), modifier = Modifier
                .constrainAs(title) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)

                }
                .padding(bottom = 8.dp))

            MyDailyPlan(Modifier.constrainAs(dailyPlan){
                top.linkTo(title.bottom)
                start.linkTo(parent.start)
            }, mutableListOf())

            TodayTarget(
                Modifier
                    .constrainAs(currentTrain) {
                        top.linkTo(dailyPlan.bottom)
                        start.linkTo(parent.start)
                    }
                    .padding(horizontal = 8.dp, vertical = 16.dp))

            GymRatBasics(modifier = Modifier
                .constrainAs(gymrat) {
                    top.linkTo(currentTrain.bottom)
                    start.linkTo(parent.start)
                }
                .padding(top = 16.dp),nav)
        }

    }
}

@Composable
fun GymRatBasics(modifier: Modifier,nav: NavHostController){

    Column(modifier) {
        Text(
            text = "GymRat",
            fontSize = 24.sp,fontFamily = FontFamily(Font(R.font.poppinsbold)),
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Row {
            MyGainsScaner( nav = nav )
            MySuplementation()
        }


    }

}

@Composable
fun MySuplementation() {

}

@Composable
fun TodayTarget(modifier: Modifier) {

    Box(modifier) {
        BannerInfo(R.drawable.banner_dashboard_calendar,{})
    }
}

@Composable
fun MyGainsScaner( nav: NavHostController) {

    // Carga la composición del recurso Lottie
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.scanner_dashboard))

    var isPlaying by remember {
        mutableStateOf(true)
    }
    // Animación con progreso controlado
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever,
        isPlaying = isPlaying // Añadido el control de reproducción
    )

    Box(Modifier.fillMaxWidth()) {
        Card(
            modifier= Modifier
                .clickable { nav.navigate(Routes.GainsScanner.routes)},
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFCE5D8))
        ) {
            Row(
                Modifier.padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                LottieAnimation(
                    modifier = Modifier
                        .padding(8.dp)
                        .size(50.dp),
                    composition = composition,
                    progress = progress,
                )

                Text(
                    text = "Escanear alimentos",
                    fontSize = 16.sp ,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                )
            }
        }
    }

}

@Composable
fun MyDailyPlan(modifier: Modifier, mutableList: MutableList<Any>) {

    val stats = listOf(
        CaloriesStats(
            currentCalories = 1500,
            targetCalories = 2000,
            burnedCalories = 300,
            timeOfSleep = 7
        ),
        MacronutrientStats(
            currentFats = 50,
            targetFats = 70,
            currentProteins = 100,
            targetProteins = 150,
            currentCarbs = 200,
            targetCarbs = 250
        )
    )
    Column(modifier.fillMaxSize()) {

        HorizontalPager(state = rememberPagerState(pageCount = { stats.size }),
            Modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 16.dp)
        )
        {position ->

            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(end = 16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFCE5D8))
            ) {
                when(val stat = stats[position]){
                    is CaloriesStats -> CaloriesStatsCard(stat)
                    is MacronutrientStats-> MacronutrientStatsCard(stat)
                    else -> {}
                }
            }
        }

        Text(
            modifier = Modifier.padding(8.dp),
            text = "Siempre puedes modificar tus macros ajustandose a tus objetivos.",
            fontSize = 12.sp,
            color = Color.LightGray,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.SansSerif,
            fontStyle = FontStyle.Italic,
            textAlign = TextAlign.Center
            )
    }


}

@Composable
fun MacronutrientStatsCard(stats: MacronutrientStats) {

    Column(Modifier.padding(16.dp),)
        {
        Text("Macronutrientes", style = MaterialTheme.typography.titleMedium, color = Color.Black)
        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically, // Centrado vertical
            horizontalArrangement = Arrangement.Center) {
            CircularProgressData(current = stats.currentProteins?:0 , goal = stats.targetProteins?:0, type = "P", sizeCircle = 60)
            CircularProgressData(current =stats.currentCarbs?:0 , goal =stats.targetCarbs?:0 , type = "H", sizeCircle = 60)
            CircularProgressData(current = stats.currentFats?:0, goal = stats.targetFats?:0, type =  "G", sizeCircle = 60)
        }
    }
}


@Composable
fun CaloriesStatsCard(caloriesStats: CaloriesStats) {
    Column(
        Modifier
            .padding(16.dp)
            .fillMaxSize())
    {
        Text("Calorías", style = MaterialTheme.typography.titleMedium, color = Color.Black)
        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            CircularProgressData(current = caloriesStats.currentCalories?:0 , goal = caloriesStats.targetCalories?:0, type = "C", sizeCircle = 100)
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                CardStatsView(caloriesStats.currentCalories.toString(),"C")
                CardStatsView(caloriesStats.targetCalories.toString(),"T")
                CardStatsView(caloriesStats.burnedCalories.toString(),"E")
                CardStatsView(caloriesStats.timeOfSleep.toString(),"S")
            }
        }

    }
}

@Composable
fun CardStatsView(stat:String, type:String) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(bottom = 4.dp)) {
        Image(
            painter = painterResource(id = FormatterUtils().getImageCardByType(type)),
            contentDescription = "image",
            modifier = Modifier.size(20.dp)
        )
        Text(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .fillMaxWidth(),
            text = stat,
            color = Color.Black,
            fontSize = 14.sp,
            textAlign = TextAlign.Start)
    }
}


@Composable
fun MyDashBoardHeader(modifier: Modifier, viewModel: DashBoardViewModel, userData: UserData) {

    val motivationText = rememberSaveable{ mutableStateOf( viewModel.getMotivationText()) }

    Box(modifier.fillMaxWidth()) {
        ConstraintLayout(Modifier.fillMaxWidth()) {
            val (name, buzon,motivaion,handicon,divider) = createRefs()
            Text(text = "Hola ${userData.name}!", fontSize = 28.sp,fontFamily = FontFamily(Font(R.font.poppinsbold)), modifier = Modifier.constrainAs(name){
                start.linkTo(parent.start)
                top.linkTo(parent.top)
            })

            Icon(painter = painterResource(id = R.drawable.bandeja_de_entrada), contentDescription ="box",
                Modifier
                    .constrainAs(buzon) {
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                    }
                    .padding(top = 8.dp)
                    .size(20.dp), colorResource(id = R.color.black))

            Text(text = motivationText.value, modifier= Modifier.constrainAs(motivaion){
                top.linkTo(name.bottom)
                start.linkTo(parent.start)
            })

            Image(painter = painterResource(id = R.drawable.wave_9971673), contentDescription ="box",
                Modifier
                    .constrainAs(handicon) {
                        start.linkTo(name.end)
                        top.linkTo(name.top)
                    }
                    .padding(top = 8.dp)
                    .size(25.dp))

            HorizontalDivider(color = colorResource(id = R.color.orange_low), modifier = Modifier
                .constrainAs(divider) {
                    top.linkTo(motivaion.bottom)
                }
                .padding(vertical = 16.dp))
        }

    }
}




