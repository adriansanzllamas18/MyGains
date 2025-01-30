package com.example.mygains.routinedetail.ui

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.mygains.R

@Preview(showBackground = true)
@Composable
fun RoutineDetailCompossable() {
    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        item {
            RoutineHeader()
        }

        item {
            RoutineSlider()
        }

        item {
            RoutineGlobalInfo()
        }

        item {
            RoutineExercises()
        }
    }
}

@Composable
fun RoutineExercises() {
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

        RoutineInfoExcercises()
    }
}

@Composable
fun RoutineInfoExcercises() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = "Nombre del ejercicio")
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(text = "serie 1º")
            Text(text = "Numero de repeticiones")
            Text(text = "peso por serie")
        }
    }
}


@Composable
fun RoutineGlobalInfo() {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)) {
        Row(Modifier.fillMaxWidth()) {
            Text(text = "Rutina de Pecho", modifier = Modifier.weight(1f))
            Text(text = "Lunes 17, 18:00 pm")
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
fun RoutineSlider() {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 16.dp)) {
        HorizontalPager(state = rememberPagerState( pageCount = {2}), modifier = Modifier.height(200.dp)) {

            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(end = 16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFCE5D8))
            ) {

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
