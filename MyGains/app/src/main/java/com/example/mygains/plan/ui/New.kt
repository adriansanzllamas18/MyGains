package com.example.mygains.plan.ui


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Text


import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.mygains.R
import com.example.mygains.exercisesplan.data.RoutineDayData

import java.lang.reflect.Modifier


@Composable
fun New (modifier: androidx.compose.ui.Modifier, listExercises:MutableList<RoutineDayData>){

    Column(
     modifier = modifier) {
        listExercises.forEach {
            Column() {
                Row(androidx.compose.ui.Modifier.padding(start = 8.dp)) {
                    Text(text = "9:50",
                        androidx.compose.ui.Modifier.align(Alignment.CenterVertically),
                        fontSize = 12.sp, color = Color.Gray)
                    ConstraintLayout(modifier = androidx.compose.ui.Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 8.dp)) {
                        val (pipe,exerciseType,card,muscleName, timeExcercise, alert) = createRefs()
                        Card(modifier = androidx.compose.ui.Modifier
                            .size(40.dp)
                            .constrainAs(card) {
                                start.linkTo(parent.start)
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
                                    bottom.linkTo(card.bottom)
                                }
                                .padding(top = 13.dp),
                            color = Color.Gray,
                            fontSize = 12.sp
                        )
                        Text(text = "Entreno de ${it.exerciseType}",
                            androidx.compose.ui.Modifier
                                .constrainAs(exerciseType)
                                {
                                    top.linkTo(parent.top)
                                    start.linkTo(parent.start)
                                    end.linkTo(parent.end)
                                }
                                .padding(8.dp)
                        )
                        Text(text = it.exercises.muscle, androidx.compose.ui.Modifier.constrainAs(muscleName)
                        {
                            top.linkTo(exerciseType.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        },
                            color = Color.Gray
                        )

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
    }
}