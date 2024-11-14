package com.example.mygains.exercisesplan.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerColors
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.mygains.exercisesplan.data.MuscleGroupe
import com.example.mygains.R
import com.example.mygains.exercisesplan.data.Exercises
import com.example.mygains.exercisesplan.data.RoutineDayData
import com.example.mygains.exercisesplan.data.SeriesAndReps
import com.example.mygains.exercisesplan.data.ExcerciseType
import com.example.mygains.extras.utils.FormatterUtils
import com.example.mygains.login.ui.Loader


@Composable
fun ExcercisesPlanCompossable(nav: NavHostController, date: String) {

    val excercisesPlanViewModel:ExcercisesPlanViewModel= hiltViewModel()

    val routineDayData by excercisesPlanViewModel._routineDayDataLife.observeAsState(initial = RoutineDayData() )
    val result by excercisesPlanViewModel._saveResultLife.observeAsState(initial = null )
    val isLoading by excercisesPlanViewModel._isLoadingLife.observeAsState(false)
    val showAlert by excercisesPlanViewModel._isAlertLife.observeAsState(false)

    //asignamos a routinedata la fecha de la nav
    routineDayData.date=date

    Column(

        Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.systemBars)) {
        MyHeader(modifier = Modifier
            .align(Alignment.CenterHorizontally)
            .padding(16.dp),nav,routineDayData,excercisesPlanViewModel,showAlert)

       ConstraintLayout {
           val (body) = createRefs()
           MyBodyRoutineExercisesType(modifier= Modifier
               .constrainAs(body) {
                   top.linkTo(parent.top)
                   start.linkTo(parent.start)

               }
               .padding(16.dp), excercisesPlanViewModel,routineDayData)
       }



        LaunchedEffect(result) {
                if (result == true){
                    nav.popBackStack()
                }
            }
    }

        if (showAlert){
            AlertDialog(
                modifier = Modifier.fillMaxWidth(),
                onDismissRequest = {},
                confirmButton = {
                    Button(onClick = {
                        excercisesPlanViewModel.setAlert(false)
                    }
                    ) {
                        Text(text = "Volver")
                    }},
                title = { Text(text = "Error")},
                text = { Text(text = "Compruebe que todos los campos esten completos o intentelo de nuevo más tarde.")})
        }

    Loader(isLoading = isLoading)
}

@Composable
fun MyBodyRoutineExercisesType(modifier: Modifier, excercisesPlanViewModel: ExcercisesPlanViewModel, routineDayData: RoutineDayData) {

// Estado para almacenar el grupo seleccionado
    var selectedExcerciseType by remember { mutableStateOf<ExcerciseType?>(null) }

    val excerciseTypes = listOf<ExcerciseType>(
        ExcerciseType("Cardio",R.drawable.cardio),
        ExcerciseType("Fuerza",R.drawable.fuerza)
    )
    var expanded by remember { mutableStateOf(false) }



   routineDayData.exerciseType = selectedExcerciseType?.name ?: ""
    Column(modifier) {
            Text(
                text = "Tipo de ejercicio",
                fontSize = 24.sp,
                modifier = Modifier.padding(8.dp),
                fontStyle = FontStyle(R.font.poppins)
            )

        Row(modifier = Modifier.horizontalScroll(rememberScrollState())) {
            excerciseTypes.forEach { excerciseType ->
                MuscleGroupCard(
                    group = excerciseType.name,
                    image = excerciseType.image,
                    onClick = { selectedExcerciseType = excerciseType },
                    isSelected = selectedExcerciseType == excerciseType
                )
            }
        }
            if (selectedExcerciseType?.name  == "Fuerza" && !expanded ){
                MyBodyLiftExcercises(
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),excercisesPlanViewModel,routineDayData)
            }
        }
}
@Composable
fun MyBodyLiftExcercises(modifier: Modifier,excercisesPlanViewModel: ExcercisesPlanViewModel,routineDayData: RoutineDayData) {

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
        Row(modifier = Modifier.horizontalScroll(rememberScrollState())) {
            muscleGroups.forEach { muscle ->
                MuscleGroupCard(
                    group = muscle.name,
                    image = muscle.image,
                    onClick = { selectedMuscleGroup = muscle },
                    isSelected = selectedMuscleGroup == muscle
                )
            }
        }
        //al principio como no hay nada seleccionado la vista de repeticiones y series no se muestra
        selectedMuscleGroup?.let { MyExercisesSriesAndReps(modifier = Modifier
            .padding(top = 16.dp)
            .fillMaxSize(), muscleGroupe = it, excercisesPlanViewModel = excercisesPlanViewModel, routineDayData = routineDayData ) }

    }
}

@Composable
fun MyExercisesSriesAndReps(
    modifier: Modifier = Modifier,
    muscleGroupe: MuscleGroupe,
    excercisesPlanViewModel: ExcercisesPlanViewModel,
    routineDayData: RoutineDayData
) {
    var numberSeries by remember { mutableStateOf(0) }
    var excerciseName by remember { mutableStateOf("") }

    // Convertir la lista de series en un `mutableStateListOf` para hacerla observable
    val seriesAndReps = remember { mutableStateListOf<SeriesAndReps>() }

    // Ajuste de la lista de seriesAndReps cuando cambia `numberSeries`
    LaunchedEffect(numberSeries) {
        if (seriesAndReps.size < numberSeries) {
            repeat(numberSeries - seriesAndReps.size) {
                seriesAndReps.add(SeriesAndReps(serie = "${seriesAndReps.size + 1}", reps = "0", weght = "0"))
            }
        } else if (seriesAndReps.size > numberSeries) {
            repeat(seriesAndReps.size - numberSeries) {
                seriesAndReps.removeLast()
            }
        }

        // Actualizar `routineDayData.exercises`
        routineDayData.exercises = Exercises(
            muscle = muscleGroupe.name,
            excerciseName = excerciseName,
            seriesAndReps = seriesAndReps
        )
    }

    LazyColumn(modifier = modifier.fillMaxSize()) {
        item {
            // Parte de UI para el nombre del grupo muscular y el número de series
            Row(
                modifier = Modifier.padding(8.dp)
            ) {
                Text(
                    text = muscleGroupe.name,
                    fontSize = 24.sp,
                    modifier = Modifier
                        .weight(1f)
                        .align(Alignment.CenterVertically), // Usa weight para que se expanda
                    fontStyle = FontStyle(R.font.poppins)
                )

                IconButton(onClick = { if (numberSeries > 0) numberSeries-- }) {
                    Icon(painter = painterResource(id = R.drawable.remove_circle_24), contentDescription = "remove")
                }

                Text(text = numberSeries.toString(), fontSize = 18.sp,modifier= Modifier.align(Alignment.CenterVertically))

                IconButton(onClick = { numberSeries++ }) {
                    Icon(imageVector = Icons.Filled.AddCircle, contentDescription = "add")
                }

                Text(text = "Ser.", fontSize = 18.sp,modifier= Modifier.align(Alignment.CenterVertically))
            }

            OutlinedTextField(
                value = excerciseName,
                onValueChange = { name -> excerciseName = name },
                modifier = Modifier
                    .padding(top = 16.dp)
                    .width(200.dp),
                placeholder = { Text(text = "Nombre ejercicio..") }
            )
        }

        itemsIndexed(seriesAndReps) { index, data ->
            // Fila para cada serie y controles para ajustar reps y peso
            Row(Modifier.padding(top = 18.dp, start = 8.dp, end = 8.dp)) {
                Text(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    text = "${index + 1} º Serie", fontSize = 18.sp
                )

                IconButton(modifier= Modifier.align(Alignment.CenterVertically),onClick = {
                    val newReps = (data.reps.toIntOrNull() ?: 1).coerceAtLeast(1) - 1
                    seriesAndReps[index] = data.copy(reps = newReps.toString())
                }) {
                    Icon(painter = painterResource(id = R.drawable.remove_circle_24), contentDescription = "remove")
                }

                // Mostrar el número de reps actual y actualizarlo en la lista
                Text(
                    text = data.reps,
                    Modifier.align(Alignment.CenterVertically),
                    fontSize = 18.sp
                )

                IconButton(modifier= Modifier.align(Alignment.CenterVertically),onClick = {
                    val newReps = (data.reps.toIntOrNull() ?: 0) + 1
                    seriesAndReps[index] = data.copy(reps = newReps.toString())
                }) {
                    Icon(imageVector = Icons.Filled.AddCircle, contentDescription = "add")
                }

                Text(text = "Reps.", Modifier.align(Alignment.CenterVertically), fontSize = 18.sp)

                OutlinedTextField(
                    value = data.weght,
                    onValueChange = { weight ->
                        seriesAndReps[index] = data.copy(weght = weight)
                    },
                    modifier = Modifier
                        .width(100.dp)
                        .align(Alignment.CenterVertically),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    suffix = { Text(text = "Kg") },
                    maxLines = 1
                )
            }

            // Divider entre las series
            HorizontalDivider(
                modifier = Modifier
                    .height(1.dp)
                    .padding(vertical = 16.dp),
                color = colorResource(id = R.color.orange)
            )
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyHeader(modifier: Modifier,nav: NavHostController, routineDayData: RoutineDayData, excercisesPlanViewModel: ExcercisesPlanViewModel,showAlert:Boolean) {

    var expanded:Boolean by remember {
        mutableStateOf(false)
    }
    var initialPiker by remember {
        mutableStateOf(false)
    }


    if (expanded) initialPiker = true

    val timeSelected = rememberTimePickerState()

    Row(modifier = modifier.fillMaxWidth()) {
        ConstraintLayout(Modifier.fillMaxWidth()) {
            val (close, title,save,hour,hourText) = createRefs()

            Icon(imageVector = Icons.Filled.Close, contentDescription = "atras",
                Modifier
                    .constrainAs(close) {
                        start.linkTo(parent.start)
                        bottom.linkTo(parent.bottom)
                        top.linkTo(parent.top)
                    }
                    .clickable { nav.popBackStack() }
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
                    .clickable { excercisesPlanViewModel.saveDataRoutine(routineDayData) }
                    .size(24.dp)
            )

            Icon(painter = painterResource(id = R.drawable.calendar_hour), contentDescription = "hour",
                Modifier
                    .constrainAs(hour) {
                        end.linkTo(save.start)
                        top.linkTo(parent.top)
                    }
                    .padding(end = 16.dp)
                    .clickable {
                        expanded = true
                    }
                    .size(24.dp),
                tint = if (!initialPiker) Color.Red else Color.Black
            )

            if (initialPiker){
                routineDayData.timeOfDay = FormatterUtils().formatHour(timeSelected)
                Text(
                    modifier = Modifier
                        .constrainAs(hourText) {
                            end.linkTo(hour.start)
                            top.linkTo(parent.top)
                        }
                        .padding(end = 8.dp),
                    color = colorResource(id = R.color.orange),
                    text =FormatterUtils().formatHour(timeSelected),
                )
            }

        }

    }

    if (expanded){

        ModalBottomSheet(
            sheetState =  rememberModalBottomSheetState(skipPartiallyExpanded = true),
            containerColor = colorResource(id = R.color.orange_low),
            onDismissRequest = { expanded= false },
            scrimColor = BottomSheetDefaults.ScrimColor,
        ) {
            Column(modifier = Modifier.align(Alignment.CenterHorizontally) ){
                TimePicker(state = timeSelected,Modifier.align(Alignment.CenterHorizontally),
                    colors = TimePickerColors(clockDialColor = colorResource(id = R.color.orange),
                        selectorColor =colorResource(id = R.color.orange_low),
                        containerColor = colorResource(id = R.color.orange),
                        periodSelectorSelectedContentColor = colorResource(id = R.color.orange_low),
                        clockDialUnselectedContentColor = colorResource(id = R.color.orange_low),
                        clockDialSelectedContentColor = colorResource(id = R.color.orange),
                        periodSelectorSelectedContainerColor = colorResource(id = R.color.black),
                        periodSelectorUnselectedContainerColor = colorResource(id = R.color.orange),
                        periodSelectorUnselectedContentColor = colorResource(id = R.color.orange_low),
                        periodSelectorBorderColor = colorResource(id = R.color.black),
                        timeSelectorUnselectedContainerColor = colorResource(id = R.color.black),
                        timeSelectorSelectedContainerColor = colorResource(id = R.color.orange),
                        timeSelectorUnselectedContentColor = colorResource(id = R.color.orange_low),
                        timeSelectorSelectedContentColor = colorResource(id = R.color.orange_low) )
                )
                Log.i("hour",FormatterUtils().formatHour(timeSelected))
                Icon(imageVector = Icons.Default.Info, contentDescription ="info",
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    tint = colorResource(id = R.color.orange))
                Text(text = "Esta será la ahora  del día en la que empezarás el ejercicio , recuerda que si añades varios ejercicios en la misma hora estos aparecerán relacionados.",
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(16.dp),
                    textAlign = TextAlign.Center,
                    color = Color.Gray)
            }
        }
    }
}
