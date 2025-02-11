package com.example.mygains.routinedetail.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mygains.R
import com.example.mygains.extras.utils.FormatterUtils
import com.example.mygains.routinedetail.data.RoutineDetailModel

@Preview(showBackground = true)
@Composable
fun RoutineDetailCompossable() {


    val routineDetailViewModel: RoutineDetailViewModel = hiltViewModel()

    val routineDetailModel by  routineDetailViewModel.routineDetailModelLife.observeAsState(
        mutableListOf<RoutineDetailModel>()
    )

    val sliderPosition by  routineDetailViewModel.currentPageLife.observeAsState(
      0
    )


    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
        .windowInsetsPadding(WindowInsets.systemBars),
        state = rememberLazyListState()
        ) {
        item {
          RoutineHeader()
        }

        item {
           RoutineSlider(routineDetailModel,routineDetailViewModel)
        }

        item {
                AnimatedVisibility(visible = routineDetailModel.isNotEmpty() ) {
                    RoutineGlobalInfo(routineDetailModel,sliderPosition)
            }

        }
        item {
            AnimatedVisibility(visible = routineDetailModel.isNotEmpty() ) {
                RoutineExercises(routineDetailModel,sliderPosition)
            }
        }
    }
}

@Composable
fun RoutineExercises(routineDetailModel: MutableList<RoutineDetailModel>,sliderPosition:Int) {

    Column(Modifier.fillMaxWidth(), verticalArrangement = Arrangement.Center) {
        Text(
            text = "Ejercicios",
            fontSize =  28.sp
        )
        Row(modifier = Modifier
            .padding(vertical = 16.dp)
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())) {

         (1..6).forEach{

                Card(
                    modifier = Modifier
                        .size(150.dp)
                        .padding(end = 16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFCE5D8))
                ) {

                }
            }
        }

        RoutineInfoExcercises(routineDetailModel,sliderPosition)
    }
}

@Composable
fun RoutineInfoExcercises(routineDetailModel: MutableList<RoutineDetailModel>,sliderPosition: Int) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = routineDetailModel[sliderPosition].routineDayModel?.exercises?.excerciseName?:"-----")
        routineDetailModel[sliderPosition].routineDayModel?.exercises?.seriesAndReps?.let {seriesreps->
                seriesreps.forEach {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text(text = "Serie ${it.serie}º")
                        Text(text = " Reps:${it.reps}")
                        Text(text = "kg:${it.weght}")
                    }
                }
            }
    }
}


@Composable
fun RoutineGlobalInfo(currentPage: MutableList<RoutineDetailModel>, sliderPosition: Int) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)) {
        Row(Modifier.fillMaxWidth()) {
            Text(text = "Rutina de ${currentPage[sliderPosition].routineDayModel?.exercises?.muscle?:""}", modifier = Modifier.weight(1f))
            Text(text = currentPage[sliderPosition].date?:"") // Lunes 17, 18:00 pm todo cambiar formato
        }
        Row(Modifier.fillMaxWidth()) {
            Text(text = "PlayList", modifier = Modifier.weight(1f))
            Text(text = "BeastMode")
        }

        Row(
            Modifier
                .fillMaxWidth()
                .padding(16.dp)) {
            Text(text = "300kcl aprox.", modifier = Modifier.weight(1f))
            Text(text = "1h 30 min")
        }

        Text(text = "La ultima vez que entrenastes pecho levantastes un total de 100kg.", textAlign = TextAlign.Center)
    }
}

@Composable
fun RoutineSlider(routineDetailModel: MutableList<RoutineDetailModel>,viewModel: RoutineDetailViewModel) {
    val steSlider = rememberPagerState( pageCount = {routineDetailModel.size})
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 16.dp)) {

        HorizontalPager(
            state = steSlider,
            modifier = Modifier.height(200.dp),
            contentPadding = PaddingValues(8.dp)

        ) {

            viewModel.setSliderPosition(steSlider.currentPage)
            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(end = 16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFCE5D8)),
                shape = RoundedCornerShape(16.dp), // Borde redondeado
            ) {
                Image(painter = painterResource(
                    id = FormatterUtils().getSliderImageByType(routineDetailModel[steSlider.currentPage].routineDayModel?.exerciseType?:"")),
                    contentDescription = "imageSlider",
                    contentScale = ContentScale.Crop)
            }

        }
    }
}

@Composable
fun RoutineHeader() {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
    ) {
        ConstraintLayout(Modifier.fillMaxWidth()) {

            val (back,edit)= createRefs()

            Icon(painter = painterResource(id = R.drawable.angulo_izquierdo),
                contentDescription ="back",
                modifier = Modifier
                    .size(24.dp)
                    .constrainAs(back) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    }
            )

            Icon(painter = painterResource(id = R.drawable.boligrafo_cuadrado),
                contentDescription ="edit" ,
                modifier = Modifier
                    .size(24.dp)
                    .constrainAs(edit) {
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                    }
            )
        }
    }
}
