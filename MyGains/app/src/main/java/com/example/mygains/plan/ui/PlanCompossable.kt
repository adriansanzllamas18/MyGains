package com.example.mygains.plan.ui

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
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


@Composable
fun  PlanCompossable(nav: NavHostController) {

    Column(
        Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.systemBars)) {
        MyHeader(modifier = Modifier
            .padding(16.dp),nav)

        Box(Modifier.verticalScroll(rememberScrollState())){
            ConstraintLayout(
                Modifier
                    .fillMaxSize()
                    .padding(8.dp)) {
                val (myplan, calendar) = createRefs()
                MyWeekCalendar(modifier = Modifier
                    .constrainAs(calendar) {
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                        start.linkTo(parent.start)
                    }
                    .padding(8.dp))
                
                MyPlanForDay(modifier = Modifier
                    .constrainAs(myplan) {
                        top.linkTo(calendar.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .padding(8.dp),nav)
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
                Modifier.size(120.dp).align(Alignment.CenterHorizontally).padding(8.dp))
            Text(text = "No hay entreno registrado para este dia...", Modifier.padding(8.dp))
            Button(onClick = {
                nav.navigate(Routes.ExcercisesPlan.routes) {
                    // Asegúrate de que no se pueda volver a esta pantalla
                    popUpTo(Routes.Plan.routes) { inclusive = true }
                }

            }, Modifier.align(Alignment.CenterHorizontally)) {
                Text(text = "Añadir")
            }
        }
    }

}

@Composable
fun MyWeekCalendar(modifier: Modifier) {
    val currentDate = remember { LocalDate.now() }
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
                    dayContent = { Day(it) }, // Mostrar el contenido de cada día
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
fun Day(day: CalendarDay, modifier: Modifier = Modifier) {
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
                val (close,title) = createRefs()

                Icon(imageVector =  Icons.Filled.Close, contentDescription ="atras",
                    Modifier
                        .constrainAs(close) {
                            start.linkTo(parent.start)
                            bottom.linkTo(parent.bottom)
                            top.linkTo(parent.top)
                        }
                        .clickable { nav.popBackStack()}
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

