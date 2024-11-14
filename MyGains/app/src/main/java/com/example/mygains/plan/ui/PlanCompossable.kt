package com.example.mygains.plan.ui

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.mygains.R
import com.example.mygains.extras.navigationroutes.Routes
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import java.time.LocalDate
import java.time.YearMonth


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlanCompossable(nav: NavHostController) {

    val planViewModel:PlanViewModel = hiltViewModel()

    val selectedDay:String by planViewModel._selectedDateLife.observeAsState(initial = LocalDate.now().toString())


    val isLoading:Boolean by planViewModel._isLoadingLife.observeAsState(initial = false)


    val routineList by planViewModel._routineDayListLife.observeAsState(initial = mutableListOf())

    // Crear un estado para el desplazamiento de la lista
    val lazyListState = rememberLazyListState()

    // Usar un valor que cambie de acuerdo con el desplazamiento
    val initialHeight = 500.dp // Altura inicial del calendario
    val minHeight = 100.dp // Altura mínima del calendario

    // Obtener la densidad de la pantalla para convertir píxeles a dp
    val density = LocalDensity.current.density

    // Mantener el valor de la altura con un remember para evitar recomposiciones innecesarias
    val scrollOffset = remember { derivedStateOf { lazyListState.firstVisibleItemScrollOffset } }

    // Ajustar la altura del calendario dependiendo del desplazamiento
    val heightInPx = (initialHeight - (scrollOffset.value.dp / density) / 4).coerceAtLeast(minHeight)

    // Convertir la altura de píxeles a dp
    val newHeight = with(LocalDensity.current) { heightInPx }

    LaunchedEffect(Unit) {
        planViewModel.getAllExcercisesForDay(selectedDay)
        //esto es si quiero que cuando salga de la pantalla de ejercicios siga apareciendo la fecha actual
        //planViewModel.setSelectedDate(LocalDate.now().toString())
    }


    PullToRefreshBox(modifier = Modifier.fillMaxSize(),
        isRefreshing = isLoading,
        onRefresh = {
            planViewModel.getAllExcercisesForDay(selectedDay)
        }
    ) {
        LazyColumn(
            state = lazyListState, // Pasar el estado de LazyList
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.systemBars)
        ) {

            item {
                MyHeader(
                    modifier = Modifier.padding(16.dp), nav,planViewModel,selectedDay
                )
            }

            item {
                Box {
                    ConstraintLayout(
                        Modifier
                            .fillMaxSize()
                            .padding(8.dp)
                    ) {
                        val (myplan, calendar) = createRefs()
                        MyWeekCalendar(
                            modifier = Modifier
                                .constrainAs(calendar) {
                                    top.linkTo(parent.top)
                                    end.linkTo(parent.end)
                                    start.linkTo(parent.start)
                                }
                                .height(newHeight) // Establecer la altura dinámica del calendario
                                .padding(8.dp)
                            , selectedDay,planViewModel
                        )
                    }
                }
            }



            item {
                TitleDateForList("",
                    Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp))
                HorizontalDivider(modifier = Modifier
                    .height(8.dp)
                    .padding(start = 16.dp, end = 16.dp),
                    color =Color(0xFFFCE5D8))
                ExercisesOfDayListComposable(
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp), routineList) // Lista de nuevos elementos
            }
        }
    }
}


@Composable
fun MyWeekCalendar(modifier: Modifier, selectedDay: String,planViewModel: PlanViewModel) {

    val firstDayOfWeek = remember { firstDayOfWeekFromLocale() }
    val currentMonth = remember { YearMonth.now() }
    val startMonth = remember { currentMonth.minusMonths(100) }
    val endMonth = remember { currentMonth.plusMonths(100) }

    val state = rememberCalendarState(
        startMonth = startMonth,
        endMonth = endMonth,
        firstVisibleMonth = currentMonth,
        firstDayOfWeek = firstDayOfWeek
    )

    Column(modifier = modifier) {
        Text(
            text = "Calendario",
            fontSize = 24.sp,
            modifier = Modifier.padding(8.dp),
            fontStyle = FontStyle(R.font.poppins)
        )
        Card(colors = CardDefaults.cardColors(containerColor = Color.White)) {
            ConstraintLayout(Modifier.padding(16.dp)) {
                val (month, calendar, divider) = createRefs()

                HorizontalCalendar(
                    Modifier
                        .constrainAs(calendar) {
                            top.linkTo(parent.top)
                        }
                        .padding(8.dp),
                    state = state,
                    monthHeader ={ MonthHeader(currentMonth =state.firstVisibleMonth.yearMonth ) },
                    dayContent = { Day(it, selectedDate = selectedDay, planViewModel =planViewModel ) }, // Mostrar el contenido de cada día
                )
            }
        }
    }
}
@Composable
fun MonthHeader(currentMonth: YearMonth) {

    Column {
        Text(
            text = "${currentMonth.month.name.replaceFirstChar { it.uppercase() }} ${currentMonth.year}",
            fontSize = 20.sp,
            modifier = Modifier.padding(8.dp),
            fontWeight = FontWeight.Bold
        )

        HorizontalDivider(modifier = Modifier
            .height(8.dp),
            color =Color(0xFFFCE5D8))
    }

}

@Composable
fun Day(day: CalendarDay, modifier: Modifier = Modifier,planViewModel: PlanViewModel,selectedDate:String) {
    val isToday = selectedDate == day.date.toString()
    val backgroundColor = if (isToday) colorResource(id = R.color.orange_low) else Color.Transparent
    val textColor = if (isToday) colorResource(id = R.color.orange) else if(day.position != DayPosition.MonthDate) Color.Gray else Color.Black
    val outDay = if (day.position == DayPosition.MonthDate) Color.White else Color.Gray



    Card(
        modifier = modifier
            .height(70.dp)
            .width(70.dp)
            .padding(4.dp)
            .clickable {
                planViewModel.setSelectedDate(day.date.toString())
                println("Fecha seleccionada: $selectedDate")
            },
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(modifier = Modifier.padding(4.dp),
                text = day.date.dayOfWeek.toString().take(3),
                color = textColor,
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 10.sp
            )

            Text(
                text = day.date.dayOfMonth.toString(),
                color = textColor,
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 18.sp
            )

            if (isToday) {
                Icon(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    painter = painterResource(id = R.drawable.point_filled),
                    contentDescription = "point",
                    tint = colorResource(id = R.color.orange)
                )
            }
        }
    }
}




@Composable
fun MyHeader(
    modifier: Modifier,
    nav: NavHostController,
    planViewModel: PlanViewModel,
    selectedDay: String
) {

        Row(modifier= modifier.fillMaxWidth()) {
            ConstraintLayout(Modifier.fillMaxWidth()) {
                val (close,title,add) = createRefs()

                Icon(imageVector =  Icons.Filled.Close, contentDescription ="atras",
                    Modifier
                        .constrainAs(close) {
                            start.linkTo(parent.start)
                            bottom.linkTo(parent.bottom)
                            top.linkTo(parent.top)
                        }
                        .clickable { nav.popBackStack() }
                )
                Icon(painter = painterResource(id = R.drawable.add_exercise), contentDescription = "save",
                    Modifier
                        .constrainAs(add) {
                            end.linkTo(parent.end)
                            top.linkTo(parent.top)
                        }
                        .clickable { nav.navigate(Routes.ExcercisesPlan.createRout(selectedDay)) }
                        .size(24.dp)
                )
                Text(text = "Mi plan", modifier = Modifier.constrainAs(title){
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                    top.linkTo(parent.top)
                },
                    fontSize = 18.sp, fontWeight = FontWeight.Bold
                )

            }

        }

}

@Composable
fun TitleDateForList(day:String,modifier: Modifier){

    Row(modifier) {
        Text(
            text = "Tu entreno de hoy",
            fontSize = 24.sp,
            modifier = Modifier.padding(8.dp),
            fontStyle = FontStyle(R.font.poppins)
        )
    }
}
