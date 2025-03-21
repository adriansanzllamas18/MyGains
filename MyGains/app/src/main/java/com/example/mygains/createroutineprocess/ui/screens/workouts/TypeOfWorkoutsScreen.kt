package com.example.mygains.createroutineprocess.ui.screens.workouts

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.mygains.createroutineprocess.data.models.TypeOfWorkOutModel
import com.example.mygains.createroutineprocess.ui.screens.stregnth.CreateRoutineViewModel
import com.example.mygains.extras.navigationroutes.Routes
import dagger.hilt.android.lifecycle.HiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TypeOfWorkoutsScreen(nav: NavHostController) {

    val typeOfWorkoutsViewModel:TypeOfWorkoutsViewModel = hiltViewModel()
    val workouts by typeOfWorkoutsViewModel.workoutsLive.observeAsState(initial = mutableListOf())

    LaunchedEffect(Unit) {
        typeOfWorkoutsViewModel.getAllWorkOuts()
    }

    PullToRefreshBox(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        isRefreshing = false,
        onRefresh = {},
    )
    {
        LazyColumn {
            item {
                MyTypeOfTrainingBody(workouts,nav)
            }
        }
    }
}

@Composable
fun MyTypeOfTrainingBody(data: MutableList<TypeOfWorkOutModel>,nav: NavHostController) {

    if (data.isEmpty()){
        ShimmerCard()
    }else{
        data.forEach {typeofworkoutmodel->
            Card(modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .clickable {
                    if (!typeofworkoutmodel.id_workout.isNullOrEmpty())
                        nav.navigate(Routes.InfoTypeOfWorkout.createRout(typeofworkoutmodel.id_workout!!)){
                            launchSingleTop =  true
                        }
                }
                .padding(vertical = 16.dp),
                shape = RoundedCornerShape(12)
            ) {
                Box(contentAlignment = Alignment.BottomStart){
                    AsyncImage(
                        model = typeofworkoutmodel.image,
                        contentDescription = "Character Of the day",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                    Box(
                        Modifier
                            .fillMaxSize()
                            .background(
                                Brush.horizontalGradient(
                                    0f to Color.Black.copy(alpha = 0.9f),
                                    1f to Color.White.copy(alpha = 0f)
                                )
                            ))
                    Text(typeofworkoutmodel.name?:"",
                        fontSize = 40.sp,
                        maxLines = 1,
                        minLines = 1,
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        overflow = TextOverflow.Ellipsis, // esto significa que si no entra el texto va a poner punto punto punto
                        modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp)
                    )
                }
            }

        }
    }


}



@Composable
fun ShimmerCard() {
    val transition = rememberInfiniteTransition()
    val shimmerOffset by transition.animateFloat(
        initialValue = -300f,
        targetValue = 300f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = ""
    )

    val brush = Brush.linearGradient(
        colors = listOf(
            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
        ),
        start = Offset(x = shimmerOffset, y = 0f),
        end = Offset(x = shimmerOffset + 300f, y = 0f)
    )
    var animatedView by remember{ mutableStateOf(false) }

    LaunchedEffect(Unit){
        animatedView = true
    }

    (1..3).forEach {

        AnimatedVisibility(
            visible = animatedView,
            enter = slideInVertically(
                initialOffsetY = { 50 * (it + 1) } // Cada tarjeta baja desde una posición diferente
            ) + fadeIn(animationSpec = tween(500)),
            exit = fadeOut(animationSpec = tween(300))
        ) {

            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {

                    Column(modifier = Modifier.padding(16.dp)) {

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .background(brush, shape = RoundedCornerShape(8.dp))
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                                .background(brush, shape = RoundedCornerShape(8.dp))
                        )
                    }
            }
        }

    }

}


