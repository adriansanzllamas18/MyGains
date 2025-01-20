package com.example.mygains.plan.ui


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text


import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.mygains.R
import com.example.mygains.exercisesplan.data.models.RoutineDayData


@Composable
fun ExercisesOfDayListComposable (modifier: androidx.compose.ui.Modifier, listExercises:MutableList<RoutineDayData>){

    Column(
     modifier = modifier) {
        listExercises.forEachIndexed { index, routineDayData ->
            Column() {
                Row(androidx.compose.ui.Modifier.padding(start = 8.dp)) {
                    ConstraintLayout(modifier = androidx.compose.ui.Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 8.dp)) {
                        val (pipe,exerciseType,card,muscleName, timeExcercise, alert,hour) = createRefs()

                        Text(text = routineDayData.timeOfDay,
                            androidx.compose.ui.Modifier.constrainAs(hour) {
                                start.linkTo(parent.start)
                                bottom.linkTo(parent.bottom)
                            },
                            fontSize = 12.sp, color = Color.Gray)
                        
                        Card(modifier = androidx.compose.ui.Modifier
                            .size(40.dp)
                            .constrainAs(card) {
                                start.linkTo(hour.end)
                                top.linkTo(parent.top)
                            }, colors = CardColors(containerColor =  Color(0xFFFCE5D8), contentColor =Color.White,Color.White,Color.White )) {
                            Image(painter = painterResource(id = R.drawable.fuerza) , contentDescription ="icon" )
                        }

                        Text(text = "15 mins",
                            androidx.compose.ui.Modifier
                                .constrainAs(timeExcercise)
                                {
                                    top.linkTo(card.bottom)
                                    start.linkTo(card.end)
                                    bottom.linkTo(hour.bottom)
                                }
                                .padding(top = 13.dp),
                            color = Color.Gray,
                            fontSize = 12.sp
                        )
                        Text(text = "Entreno de ${routineDayData.exerciseType}",
                            androidx.compose.ui.Modifier
                                .constrainAs(exerciseType)
                                {
                                    top.linkTo(parent.top)
                                    start.linkTo(parent.start)
                                    end.linkTo(parent.end)
                                }
                                .padding(8.dp)
                        )
                        Text(text = routineDayData.exercises.muscle,
                            androidx.compose.ui.Modifier.constrainAs(muscleName)
                            {
                                top.linkTo(exerciseType.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            },
                            color = Color.Gray
                        )


                        if (index < listExercises.size-1){
                            if (routineDayData.timeOfDay==listExercises[index+1].timeOfDay){
                                Card(modifier = androidx.compose.ui.Modifier
                                    .width(8.dp)
                                    .height(56.dp)
                                    .constrainAs(pipe) {
                                        top.linkTo(card.bottom)
                                        start.linkTo(card.start)
                                        end.linkTo(card.end)
                                    }
                                    , colors = CardColors(containerColor =  colorResource(id = R.color.orange), contentColor =Color.White,Color.White,Color.White )) {
                                }
                            }else{
                                Card(modifier = androidx.compose.ui.Modifier
                                    .width(8.dp)
                                    .height(56.dp)
                                    .constrainAs(pipe) {
                                        top.linkTo(card.bottom)
                                        start.linkTo(card.start)
                                        end.linkTo(card.end)
                                    }
                                    , colors = CardColors(containerColor =  colorResource(id = R.color.orange_low), contentColor =Color.White,Color.White,Color.White )) {
                                }
                            }
                           
                        }


                        Icon(imageVector = Icons.Default.Notifications, contentDescription = "notification",
                            modifier = androidx.compose.ui.Modifier.constrainAs(alert) {
                                end.linkTo(parent.end)
                                top.linkTo(parent.top)
                                bottom.linkTo(card.bottom)
                            },
                            tint = colorResource(id = R.color.orange_low)
                        )
                    }

                }
            }
        }
        if (listExercises.isEmpty()){
            MyPlanForDay(modifier = Modifier.align(Alignment.CenterHorizontally))
        }
    }
}

@Composable
fun MyPlanForDay(modifier: Modifier) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.nodata))
    var isPlaying by remember {
        mutableStateOf(true)
    }
    // Animación con progreso controlado
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever,
        isPlaying = isPlaying // Añadido el control de reproducción
    )

    Column(modifier) {

        Card(colors = CardDefaults.cardColors(containerColor = Color.White)) {
            LottieAnimation(
                composition = composition,
                progress = progress,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(8.dp)
                    .size(200.dp)
            )

            Text(text = "No hay entreno registrado para este dia , puedes añadir tus ejercicios en el icono de la esquina superior derecha.",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp),
                textAlign = TextAlign.Center,
                color = Color.Gray)

        }
    }

}

