package com.example.mygains.createroutineprocess.ui.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Card
import androidx.compose.material3.FilterChip
import androidx.compose.material3.SelectableChipColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.mygains.R
import com.example.mygains.createroutineprocess.data.models.StrengthExerciseModel
import com.example.mygains.createroutineprocess.ui.CreateRoutineViewModel
import kotlinx.coroutines.launch


@Composable
fun ExercisesToAddRoutine(
    nav: NavHostController,
    muscle_id: String,
    createRoutineViewModel: CreateRoutineViewModel
) {
    val pagerState = rememberPagerState(initialPage = 0, pageCount = {2})
    val coroutineScope = rememberCoroutineScope()
    val exercises by createRoutineViewModel.exercisesLive.observeAsState(initial = mutableListOf())

    LaunchedEffect(Unit) {
        if (muscle_id.isNotEmpty()){
            createRoutineViewModel.getAllExercises(muscle_id)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            FilterChip(
                selected = pagerState.currentPage == 0,
                onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(0)
                    }
                },
                label = { Text(text = "Ejercicios", textAlign = TextAlign.Center,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .fillMaxWidth()) },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 4.dp),
                colors = getChipColors(isSelected = pagerState.currentPage == 0)
            )

            FilterChip(
                selected = pagerState.currentPage == 1,
                onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(1)
                    }
                },
                label = { Text(text = "Mi rutina",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .fillMaxWidth()
                    )},
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 4.dp),
                colors = getChipColors(isSelected = pagerState.currentPage == 1)
            )
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.padding(8.dp)
        ) { position ->
            when (position) {
                0 -> {
                    LazyColumn() {
                        items(exercises) {exercise->
                            ExerciseItemList(exercise)
                        }
                    }
                }
                1 -> {

                }
            }
        }
    }
}

// Función auxiliar para definir los colores de los chips
@Composable
fun getChipColors(isSelected: Boolean) = SelectableChipColors(
    containerColor = if (isSelected) Color(0xFFCA5300) else Color(0xFFFCE5D8),
    labelColor = if (isSelected) Color.White else Color.Black,
    leadingIconColor = Color.White,
    trailingIconColor = Color.White,
    disabledContainerColor = Color.Gray.copy(alpha = 0.5f),
    disabledLabelColor = Color.White.copy(alpha = 0.5f),
    disabledLeadingIconColor = Color.Gray.copy(alpha = 0.5f),
    disabledTrailingIconColor = Color.Gray.copy(alpha = 0.5f),
    selectedContainerColor = Color(0xFFCA5300),
    disabledSelectedContainerColor = Color.Gray.copy(alpha = 0.8f),
    selectedLabelColor = Color.White,
    selectedLeadingIconColor = Color.White,
    selectedTrailingIconColor = Color.White
)



@Composable
fun ExerciseItemList(exercise: StrengthExerciseModel) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)) {

        Row(verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = exercise.image_url,
                modifier = Modifier.size(100.dp),
                contentScale = ContentScale.Crop,
                contentDescription = "imageExercise")

            Column(modifier = Modifier.padding(8.dp)) {
                Text(text = exercise.name?:"", modifier = Modifier.fillMaxWidth())
                Text(text = exercise.primary_muscle?:"", modifier = Modifier
                    .background(colorResource(id = R.color.orange_low))
                    .fillMaxWidth())
            }
        }
    }

}