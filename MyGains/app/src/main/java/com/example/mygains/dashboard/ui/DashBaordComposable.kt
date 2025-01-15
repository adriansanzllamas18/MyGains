package com.example.mygains.dashboard.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.mygains.R
import com.example.mygains.extras.navigationroutes.Routes
import com.example.mygains.login.ui.LoginDivider
import com.example.mygains.plan.ui.PlanViewModel
import com.example.mygains.userinfo.data.UserData


@Composable
fun MyDashBoard(nav:NavHostController){

    val dashBoardViewModel: DashBoardViewModel = hiltViewModel()

    //todo cambiar al init del viewmodel
    dashBoardViewModel.getUserData()

    val userData: UserData by dashBoardViewModel.userDataLive.observeAsState(initial = UserData())

    ConstraintLayout(modifier = Modifier
        .fillMaxSize()
        .windowInsetsPadding(WindowInsets.systemBars)) {
        val (bottomBar,container) = createRefs()
        ConstraintLayout(modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.systemBars)
            .padding(8.dp)
            .constrainAs(container) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(bottomBar.top)
            }) {
            val (header, body,divider) = createRefs()
            MyDashBoardHeader(modifier= Modifier
                .constrainAs(header) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .padding(24.dp), userData = userData, viewModel = dashBoardViewModel)

            LoginDivider(modifier = Modifier.constrainAs(divider){
                top.linkTo(header.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            })
            MyDashBoar(modifier= Modifier
                .constrainAs(body) {
                    top.linkTo(divider.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .padding(16.dp),nav)

        }
        MyBottomNavigation(modifier=Modifier.constrainAs(bottomBar){
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }, nav, userData =userData  )
    }


}

@Composable
fun MyDashBoar(modifier: Modifier,nav: NavHostController) {
    Box(modifier) {
        ConstraintLayout(Modifier.fillMaxSize()) {
            val (dailyPlan, title,counter, scanner) = createRefs()

            Text(text = "Tu rutina de hoy", fontSize = 18.sp,fontFamily = FontFamily(Font(R.font.poppinsbold)), modifier = Modifier
                .constrainAs(title) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)

                }
                .padding(bottom = 8.dp))

            MyDailyPlan(Modifier.constrainAs(dailyPlan){
                top.linkTo(title.bottom)
                start.linkTo(parent.start)
            }, mutableListOf())

            CaloriesAndSteps(modifier = Modifier
                .constrainAs(counter) {
                    top.linkTo(dailyPlan.bottom)
                    start.linkTo(parent.start)
                }
                .padding(top = 16.dp))

            MyGainsScaner(modifier = Modifier
                .constrainAs(scanner) {
                    top.linkTo(counter.bottom)
                    start.linkTo(parent.start)
                }
                .padding(top = 16.dp), nav =nav )
        }

    }
}

@Composable
fun MyGainsScaner(modifier: Modifier, nav: NavHostController) {

    // Carga la composición del recurso Lottie
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.scandasboardanimation))

    var isPlaying by remember {
        mutableStateOf(true)
    }
    // Animación con progreso controlado
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever,
        isPlaying = isPlaying // Añadido el control de reproducción
    )

    Box(modifier) {
        Card(
            modifier= Modifier
                .fillMaxWidth()
                .height(100.dp)
                .clickable { nav.navigate(Routes.GainsScanner.routes) },
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp), colors = CardDefaults.cardColors(containerColor = Color(0xFFFCE5D8))
        ) {
            Row {
                LottieAnimation(
                    modifier = Modifier.padding(8.dp),
                    composition = composition,
                    progress = progress,
                )
                Text(text = "Escanea tus alimentos",
                    modifier = Modifier.align(Alignment.CenterVertically),
                    fontWeight = FontWeight.Bold,
                )
            }
            

        }
    }

}

@Composable
fun MyDailyPlan(modifier: Modifier, mutableList: MutableList<String>) {

    val pagerState = rememberPagerState(pageCount = {
        7
    }, initialPage = 5)

   HorizontalPager(state = pagerState, pageSpacing = 8.dp, modifier = modifier) {
       
       Card() {
           ConstraintLayout(
               Modifier
                   .fillMaxWidth()
                   .background(Color(0xFFFCE5D8))
                   .padding(4.dp)) {
               val (dailyPlan, day,image,before,after) = createRefs()


               Icon(imageVector =  Icons.Filled.KeyboardArrowLeft, contentDescription = "before",
                   Modifier
                       .constrainAs(before) {
                           end.linkTo(day.start)
                           top.linkTo(parent.top)
                       }
                       .padding(end = 8.dp),
                   colorResource(id = R.color.orange) )
               Text(text = "Lunes 1",Modifier.constrainAs(day){
                   top.linkTo(parent.top)
                   end.linkTo(parent.end)
                   start.linkTo(parent.start)
               })
               Image(painter = painterResource(id =R.drawable.capacitacion ) , contentDescription = "image",
                   Modifier
                       .size(80.dp)
                       .padding(8.dp)
                       .constrainAs(image) {
                           start.linkTo(parent.start)
                           top.linkTo(day.bottom)
                       })

               LazyColumn(
                   Modifier
                       .constrainAs(dailyPlan) {
                           start.linkTo(image.end)
                           top.linkTo(day.bottom)
                       }
                       .padding(8.dp)) {
                   items(mutableList) { item ->
                       Text(text = item)
                   }
               }
               Icon(imageVector =  Icons.Filled.KeyboardArrowRight, contentDescription = "before",
                   Modifier
                       .constrainAs(after) {
                           start.linkTo(day.end)
                           top.linkTo(parent.top)
                       }
                       .padding(start = 8.dp),
                   colorResource(id = R.color.orange) )
           }
       }
   }

}


@Composable
fun MyDashBoardHeader(modifier: Modifier, viewModel: DashBoardViewModel, userData: UserData) {

    val motivationText = rememberSaveable{ mutableStateOf( viewModel.getMotivationText()) }

    Box(modifier.fillMaxWidth()) {
        ConstraintLayout(Modifier.fillMaxWidth()) {
            val (name, buzon,motivaion,handicon) = createRefs()
            Text(text = "Hola ${userData.name}!", fontSize = 28.sp,fontFamily = FontFamily(Font(R.font.poppinsbold)), modifier = Modifier.constrainAs(name){
                start.linkTo(parent.start)
                top.linkTo(parent.top)
            })

            Icon(painter = painterResource(id = R.drawable.inbox), contentDescription ="box",
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
        }
    }
}

@Composable
fun CaloriesAndSteps(modifier: Modifier){
    Row(modifier = modifier){
            Card(
                Modifier
                    .weight(1f)
                    .padding(end = 4.dp), elevation = CardDefaults.cardElevation(defaultElevation = 8.dp), colors = CardDefaults.cardColors(containerColor = Color(0xFFFCE5D8))) {
                Text(text = "Pasos",
                    Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 8.dp),
                    color = Color.Black,fontFamily = FontFamily(Font(R.font.poppinsbold)))
                Text(text = "20000000",
                    Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 8.dp, bottom = 8.dp),
                    color = Color.Black,fontFamily = FontFamily(Font(R.font.poppinsbold)))
            }

            Card(
                Modifier
                    .weight(1f)
                    .padding(start = 4.dp), elevation = CardDefaults.cardElevation(defaultElevation = 8.dp), colors = CardDefaults.cardColors(containerColor = Color(0xFFFCE5D8))) {
                Text(text = "Caloías",
                    Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 8.dp),
                    color = Color.Black,fontFamily = FontFamily(Font(R.font.poppinsbold)))
                Text(text = "8000kcl",
                    Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 8.dp, bottom = 8.dp),
                    color = Color.Black,fontFamily = FontFamily(Font(R.font.poppinsbold)))
            }
        }


}

@Composable
fun MyBottomNavigation(modifier: Modifier,nav: NavHostController, userData: UserData) {

    Surface(modifier, shape = RoundedCornerShape(topStart = 16.dp)) {
        NavigationBar( containerColor = Color(0xFFFCE5D8) ) {

            if (userData.image?.isNotEmpty() == true){
                NavigationBarItem(selected = false , onClick = { nav.navigate(Routes.Perfil.routes)
                }, icon = { Image(modifier = Modifier.clip(CircleShape),
                    contentScale = ContentScale.Crop,
                    painter = rememberAsyncImagePainter(userData.image),
                    contentDescription = "perfil"
                )
                })
            }else{
                NavigationBarItem(selected = false , onClick = { nav.navigate(Routes.Perfil.routes)
                }, icon = { Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "perfil"
                )})
            }

            NavigationBarItem(selected = false , onClick = {

            }, icon = { Icon(
                imageVector = Icons.Default.Home,
                contentDescription = "home"
            )})
            NavigationBarItem(selected = false , onClick = { nav.navigate(Routes.Plan.routes) }, icon = { Icon(
                imageVector = Icons.Default.DateRange,
                contentDescription = "plan"
            )})
        }
    }

}


