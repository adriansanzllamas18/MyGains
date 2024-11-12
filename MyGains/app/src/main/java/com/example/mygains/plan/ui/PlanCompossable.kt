package com.example.mygains.plan.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.currentCompositionLocalContext
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.mygains.login.ui.Loader
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import kotlinx.coroutines.currentCoroutineContext
import java.time.LocalDate
import java.time.YearMonth


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlanCompossable(nav: NavHostController) {

    var planViewModel:PlanViewModel = hiltViewModel()


    var selectedDay:String by remember { mutableStateOf(LocalDate.now().toString()) }


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
    }
    PullToRefreshBox(modifier = Modifier.fillMaxSize()
        , isRefreshing = isLoading,
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
                    modifier = Modifier.padding(16.dp), nav
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
                            , selectedDay
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
fun MyPlanForDay(modifier: Modifier,nav: NavHostController) {
    Column(modifier) {
        Text(
            text = "Entreno",
            fontSize = 24.sp,
            modifier = Modifier.padding(8.dp),
            fontStyle = FontStyle(R.font.poppins)
        )


        Card(colors = CardDefaults.cardColors(containerColor = Color.White)) {
            Image(painter = painterResource(id = R.drawable.capacitacion), contentDescription ="image",
                Modifier
                    .size(120.dp)
                    .align(Alignment.CenterHorizontally)
                    .padding(8.dp))
            Text(text = "No hay entreno registrado para este dia...", Modifier.padding(8.dp))
            Button(onClick = {
                nav.navigate(Routes.ExcercisesPlan.routes)
            }, Modifier.align(Alignment.CenterHorizontally)) {
                Text(text = "Añadir")
            }
        }
    }

}

@Composable
fun MyWeekCalendar(modifier: Modifier, selectedDay: String) {

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
                    dayContent = { Day(it,selectedDay= selectedDay) }, // Mostrar el contenido de cada día
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
fun Day(day: CalendarDay, modifier: Modifier = Modifier,selectedDay: String) {
    val isToday = day.date == LocalDate.now()
    val backgroundColor = if (isToday) colorResource(id = R.color.orange_low) else Color.Transparent
    val textColor = if (isToday) colorResource(id = R.color.orange) else if(day.position != DayPosition.MonthDate) Color.Gray else Color.Black
    val outDay = if (day.position == DayPosition.MonthDate) Color.White else Color.Gray



    Card(
        modifier = modifier
            .height(70.dp)
            .width(70.dp)
            .padding(4.dp)
            .clickable {
                // Acción al hacer clic en un día
                println("Fecha seleccionada: ${day.date}")
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


// Extensiones para obtener YearMonth desde LocalDate
fun LocalDate.yearMonth(): YearMonth {
    return YearMonth.of(this.year, this.monthValue)
}



@Composable
fun MyHeader(modifier: Modifier,nav: NavHostController) {

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
                        .clickable { nav.navigate(Routes.ExcercisesPlan.routes) }
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
