package com.example.mygains.userinfo.ui.screens

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.mygains.R
import com.example.mygains.configuration.data.model.ConfigurationItemModel
import com.example.mygains.userinfo.ui.states.UserProfileHealthSectionUIState
import com.example.mygains.extras.globalcomponents.ItemListComponent
import com.example.mygains.extras.globalcomponents.loadercomponent.LoaderComponent
import com.example.mygains.userinfo.data.models.UserNutritionDataModel
import com.example.mygains.userinfo.ui.UserInfoViewModel
import com.example.mygains.userinfo.ui.states.UserNutritionGoalsSectionUIState
import com.example.mygains.userinfo.ui.states.UserProfileSectionUIState

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun NewUserInfoScreen() {

    val userInfoViewModel :UserInfoViewModel = hiltViewModel()
    val refreshState by userInfoViewModel.refreshingLife.observeAsState(initial = false)


    PullToRefreshBox(
        isRefreshing = refreshState ,
        onRefresh = {
           userInfoViewModel.refreshData()
        }) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp)
        )
        {
            item {
                UserInfoHeaderSection(userInfoViewModel)
            }

            item {
                MyHealthSection(userInfoViewModel)
            }
            item {
                MyNutritionSection(userInfoViewModel)
            }
            item {
                ItemListComponent(
                    ConfigurationItemModel(leftIcon = R.drawable.configuration_icon,
                        text = "Configuración",
                        action = {
                            // nav.navigate(Routes.Configuration.routes)
                        })
                )
            }
        }
    }

}

@Composable
fun MyNutritionSection(userInfoViewModel: UserInfoViewModel) {


    val nutritionSectionState  by userInfoViewModel.nutritionSectonSate.collectAsState()
    var userNutritionData by remember {
        mutableStateOf(UserNutritionDataModel())
    }

    when(val state = nutritionSectionState ){
        is UserNutritionGoalsSectionUIState.Loading->{

        }
        is UserNutritionGoalsSectionUIState.Succes->{
            userNutritionData = state.userNutritionData
        }
        is UserNutritionGoalsSectionUIState.Error->{}
        is UserNutritionGoalsSectionUIState.NoDataYet->{}
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp))
    {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
        {
            Text(
                text = "Datos  Nutricionales",
                textAlign = TextAlign.Center,
                fontSize = 18.sp,
                fontFamily = FontFamily(Font( R.font.montserratbold))
            )

            CaloriesSection()
            ProgressBarNutritionIndicator(
                goalValue = userNutritionData.goalProtein,
                currentValue = userNutritionData.currentProtein ,
                Color(0xFF64B5F6))
            ProgressBarNutritionIndicator(
                goalValue = userNutritionData.goalCarbs,
                currentValue = userNutritionData.currentCarbs ,
                Color(0xFFFBC02D))
            ProgressBarNutritionIndicator(
                goalValue =  userNutritionData.goalFat,
                currentValue = userNutritionData.currentFat ,
                Color(0xFFFFAB91))
        }
    }
}



@Composable
fun CaloriesSection() {
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    )
    {
        Text(
            text = "2300",
            textAlign = TextAlign.Center,
            fontSize = 14.sp,
            fontFamily = FontFamily(Font( R.font.montserratregular))
        )
        Text(
            text = "/"+"2300",
            textAlign = TextAlign.Center,
            fontSize = 14.sp,
            fontFamily = FontFamily(Font( R.font.montserratbold))
        )
    }
}

@Composable
fun ProgressBarNutritionIndicator(goalValue:Double,currentValue:Double, color: Color){


    var initAnimation by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(Unit) {
        initAnimation = true
    }

    var percent = if (goalValue != 0.00) {
        (currentValue / goalValue).toFloat()
    } else {
        0f
    }

    //TODO EXTERNALIZAR A UNA FUNCION EN EL UTILS
    val progresanimation by animateFloatAsState(
        targetValue =  if (initAnimation || percent != 0f) percent.coerceIn(0f,1f) else 0f,
        label = "",
        animationSpec = tween(
            durationMillis = 2000,
            delayMillis = 1000,
            easing = FastOutSlowInEasing
        )
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    )
    {
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            Row {
                Text(
                    text = "Proteinas",
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font( R.font.montserratregular))
                )
                Text(
                    modifier = Modifier.weight(1f),
                    text = "$currentValue/$goalValue",
                    textAlign = TextAlign.End,
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font( R.font.montserratregular))
                )
            }

            LinearProgressIndicator(
                progress = { progresanimation },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .padding(horizontal = 8.dp),
                color = color,
                trackColor = Color(0xFFe0e0e0),
                strokeCap = StrokeCap.Butt,
                drawStopIndicator = {}
            )
        }
    }
}

@Composable
fun MyHealthSection(userInfoViewModel: UserInfoViewModel) {


    val uiState  by userInfoViewModel.healthSateSecton.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .shadow(8.dp, shape = RectangleShape)
            .background(
                colorResource(id = R.color.white)
            ))
    {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        )
        {
            Text(
                text = "Datos de salud",
                textAlign = TextAlign.Center,
                fontSize = 18.sp,
                fontFamily = FontFamily(Font(R.font.montserratbold))
            )

            when (val uiState = uiState) {
                is UserProfileHealthSectionUIState.Loading -> {
                    Box(modifier = Modifier.fillMaxWidth().size(180.dp)) {
                        LoaderComponent()
                    }
                }

                is UserProfileHealthSectionUIState.Succes -> {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    )
                    {
                        Column(
                            Modifier.weight(1f),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        )
                        {
                            Text(
                                text = uiState.userhealthdata.weight,
                                textAlign = TextAlign.Center,
                                fontSize = 18.sp,
                                fontFamily = FontFamily(Font(R.font.montserratbold))
                            )
                            Text(
                                text = "peso",
                                textAlign = TextAlign.Center,
                                fontSize = 14.sp,
                                fontFamily = FontFamily(Font(R.font.montserratregular))
                            )
                        }
                        Column(
                            modifier = Modifier.weight(1f),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        )
                        {
                            Text(
                                text = uiState.userhealthdata.bodyFat,
                                textAlign = TextAlign.Center,
                                fontSize = 18.sp,
                                fontFamily = FontFamily(Font(R.font.montserratbold))
                            )
                            Text(
                                text = "% graso",
                                textAlign = TextAlign.Center,
                                fontSize = 14.sp,
                                fontFamily = FontFamily(Font(R.font.montserratregular))
                            )
                        }
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    )
                    {
                        Column(
                            Modifier.weight(1f),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        )
                        {
                            Text(
                                text = uiState.userhealthdata.muscleMass,
                                textAlign = TextAlign.Center,
                                fontSize = 18.sp,
                                fontFamily = FontFamily(Font(R.font.montserratbold))
                            )
                            Text(
                                text = "% muscular",
                                textAlign = TextAlign.Center,
                                fontSize = 14.sp,
                                fontFamily = FontFamily(Font(R.font.montserratregular))
                            )
                        }
                        Column(
                            modifier = Modifier.weight(1f),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        )
                        {
                            Text(
                                text = uiState.userhealthdata.height,
                                textAlign = TextAlign.Center,
                                fontSize = 18.sp,
                                fontFamily = FontFamily(Font(R.font.montserratbold))
                            )

                            Text(
                                text = "altura",
                                textAlign = TextAlign.Center,
                                fontSize = 14.sp,
                                fontFamily = FontFamily(Font(R.font.montserratregular))
                            )
                        }
                    }
                }

                is UserProfileHealthSectionUIState.Error -> {
                    AsyncImage(modifier = Modifier
                        .size(180.dp)
                        .fillMaxWidth(),
                        model = R.drawable.nodata_health_image,
                        contentDescription = "imagenodata" )
                }

                is UserProfileHealthSectionUIState.NodataYet->{

                    AsyncImage(modifier = Modifier
                        .size(180.dp)
                        .fillMaxWidth(),
                        model = R.drawable.nodata_health_image,
                        contentDescription = "imagenodata" )
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        onClick = { /*TODO*/ },
                        colors = ButtonColors(containerColor = Color.Black,
                            contentColor = Color.White,
                            disabledContentColor = Color.White,
                            disabledContainerColor = Color.LightGray)
                    ) {
                        Text(
                            text = "Editar",
                            fontFamily = FontFamily(Font( R.font.montserratbold))
                        )
                    }
                }
            }
        }
    }

}

@Composable
fun UserInfoHeaderSection(userInfoViewModel: UserInfoViewModel) {

    val uiState  by userInfoViewModel.uiState.collectAsState()

    when(val state = uiState){
        is UserProfileSectionUIState.Error->{}
        is UserProfileSectionUIState.Succes->{

            val infiniteTransition = rememberInfiniteTransition(label = "AnimatedGradient")

            val color1 by infiniteTransition.animateColor(
                initialValue = Color(0xFFFCE5D8),
                targetValue = colorResource(id = R.color.blue_icon),
                animationSpec = infiniteRepeatable(
                    animation = tween(durationMillis = 3000, easing = LinearEasing),
                    repeatMode = RepeatMode.Reverse
                ), label = "Color1"
            )

            val color2 by infiniteTransition.animateColor(
                initialValue = Color(0xFFFCE5D8),
                targetValue = colorResource(id = R.color.orange),
                animationSpec = infiniteRepeatable(
                    animation = tween(durationMillis = 5000, easing = LinearEasing),
                    repeatMode = RepeatMode.Reverse
                ), label = "Color2"
            )

            val animatedBrush = Brush.linearGradient(
                colors = listOf(color1, color2),
                start = Offset.Zero,
                end = Offset.Infinite
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(animatedBrush),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            )
            {
                if (state.userData.image!!.isNotEmpty()){
                    Image(painter = rememberAsyncImagePainter(state.userData.image), contentDescription = "image",
                        Modifier
                            .padding(vertical = 8.dp)
                            .size(120.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop)
                }else{
                    Image(imageVector = Icons.Filled.AccountCircle, contentDescription = "image",
                        Modifier
                            .size(120.dp))
                }

                Text(
                    text = state.userData.name,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontSize = 28.sp,
                    fontFamily = FontFamily(Font( R.font.montserratbold))
                )
                Text(
                    text = state.userData.name ,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font( R.font.montserratregular))
                )
                Spacer(modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp))
            }
        }
        is UserProfileSectionUIState.Loading->{}
    }
}
