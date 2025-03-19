package com.example.mygains.createroutineprocess.ui.screens.stregnth

import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.mygains.createroutineprocess.data.models.InfoTypeOfWorkOutModel
import com.example.mygains.extras.navigationroutes.Routes


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfoTypeOfWorkout(
    nav: NavHostController,
    workout_id: String,
    createRoutineViewModel: CreateRoutineViewModel
) {

    val list by createRoutineViewModel.infoWorkoutsLive.observeAsState(initial = mutableListOf())

    LaunchedEffect(Unit) {
        createRoutineViewModel.getAllInfoWorkOuts(workout_id)
    }

    PullToRefreshBox(
        isRefreshing = false,
        onRefresh = { /*TODO*/ },
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column {
            MyBodyInfoWorkOut(list,nav)
        }

    }
}

@Composable
fun MyBodyInfoWorkOut(infolist:MutableList<InfoTypeOfWorkOutModel>,nav: NavHostController) {

    LazyVerticalGrid(columns = GridCells.Fixed(2)) {
        items(infolist){data->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(vertical = 16.dp, horizontal = 8.dp),
                shape = RoundedCornerShape(12),
                onClick = {nav.navigate(Routes.Exercises.createRout(data.muscle_id?:""))}
            ) {
                Box(contentAlignment = Alignment.BottomStart) {
                    AsyncImage(
                        model = data.image,
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
                            )
                    )

                    Text(
                        data.muscleName ?: "",
                        fontSize = 20.sp,
                        maxLines = 1,
                        minLines = 1,
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        modifier = Modifier
                            .padding(horizontal = 24.dp, vertical = 16.dp)
                            .basicMarquee()
                    )
                }
            }
        }
    }
}



