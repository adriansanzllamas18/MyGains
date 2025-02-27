package com.example.mygains.createroutineprocess.ui.screens


import android.content.ClipData.Item
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.EaseInOutCubic
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SelectableChipColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathSegment
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.mygains.R
import com.example.mygains.createroutineprocess.data.models.StrengthExerciseModel
import com.example.mygains.createroutineprocess.ui.CreateRoutineViewModel
import ir.ehsannarmani.compose_charts.ColumnChart
import ir.ehsannarmani.compose_charts.LineChart
import ir.ehsannarmani.compose_charts.RowChart
import ir.ehsannarmani.compose_charts.models.AnimationMode
import ir.ehsannarmani.compose_charts.models.BarProperties
import ir.ehsannarmani.compose_charts.models.Bars
import ir.ehsannarmani.compose_charts.models.DividerProperties
import ir.ehsannarmani.compose_charts.models.DotProperties
import ir.ehsannarmani.compose_charts.models.DrawStyle
import ir.ehsannarmani.compose_charts.models.GridProperties
import ir.ehsannarmani.compose_charts.models.Line
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
    val selectedExercise by createRoutineViewModel.exercisesSelectedlLive.observeAsState(initial = null)

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
                            ExerciseItemList(exercise, viewModel = createRoutineViewModel)
                        }
                    }
                }
                1 -> {

                }
            }
        }
    }

    selectedExercise?.let { ExerciseDetailAndConfigRoutineDialog(viewModel = createRoutineViewModel, strengthExerciseModel = it) }

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
fun ExerciseItemList(exercise: StrengthExerciseModel, viewModel: CreateRoutineViewModel) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
        .clickable {
            viewModel.setExercisesVisibility(true)
            viewModel.setSelectedExercise(exercise)
        }
    )
    {

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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseDetailAndConfigRoutineDialog(viewModel: CreateRoutineViewModel,strengthExerciseModel:StrengthExerciseModel){

    val showDetail by  viewModel.showDetailLive.observeAsState(initial = false)
    if (showDetail){
        ModalBottomSheet(
            modifier = Modifier.fillMaxWidth(),
            onDismissRequest = { viewModel.setExercisesVisibility(false) }
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
            {

                item {
                    AsyncImage(
                        modifier = Modifier
                            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp, bottomStart = 0.dp, bottomEnd = 0.dp)),
                        contentScale = ContentScale.Crop,
                        model = strengthExerciseModel.image_url,
                        contentDescription = "exercise image"
                    )
                }

                item {
                    Spacer(modifier = Modifier.padding(8.dp))
                }

                item {
                    Text(
                        text = strengthExerciseModel.name ?: "",
                        fontSize = 28.sp
                    )
                }


                item {

                    Text(
                        text = strengthExerciseModel.description ?: "",
                        modifier = Modifier.fillMaxWidth(),
                        fontSize = 16.sp
                    )
                }



                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center) {
                        Card(
                            modifier = Modifier.padding(vertical = 8.dp),
                            colors = CardColors(
                                containerColor = colorResource(id = R.color.orange_low),
                                contentColor = Color.Black,
                                disabledContentColor = Color.Black,
                                disabledContainerColor = Color.Gray)
                        )
                        {
                            Text(
                                modifier = Modifier.padding(8.dp),
                                text = ( strengthExerciseModel.difficulty) ?: ""
                            )
                        }
                        Card(
                            modifier = Modifier.padding(vertical = 8.dp),
                            colors = CardColors(
                                containerColor = colorResource(id = R.color.gray_claro),
                                contentColor = Color.Black,
                                disabledContentColor = Color.Black,
                                disabledContainerColor = Color.Gray)
                        )
                        {
                            Text(
                                modifier = Modifier.padding(8.dp),
                                text = ( strengthExerciseModel.mechanic) ?: ""
                            )
                        }
                    }
                }

                item{
                    SetSeriesRepsWeights()
                }

                item {
                    Text(text = "Musculo principal-> ${strengthExerciseModel.primary_muscle}.")
                    Spacer(modifier = Modifier.padding(8.dp))
                    ListDeployed(title = "Músculos secundarios", list = strengthExerciseModel.secondary_muscles?: mutableListOf())
                    ListDeployed(title = "Equipamiento", list = strengthExerciseModel.equipment?: mutableListOf())
                    ListDeployed(title = "Tips", list = strengthExerciseModel.tips?: mutableListOf())
                    ListDeployed(title = "Variaciones de Ejercicios", list = strengthExerciseModel.variations?: mutableListOf())

                }


                item {
                        ColumnChart(
                            modifier = Modifier
                                .height(180.dp)
                                .width(200.dp)
                                .padding(horizontal = 8.dp),
                            data = remember {
                                listOf(
                                    Bars(
                                        label = "14 feb",
                                        values = listOf(
                                            Bars.Data(label = "kg máx", value = 50.0, color = Brush.verticalGradient(colors = listOf(Color(0xFF6C0BA9), Color(0xFF9A05EB)))),
                                            Bars.Data(label = "Reps", value = 70.0, color = SolidColor(Color.Red)),
                                        ),
                                    ),
                                    Bars(
                                        label = " 15Feb",
                                        values = listOf(
                                            Bars.Data(label = "kg máx", value = 80.0, color = Brush.verticalGradient(colors = listOf(Color(0xFF6C0BA9), Color(0xFF9A05EB)))),
                                            Bars.Data(label = "Reps", value = 60.0, color = SolidColor(Color.Red)),
                                        ),
                                    )
                                )
                            },
                            barProperties = BarProperties(
                                cornerRadius = Bars.Data.Radius.Circular(6.dp),
                                spacing = 2.dp,
                                thickness = 25.dp
                            ),
                            gridProperties = GridProperties(
                                enabled = false
                            ),
                            animationSpec = spring(
                                dampingRatio = Spring.DampingRatioMediumBouncy,
                                stiffness = Spring.StiffnessLow
                            ),
                        )
                }


            }
        }
    }
}



@Composable
fun SetSeriesRepsWeights(){

    var numSeries by remember {
        mutableIntStateOf(0)
    }
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 24.dp)) {
        Row( horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Series", modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp), textAlign = TextAlign.Start)
            Stepper(value = numSeries, onValueChange = {numSeries = it})
        }

        (1..numSeries).forEach {
            Stepper(value = numSeries, onValueChange = {numSeries = it})
        }
    }
}

@Composable
fun Stepper(
    value: Int,
    onValueChange: (Int) -> Unit,
    minValue: Int = 0,
    maxValue: Int = 800
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Button(
            onClick = { if (value > minValue) onValueChange(value - 1) },
            enabled = value > minValue
        ) {
            Text("-")
        }

        Text(text = value.toString(), fontSize = 20.sp)

        Button(
            onClick = { if (value < maxValue) onValueChange(value + 1) },
            enabled = value < maxValue
        ) {
            Text("+")
        }
    }
}

@Composable
fun ListDeployed( title:String, list:MutableList<String>, initial:Boolean=false){
    var show by remember {
        mutableStateOf(initial)
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.CenterVertically) {
            Text(text = title, modifier = Modifier.weight(1f))
            IconButton(
                modifier = Modifier.size(16.dp),
                onClick = {
                    show= !show
                }
            ){
                Icon( painter = if (show) painterResource(id = R.drawable.listup) else painterResource(id = R.drawable.listdowns),
                    contentDescription = "desplegar",)
            }
        }

        AnimatedVisibility(visible = show) {
            Card(modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
                colors = CardColors(
                    containerColor = colorResource(id = R.color.orange_low),
                    contentColor = Color.Black,
                    disabledContentColor = Color.Black,
                    disabledContainerColor = Color.Gray)
            ) {
        Column(modifier = Modifier.padding(start = 16.dp)) {
                    list.forEach {
                        Text(text = "${it}.", fontSize = 14.sp)
                    }
                }
            }

        }
    }
}

