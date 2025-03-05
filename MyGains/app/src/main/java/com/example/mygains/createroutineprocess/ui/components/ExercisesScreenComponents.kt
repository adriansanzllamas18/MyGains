package com.example.mygains.createroutineprocess.ui.components

import android.icu.text.CaseMap.Title
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.mygains.R
import com.example.mygains.createroutineprocess.data.models.ExerciseSet
import com.example.mygains.createroutineprocess.data.models.StrengthExerciseModel
import com.example.mygains.createroutineprocess.ui.CreateRoutineViewModel
import com.example.mygains.createroutineprocess.ui.screens.Stepper
import com.example.mygains.exercisesplan.data.models.SeriesAndReps

/*Componentes pantalla detalle de ejercicio*/

@Composable
fun TitleAndImageIconComponent(title:String, icon:Int){
    Row(horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.CenterVertically) {
        Image(
            modifier = Modifier.size(24.dp),
            painter = painterResource(id = icon),
            contentDescription = "icon image")
        Text(
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(8.dp),
            text = title
        )
    }
}


@Composable
fun ListInfoComponents(list:List<String>){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardColors(
            containerColor = colorResource(id = R.color.orange_low),
            contentColor = Color.Black,
            disabledContainerColor = Color.Black,
            disabledContentColor = Color.LightGray
        )
    ) {
        Column(modifier = Modifier.padding(start = 16.dp)) {
           list.forEach {
                Text(text = "${it}.", fontSize = 14.sp)
            }
        }
    }
}


@Composable
fun ExerciseItemList(
    exercise: StrengthExerciseModel,
    viewModel: CreateRoutineViewModel
) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
        .border(
            2.dp,
            color = colorResource(id = R.color.orange_low),
            shape = RoundedCornerShape(8.dp)
        )
        .clickable {
            viewModel.setExercisesVisibility(true)
            viewModel.setSelectedExercise(exercise)
        },
        colors = CardColors(contentColor = Color.Black, containerColor = Color.Transparent, disabledContentColor = Color.White, disabledContainerColor = Color.White)
    )
    {

        Row(verticalAlignment = Alignment.CenterVertically) {

                AsyncImage(
                    model = exercise.image_url,
                    modifier = Modifier
                        .size(100.dp)
                        .padding(8.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop,
                    contentDescription = "imageExercise")

                Column(modifier = Modifier.padding(8.dp)) {

                    Text(
                        text = exercise.name?:"",
                        modifier = Modifier.fillMaxWidth(),
                        fontSize = 18.sp
                    )

                    TitleAndImageIconComponent(exercise.primary_muscle?:"",R.drawable.musculo)
                }
            }
        }
}

@Composable
fun ExerciseItemToAddList(
    exercise: StrengthExerciseModel,
    viewModel: CreateRoutineViewModel
) {

    val setsList = remember { mutableStateListOf<ExerciseSet>() }

    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
        .border(
            2.dp,
            color = colorResource(id = R.color.orange_low),
            shape = RoundedCornerShape(8.dp)
        )
        .clickable {
            viewModel.setExercisesVisibility(true)
            viewModel.setSelectedExercise(exercise)
        },
        colors = CardColors(contentColor = Color.Black, containerColor = Color.Transparent, disabledContentColor = Color.White, disabledContainerColor = Color.White)
    )
    {
        Column(verticalArrangement = Arrangement.Center) {
            ExerciseItemList(exercise = exercise, viewModel = viewModel)
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {

                Text(
                    text = "Series",
                    modifier = Modifier.weight(1f)
                )
                Button(
                    onClick = {
                        setsList.add(ExerciseSet())
                    }
                ) {
                    Text(text = "+ Serie")
                }
            }

            Column(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
                setsList.forEachIndexed { position, exercise ->
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 8.dp, vertical = 16.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp) // Espaciado entre elementos
                    ) {
                        Stepper(
                            modifier = Modifier.weight(1f),
                            value = exercise.reps.toInt(),
                            onValueChange = {
                                // Crea una nueva lista con el elemento modificado
                                val updatedList = setsList.toMutableList().apply {
                                    this[position] = this[position].copy(reps = it.toString())
                                }
                                // Reemplaza toda la lista
                                setsList.clear()
                                setsList.addAll(updatedList)
                            }
                        )
                        TextField(
                            value = if (exercise.weight == 0f.toString()) "" else exercise.weight,
                            onValueChange = {
                                // Crea una nueva lista con el elemento modificado
                                val updatedList = setsList.toMutableList().apply {
                                    this[position] = this[position].copy(weight = it)
                                }
                                // Reemplaza toda la lista
                                setsList.clear()
                                setsList.addAll(updatedList)
                            },
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 8.dp)
                                .clip(RoundedCornerShape(38.dp)),
                            placeholder = { Text(text = "Kg", fontSize = 12.sp)},
                            maxLines = 1,
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color(0xFFFCE5D8),
                                unfocusedContainerColor = Color(0xFFFCE5D8),
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            ),
                            shape = RoundedCornerShape(38.dp)
                        )

                        IconButton(
                            modifier = Modifier.weight(0.5f).size(24.dp),
                            onClick = { setsList.remove(exercise)}
                        )
                        {
                            Image(painter = painterResource(id = R.drawable.eliminar), contentDescription ="eleminar" )
                        }
                    }
                }
            }
        }
    }
}
