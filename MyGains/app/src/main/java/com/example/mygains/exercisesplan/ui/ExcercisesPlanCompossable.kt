package com.example.mygains.exercisesplan.ui

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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.example.mygains.exercisesplan.data.MuscleGroupe
import com.example.mygains.R


@Composable
fun ExcercisesPlanCompossable(nav: NavHostController) {
    Column(
        Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.systemBars)) {
        MyHeader(modifier = Modifier
            .align(Alignment.CenterHorizontally)
            .padding(16.dp),nav)
       ConstraintLayout {
           val (body) = createRefs()
           MyBodyRoutineExercisesType(modifier= Modifier
               .constrainAs(body) {
                   top.linkTo(parent.top)
                   start.linkTo(parent.start)
               }
               .padding(16.dp))
       }

    }
}

@Composable
fun MyBodyRoutineExercisesType(modifier: Modifier) {

    var selectedText by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    val typeList= mutableListOf("Cardio","Pesas")

    Column(modifier) {
            Text(
                text = "Tipo de ejercicio",
                fontSize = 24.sp,
                modifier = Modifier.padding(8.dp),
                fontStyle = FontStyle(R.font.poppins)
            )

        Column {
            OutlinedTextField(value = selectedText, onValueChange = {selectedText = it},
                readOnly = true, enabled = false,
                modifier = Modifier
                    .clickable { expanded = true }
                    .fillMaxWidth(), placeholder = { Text(text = "ejercico..")})

            DropdownMenu(expanded = expanded, onDismissRequest = { expanded= false},
                Modifier
                    .background(colorResource(id = R.color.orange_low))) {
                typeList.forEach {
                    DropdownMenuItem(text = {
                        Column {
                            Row {
                                Icon(modifier = Modifier.size(24.dp), painter = painterResource(id = if (it == "Pesas")R.drawable.weights else R.drawable.run), contentDescription ="icon" )
                                Text(text = it)
                            }
                            HorizontalDivider(modifier = Modifier
                                .height(8.dp),
                                color = colorResource(id = R.color.orange)
                            )
                        } }, onClick = { expanded = false
                        selectedText= it })
                }
            }

        }
            if (selectedText == "Pesas" && !expanded ){
                MyBodyLiftExcercises(
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp))
            }
        }
}

@Composable
fun MyBodyLiftExcercises(modifier: Modifier) {

    // Estado para almacenar el grupo seleccionado
    var selectedMuscleGroup by remember { mutableStateOf<MuscleGroupe?>(null) }

    val muscleGroups = listOf<MuscleGroupe>(
        MuscleGroupe("Pectorales",R.drawable.pectorales, mutableListOf()),
        MuscleGroupe("Espalda",R.drawable.atras, mutableListOf("")),
        MuscleGroupe("Piernas",R.drawable.pierna__1_,mutableListOf("")) ,
        MuscleGroupe("Bíceps",R.drawable.biceps,mutableListOf("")) ,
        MuscleGroupe("Tríceps",R.drawable.triceps__1_,mutableListOf("")),
        MuscleGroupe("Hombros",R.drawable.deltoides__1_,mutableListOf("")),
        MuscleGroupe("Abdominales",R.drawable.abdominales__1_,mutableListOf(""))
    )
    Column(modifier) {
        Text(
            text = "Grupo Muscular",
            fontSize = 24.sp,
            modifier = Modifier.padding(8.dp),
            fontStyle = FontStyle(R.font.poppins)
        )
        LazyRow {
            items(muscleGroups.size){muscle->
                MuscleGroupCard(group = muscleGroups[muscle].name, muscleGroups[muscle].image,
                    onClick = {
                        muscleGroups[muscle]
                        selectedMuscleGroup = muscleGroups[muscle]
                              },
                    isSelected = selectedMuscleGroup == muscleGroups[muscle] )
            }
        }
        //al principio como no hay nada seleccionado la vista de repeticiones y series no se muestra
        selectedMuscleGroup?.let { MyExercisesSriesAndReps(modifier = Modifier.padding(top = 16.dp), muscleGroupe = it) }

    }
}

@Composable
fun MyExercisesSriesAndReps(modifier: Modifier,muscleGroupe: MuscleGroupe) {

    var excerciseName by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var numberSeries by remember { mutableStateOf(0) }


    Column(modifier){

        Row {
            Text(
                text = muscleGroupe.name,
                fontSize = 24.sp,
                modifier = Modifier.padding(8.dp),
                fontStyle = FontStyle(R.font.poppins)
            )

            IconButton(onClick = { if (numberSeries >0 )numberSeries--},Modifier.align(Alignment.CenterVertically)) {
                Icon(painter = painterResource(id =R.drawable.remove_circle_24 ) , contentDescription ="remove" )
            }

            Text(text = numberSeries.toString(), Modifier.align(Alignment.CenterVertically), fontSize = 18.sp)

            IconButton(onClick = {  numberSeries++},Modifier.align(Alignment.CenterVertically)) {
                Icon(imageVector = Icons.Filled.AddCircle, contentDescription ="add" )
            }

            Text(text = "Ser.", Modifier.align(Alignment.CenterVertically), fontSize = 18.sp)
        }

        LazyColumn(Modifier.padding(top = 16.dp, start = 24.dp)) {
            items(numberSeries){
                var kg by remember { mutableStateOf("") }
                var numberReps by remember { mutableStateOf(0) }

                OutlinedTextField(value = excerciseName, onValueChange = {excerciseName = it},
                    modifier = Modifier
                        .clickable { expanded = true }
                        .padding(top = 16.dp)
                        .width(200.dp), placeholder = { Text(text = "Nombre ejercicio..")})

                Row (Modifier.padding(top = 18.dp)){
                    Text( modifier = Modifier.align(Alignment.CenterVertically), text = "${it +1} º" + " Serie", fontSize = 18.sp)
                    IconButton(onClick = { if (numberReps >0 )numberReps--},Modifier.align(Alignment.CenterVertically)) {
                        Icon(painter = painterResource(id =R.drawable.remove_circle_24 ) , contentDescription ="remove" )
                    }

                    Text(text = numberReps.toString(), Modifier.align(Alignment.CenterVertically), fontSize = 18.sp)

                    IconButton(onClick = {  if (numberReps<9)numberReps++},Modifier.align(Alignment.CenterVertically)) {
                        Icon(imageVector = Icons.Filled.AddCircle, contentDescription ="add" )
                    }
                    Text(text = "Reps.", Modifier.align(Alignment.CenterVertically), fontSize = 18.sp)

                    OutlinedTextField(value = kg, onValueChange = {kg = it},
                        modifier = Modifier
                            .width(100.dp)
                            .align(Alignment.CenterVertically)
                        , keyboardOptions = KeyboardOptions( keyboardType = KeyboardType.Number),
                        suffix = { Text(text = "Kg")},
                        maxLines = 1
                    )
                }

                HorizontalDivider(modifier = Modifier
                    .height(8.dp)
                    .padding(top = 8.dp),
                    color = colorResource(id = R.color.orange))
            }
        }

    }
}

@Composable
fun MuscleGroupCard(group: String, image: Int,onClick : ()-> Unit, isSelected:Boolean) {


    val backgroundColor = if (isSelected) Color(0xFFFFA726) else Color.White

    Column {
        Card(
            modifier = Modifier
                .size(120.dp) // Tamaño de la tarjeta para cada grupo muscular
                .padding(8.dp)
                .clickable { onClick() },
            colors = CardDefaults.cardColors(containerColor = backgroundColor)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Image(painter = painterResource(id = image), contentDescription = "image")
            }

        }
        Text(text = group, fontSize = 16.sp, fontWeight = FontWeight.Bold, modifier = Modifier.align(Alignment.CenterHorizontally))
    }

}

@Composable
fun MyHeader(modifier: Modifier,nav: NavHostController) {

    Row(modifier = modifier.fillMaxWidth()) {
        ConstraintLayout(Modifier.fillMaxWidth()) {
            val (close, title,save) = createRefs()

            Icon(imageVector = Icons.Filled.Close, contentDescription = "atras",
                Modifier
                    .constrainAs(close) {
                        start.linkTo(parent.start)
                        bottom.linkTo(parent.bottom)
                        top.linkTo(parent.top)
                    }
                    .clickable {nav.popBackStack() }
            )
            Text(
                text = "Rutina", modifier = Modifier.constrainAs(title) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                    top.linkTo(parent.top)
                },
                fontSize = 18.sp, fontWeight = FontWeight.Bold
            )

            Icon(painter = painterResource(id = R.drawable.save), contentDescription = "save",
                Modifier
                    .constrainAs(save) {
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                    }
                    .clickable { }
                    .size(24.dp)
            )

        }

    }
}
