package com.example.mygains.dashboard.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.mygains.R
import com.example.mygains.dashboard.data.models.ShortCutsItem
import com.example.mygains.dashboard.ui.states.DashBoardUIState
import com.example.mygains.extras.dimensions.Dimensions
import com.example.mygains.extras.globalcomponents.loadercomponent.LoaderComponent
import com.example.mygains.userinfo.data.models.UserDataModel

@Composable
fun NewHomeScreen(nav: NavHostController) {


    val dashBoardViewModel: DashBoardViewModel = hiltViewModel()
    //val userData: UserData by dashBoardViewModel.userDataLive.observeAsState(initial = UserData())
    val uiState  by dashBoardViewModel.uiState.collectAsState()
    val scrollState= rememberLazyListState()

    val animateHeader by remember {
        derivedStateOf { scrollState.firstVisibleItemScrollOffset > 0 }
    }


    when(val state = uiState){
        is DashBoardUIState.Error -> {}
        is DashBoardUIState.Loading -> { LoaderComponent()}
        is DashBoardUIState.Succes -> {
            Box(modifier = Modifier.fillMaxSize()){

                AnimatedGradientBox(animateHeader)

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    state = scrollState
                )
                {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                        ){
                            HeaderHomeScreen(state.homeScreenModel.userDataModel, dashBoardViewModel,animateHeader)
                        }
                    }

                    item {
                        Column(
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .fillMaxWidth()
                                .clip(
                                    RoundedCornerShape(
                                        30.dp
                                    )
                                )
                                .background(Color.White)
                        )
                        {
                            BodyHomeScreen(nav, animateHeader)
                        }
                    }

                }
            }
        }
    }

}


@Composable
fun BodyHomeScreen(nav: NavHostController, animateHeader: Boolean) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)

    ){
        Column(
            Modifier
                .fillMaxWidth()
                .padding(8.dp)) {
            Text(
                text = "GYMRAT",
                fontSize = 24.sp,
                fontFamily = FontFamily(Font( R.font.montserratbold))
            )
            HorizontalDivider(modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 16.dp))
            ForTodaySection()
            HorizontalDivider(modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp))
            ShortCutsSection(nav = nav,animateHeader)
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ShortCutsSection(nav: NavHostController, animateHeader: Boolean) {

    val configuration = LocalConfiguration.current
    val windowSize = Dimensions.WindowSize.fromWidth(configuration.screenWidthDp)
    val dimensions = Dimensions.AppDimensions.fromWindowSize(windowSize)



    var shortCutsList = mutableListOf(
        ShortCutsItem.ScannerShortcut(),
        ShortCutsItem.StatsShortcut(),
        ShortCutsItem.StatsShortcut(),
        ShortCutsItem.StatsShortcut(),
        ShortCutsItem.ParchuesShortcut(),
        ShortCutsItem.RatsShortcut()
    )

    val height by animateDpAsState(
        targetValue = if (animateHeader) dimensions.cardHeight else (dimensions.cardHeight.value.toInt()-20).dp,
        animationSpec = tween(durationMillis = 500)
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = dimensions.spacing)
    )
    {
        Text(
            text = "ACCIONES  RAPIDAS",
            fontSize = 18.sp,
            fontFamily = FontFamily(Font( R.font.montserratbold))
        )

        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = dimensions.spacing),
            maxItemsInEachRow = 2,
        ) {
            shortCutsList.forEach { shortcut ->
                Box(
                    modifier = Modifier
                        .weight(1f, fill = true)
                        .padding(dimensions.spacing)
                        .size(height)
                        .clickable {
                            nav.navigate(shortcut.route)
                        }
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxSize(),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 4.dp,
                            pressedElevation = 8.dp
                        ),
                        colors = CardColors(
                            containerColor = colorResource(id = shortcut.color),
                            contentColor = Color.Black,
                            disabledContentColor = Color.White,
                            disabledContainerColor = Color.LightGray
                        )
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                tint = colorResource(id = shortcut.iconColor),
                                modifier = Modifier.size(40.dp),
                                painter = painterResource(id = shortcut.icon),
                                contentDescription = "scannnerIcon"
                            )

                            AnimatedVisibility(visible = animateHeader) {
                                Text(
                                    text = shortcut.title,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(vertical = dimensions.spacing),
                                    fontFamily = FontFamily(Font(R.font.montserratbold))
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ForTodaySection() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(12.dp),
                clip = true
            )
            .background(colorResource(id = R.color.gray_claro))
    )
    {
        Column(
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "Entrenamiento de hoy",
                fontFamily = FontFamily(Font( R.font.montserratbold))

            )
            Text(
                text = "Pecho y hombros",
                fontFamily = FontFamily(Font( R.font.montserratregular))

            )
            //lista breve resumen volumen de entrenamiento rms destacados en esos grupos musculares
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
                    text = "Ver rutina",
                    fontFamily = FontFamily(Font( R.font.montserratbold))
                )
            }
        }
    }
}

@Composable
fun HeaderHomeScreen(
    userDataModel: UserDataModel,
    dashBoardViewModel: DashBoardViewModel,
    animateHeader: Boolean
) {

    val iconSize by animateDpAsState(
        targetValue = if (animateHeader) 0.dp else 24.dp,
        animationSpec = tween(500)
    )
    //para cuando tenga ntificaciones
    /*val iconSize by animateDpAsState(
        targetValue = if (animateHeader) 0.dp else 24.dp,
        animationSpec = repeatable(
            iterations = 3,                      // Número de repeticiones
            animation = tween(durationMillis = 300), // Animación a repetir
            repeatMode = RepeatMode.Reverse      // Modo de repetición (Restart o Reverse)
        )
    )*/

    val fontSizeValue by animateFloatAsState(
        targetValue = if (animateHeader) 0f else 14f,
        animationSpec = snap(delayMillis = 10)
    )

    // Convertimos el float a sp
    val fontSize = fontSizeValue.sp
    val motivationText = rememberSaveable{ mutableStateOf( dashBoardViewModel.getMotivationText()) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
    )
    {
        Column(Modifier.weight(1f)) {
            Text(
                text = "Hola de nuevo ${userDataModel.name?:""}!",
                fontSize = 18.sp,
                fontFamily = FontFamily(Font(R.font.montserratbold))
            )
            Text(
                fontSize = fontSize,
                modifier = Modifier.padding(vertical = 8.dp),
                text = motivationText.value,
                fontFamily = FontFamily(Font(R.font.montserratregular))
            )
        }

        Icon(
            modifier = Modifier.size(iconSize),
            painter = painterResource(id = R.drawable.campana_config_icon),
            contentDescription ="notification"
        )

    }

}


@Composable
fun AnimatedGradientBox(animateHeader: Boolean) {
    // Definimos los colores iniciales y finales para la animación
    val startColor1 = Color(0xFFFCE5D8)
    val endColor1 = Color(0xFFCA5300)

    // Colores alternativos para el estado animado
    val startColor2 = Color(0xFFE8F0FF) // Puedes cambiar estos colores
    val endColor2 = Color(0xFF0052CA) // Puedes cambiar estos colores

    // Animamos la transición entre colores
    val animatedStartColor by animateColorAsState(
        targetValue = if (animateHeader) startColor2 else startColor1,
        animationSpec = tween(durationMillis = 500)
    )

    val animatedEndColor by animateColorAsState(
        targetValue = if (animateHeader) endColor2 else endColor1,
        animationSpec = tween(durationMillis = 500)
    )

    // También animamos la altura
    val height by animateDpAsState(
        targetValue = if (animateHeader) 100.dp else 200.dp,
        animationSpec = tween(durationMillis = 500)
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .clip(
                RoundedCornerShape(
                    topStart = 0.dp,
                    topEnd = 0.dp,
                    bottomStart = 30.dp,
                    bottomEnd = 30.dp
                )
            )
            .background(
                brush = Brush.verticalGradient(
                    colorStops = arrayOf(
                        0.4f to animatedStartColor,
                        1f to animatedEndColor
                    )
                )
            )
    )
}
