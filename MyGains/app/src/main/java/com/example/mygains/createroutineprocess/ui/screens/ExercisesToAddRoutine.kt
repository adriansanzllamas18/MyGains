package com.example.mygains.createroutineprocess.ui.screens


import android.graphics.Paint
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SelectableChipColors
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.LinearGradientShader
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.mygains.R
import com.example.mygains.createroutineprocess.data.models.ExerciseSet
import com.example.mygains.createroutineprocess.data.models.ExerciseWithSets
import com.example.mygains.createroutineprocess.data.models.StrengthExerciseModel
import com.example.mygains.createroutineprocess.ui.CreateRoutineViewModel
import com.example.mygains.createroutineprocess.ui.components.ExerciseItemList
import com.example.mygains.createroutineprocess.ui.components.ExerciseItemToAddList
import com.example.mygains.createroutineprocess.ui.components.ListInfoComponents
import com.example.mygains.createroutineprocess.ui.components.TitleAndImageIconComponent
import com.patrykandpatrick.vico.compose.axis.horizontal.bottomAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.compose.chart.line.lineSpec
import com.patrykandpatrick.vico.compose.component.shapeComponent
import com.patrykandpatrick.vico.compose.component.textComponent
import com.patrykandpatrick.vico.compose.dimensions.dimensionsOf
import com.patrykandpatrick.vico.core.axis.AxisPosition
import com.patrykandpatrick.vico.core.axis.formatter.AxisValueFormatter
import com.patrykandpatrick.vico.core.chart.line.LineChart
import com.patrykandpatrick.vico.core.component.shape.Shapes
import com.patrykandpatrick.vico.core.component.shape.shader.DynamicShader
import com.patrykandpatrick.vico.core.entry.entryModelOf
import com.patrykandpatrick.vico.core.entry.entryOf
import com.patrykandpatrick.vico.core.extension.mutableListOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun ExercisesToAddRoutine(
    nav: NavHostController,
    muscle_id: String,
    createRoutineViewModel: CreateRoutineViewModel
) {
    val pagerState = rememberPagerState(initialPage = 0, pageCount = {2})
    val coroutineScope = rememberCoroutineScope()
    val exercises by createRoutineViewModel.exercisesLive.observeAsState(initial = mutableListOf())
    val selectedExercise by createRoutineViewModel.exercisesSelectedlLive.observeAsState(initial = null)
    val exerciseWhitSets by createRoutineViewModel.exerciseWithSetsLive.observeAsState(initial = mutableListOf<ExerciseWithSets>())

    LaunchedEffect(Unit) {
        if (muscle_id.isNotEmpty()){
            createRoutineViewModel.getAllExercises(muscle_id)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            FilterChip(
                selected = pagerState.currentPage == 0,
                onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(0)
                    }
                },
                label = { Text(text = "Ejercicios", textAlign = TextAlign.Center,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .fillMaxWidth()) },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 4.dp),
                colors = getChipColors(isSelected = pagerState.currentPage == 0)
            )

            FilterChip(
                selected = pagerState.currentPage == 1,
                onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(1)
                    }
                },
                label = { Text(text = "Mi rutina",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .fillMaxWidth()
                    )},
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 4.dp),
                colors = getChipColors(isSelected = pagerState.currentPage == 1)
            )
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.padding(8.dp)
        ) { position ->
            when (position) {
                0 -> {
                    LazyColumn(Modifier.fillMaxSize(), rememberLazyListState()) {
                        items(exercises) {exercise->
                            ExerciseItemList(
                                borderColor = colorResource(id = R.color.orange_low),
                                exercise,
                                viewModel = createRoutineViewModel
                            )
                        }
                    }
                }

                1 -> {

                    /*creamos otra lista ya que cuando se elimina un elemento la
                    recomposicion intenta entrar a ese elemento que ya no existe antes de eliminarlo,
                    evitando que la interfaz intente acceder a datos mientras se están actualizando.
                    */


                    LazyColumn(Modifier.fillMaxSize(),rememberLazyListState()) {
                        if (exerciseWhitSets.isNotEmpty()){
                            itemsIndexed(
                                items = exerciseWhitSets,
                                key = { index, item -> index }
                            ) { index, exercise->

                                //asignamos el kay a cada elemento para que compose identifique cada uno cuando la vista cambie
                                key(exercise.exercise) {
                                    val borderToDelete  = remember {
                                        mutableIntStateOf(R.color.orange_low)
                                    }
                                    val state = rememberSwipeToDismissBoxState(
                                        positionalThreshold = { fullSize -> fullSize * 0.3f },
                                        confirmValueChange = { value ->
                                            if (value != SwipeToDismissBoxValue.Settled) {
                                                coroutineScope.launch {
                                                    delay(300) // Esperar a que termine la animación
                                                    createRoutineViewModel.removeExercise(exercise.exercise)
                                                }
                                                true
                                            } else {
                                                false
                                            }
                                        }
                                    )

                                    SwipeToDismissBox(
                                        state = state,
                                        enableDismissFromStartToEnd = false,
                                        backgroundContent = {
                                            val direction = state.targetValue // Obtiene el estado de deslizamiento

                                            borderToDelete.intValue = when (direction) {
                                                SwipeToDismissBoxValue.EndToStart -> R.color.orange
                                                else ->R.color.orange_low
                                            }

                                            Box(
                                                modifier = Modifier
                                                    .fillMaxSize()
                                                    .padding(horizontal = 16.dp),
                                                contentAlignment = when (direction) {
                                                    SwipeToDismissBoxValue.StartToEnd -> Alignment.CenterStart
                                                    SwipeToDismissBoxValue.EndToStart -> Alignment.CenterEnd
                                                    else -> Alignment.Center
                                                }
                                            ) {
                                                if (direction != SwipeToDismissBoxValue.Settled) {
                                                    Image(
                                                        modifier = Modifier.size(24.dp),
                                                        painter = painterResource(id = R.drawable.eliminar) ,
                                                        contentDescription = "Eliminar"
                                                    )
                                                }
                                            }
                                        }
                                        ,
                                        content = {
                                            ExerciseItemToAddList(
                                                borderColor = colorResource(id = borderToDelete.value),
                                                exercise = exercise.exercise,
                                                sets = exercise.sets,
                                                onSetsUpdated = { updatedSets ->
                                                    createRoutineViewModel.updateSetsForExercise(exercise.exercise, updatedSets)
                                                },
                                                viewModel = createRoutineViewModel
                                            )
                                        }
                                    )
                                }

                                }
                        }else{
                            item {
                                Image(
                                    modifier = Modifier.fillMaxWidth(),
                                    painter = painterResource(id = R.drawable.routinenodata),
                                    contentDescription = "rutina sin datos imagen")
                            }
                        }

                        item {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                                Button(
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(8.dp),
                                    enabled = true,
                                    onClick = {Log.i("createroutine",exerciseWhitSets.toString())},
                                )
                                {
                                    Text(text = "Crear rutina")
                                }
                                Image(
                                    modifier = Modifier
                                        .size(24.dp)
                                        .clickable { },
                                    painter = painterResource(id = R.drawable.reloj),
                                    contentDescription = "hora")
                            }
                        }
                    }
                }
            }
        }
    }

    selectedExercise?.let { ExerciseDetailAndConfigRoutineDialog(viewModel = createRoutineViewModel, strengthExerciseModel = it,coroutineScope,pagerState) }

}

// Función auxiliar para definir los colores de los chips
@Composable
fun getChipColors(isSelected: Boolean) = SelectableChipColors(
    containerColor = if (isSelected) Color(0xFFCA5300) else Color(0xFFFCE5D8),
    labelColor = if (isSelected) Color.White else Color.Black,
    leadingIconColor = Color.White,
    trailingIconColor = Color.White,
    disabledContainerColor = Color.Gray.copy(alpha = 0.5f),
    disabledLabelColor = Color.White.copy(alpha = 0.5f),
    disabledLeadingIconColor = Color.Gray.copy(alpha = 0.5f),
    disabledTrailingIconColor = Color.Gray.copy(alpha = 0.5f),
    selectedContainerColor = Color(0xFFCA5300),
    disabledSelectedContainerColor = Color.Gray.copy(alpha = 0.8f),
    selectedLabelColor = Color.White,
    selectedLeadingIconColor = Color.White,
    selectedTrailingIconColor = Color.White
)




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseDetailAndConfigRoutineDialog(
    viewModel: CreateRoutineViewModel,
    strengthExerciseModel:StrengthExerciseModel,
    coroutineScope: CoroutineScope,
    pagerState: PagerState){

    val showDetail by  viewModel.showDetailLive.observeAsState(initial = false)
    val lazyState = rememberLazyListState()

    if (showDetail){
        ModalBottomSheet(
            modifier = Modifier.fillMaxSize(),
            onDismissRequest = { viewModel.setExercisesVisibility(false) }
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                state = lazyState
            )
            {

                item {
                    ExerciseImageSection(strengthExerciseModel)
                }


                item {
                    Spacer(modifier = Modifier.padding(8.dp))
                }

                item {
                    ExercisesQuickInfo(strengthExerciseModel)
                }

                item {
                    ExerciseAllDetails(strengthExerciseModel)
                }
                
                item {
                        Button(
                            onClick = {
                                viewModel.addExercise(exercise = strengthExerciseModel)
                                viewModel.setExercisesVisibility(false)
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(1)
                                }
                                      },
                            modifier = Modifier.fillMaxSize()
                        )
                        {
                            Text(text = "Añadir Ejercicio")
                        }
                }

            }
        }
    }
}


@Composable
private fun LastTimesGrafic() {
    // Datos de peso originales
    val weightData = listOf(
        "01/01/2023" to 74.5f,
        "08/01/2023" to 74.8f,
        "15/01/2023" to 74.2f,
        "08/01/2023" to 74.8f,
        "15/01/2023" to 74.2f,
        "08/01/2023" to 74.8f,
        "15/01/2023" to 74.2f,
        "08/01/2023" to 74.8f,
        "15/01/2023" to 74.2f,
    )

    // Nueva línea de datos (puedes ajustar estos valores según tus necesidades)
    val secondLineData = listOf(
        "01/01/2023" to 9f,
        "08/01/2023" to 3f,
        "15/01/2023" to 3f,
        "08/01/2023" to 7f,
        "15/01/2023" to 70f,
        "08/01/2023" to 8f,
        "15/01/2023" to 2f,
        "08/01/2023" to 7f,
        "15/01/2023" to 7f,
    )

    // Extraer etiquetas y valores
    val labels = weightData.map { it.first }

    // Configurar entradas para ambas líneas
    val entries1 = weightData.mapIndexed { index, (_, weight) ->
        entryOf(x = index.toFloat(), y = weight)
    }

    val entries2 = secondLineData.mapIndexed { index, (_, weight) ->
        entryOf(x = index.toFloat(), y = weight)
    }

    // Formateador de valores para el eje X
    val xAxisFormatter = AxisValueFormatter<AxisPosition.Horizontal.Bottom> { value, _ ->
        labels.getOrNull(value.toInt()) ?: ""
    }

    // Configurar el eje X en el gráfico de Vico
    val bottomAxis = bottomAxis(
        valueFormatter = xAxisFormatter,
        label = textComponent(
            color = Color.Black, // Color del texto
            padding = dimensionsOf(horizontal = 3.dp, vertical = 2.dp),
            margins = dimensionsOf(bottom = 4.dp),
        )
    )

    // Modelo con ambas líneas de datos
    val model = entryModelOf(entries1, entries2)

    // Personalización de los puntos (dots) para la primera línea
    val pointComponent1 = shapeComponent(
        shape = Shapes.pillShape,
        color = Color(0xFFCA5300),
        strokeWidth = 2.dp,
        strokeColor = Color.White
    )

    // Personalización de los puntos para la segunda línea
    val pointComponent2 = shapeComponent(
        shape = Shapes.pillShape,
        color = Color(0xFF0072CA),  // Color diferente para la segunda línea
        strokeWidth = 2.dp,
        strokeColor = Color.White
    )

    // Etiqueta para la primera línea
    val labelComponent1 = textComponent(
        color = Color.DarkGray,
        background = shapeComponent(
            shape = Shapes.pillShape,
            color = Color.White.copy(alpha = 0.8f),
            strokeWidth = 1.dp,
            strokeColor = Color.LightGray
        ),
        padding = dimensionsOf(horizontal = 4.dp, vertical = 2.dp),
        margins = dimensionsOf(bottom = 4.dp),
        textSize = 10.sp,
        textAlign = Paint.Align.LEFT,
    )

    // Etiqueta para la segunda línea
    val labelComponent2 = textComponent(
        color = Color.DarkGray,
        background = shapeComponent(
            shape = Shapes.pillShape,
            color = Color.White.copy(alpha = 0.8f),
            strokeWidth = 1.dp,
            strokeColor = Color.LightGray
        ),
        padding = dimensionsOf(horizontal = 4.dp, vertical = 2.dp),
        margins = dimensionsOf(bottom = 4.dp),
        textSize = 10.sp,
        textAlign = Paint.Align.LEFT,
    )

    // Shader para la primera línea
    val dynamicShader1 = DynamicShader { _, left, top, right, bottom ->
        LinearGradientShader(
            from = Offset(left, top),
            to = Offset(left, bottom),
            colors = listOf(Color(0xFFCA5300).copy(alpha = 0.5f), Color.Transparent),
            colorStops = listOf(0f, 1f),
            tileMode = TileMode.Clamp
        )
    }

    // Shader para la segunda línea
    val dynamicShader2 = DynamicShader { _, left, top, right, bottom ->
        LinearGradientShader(
            from = Offset(left, top),
            to = Offset(left, bottom),
            colors = listOf(Color(0xFF0072CA).copy(alpha = 0.5f), Color.Transparent),
            colorStops = listOf(0f, 1f),
            tileMode = TileMode.Clamp
        )
    }

    Column(Modifier.padding(vertical = 16.dp)) {
        Row {
            Image(
                modifier = Modifier.size(24.dp),
                painter = painterResource(id = R.drawable.analitica),
                contentDescription = "icon image"
            )
            Text(
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(8.dp),
                text = ("últimos entrenos")
            )
        }
        Chart(
            chart = lineChart(
                lines = listOf(
                    lineSpec(
                        lineColor = Color(0xFFCA5300),
                        lineBackgroundShader = dynamicShader1,
                        point = pointComponent1,
                        pointSize = 8.dp,
                        dataLabel = labelComponent1,
                    ),
                    lineSpec(
                        lineColor = Color(0xFF0072CA),  // Color diferente para la segunda línea
                        lineBackgroundShader = dynamicShader2,
                        point = pointComponent2,
                        pointSize = 8.dp,
                        dataLabel = labelComponent2,
                    )
                ),
                spacing = 80.dp,
                pointPosition = LineChart.PointPosition.Center
            ),
            model = model,
            bottomAxis = bottomAxis
        )

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            LegendItem(color = Color(0xFFCA5300), text = "Kg")
            Spacer(modifier = Modifier.width(16.dp))
            LegendItem(color = Color(0xFF007ACC), text = "Repeticiones")
        }
    }
}

@Composable
fun LegendItem(color: Color, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(12.dp)
                .background(color, shape = CircleShape)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = text, fontSize = 14.sp, color = Color.Black)
    }
}

@Composable
fun Stepper(
    modifier: Modifier,
    value: Int,
    onValueChange: (Int) -> Unit,
    minValue: Int = 0,
    maxValue: Int = 800
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        IconButton(
            modifier = modifier.size(24.dp),
            onClick = { if (value > minValue) onValueChange(value - 1) },
            enabled = value > minValue
        ) {
            Icon(painter =  painterResource(id = R.drawable.remove), contentDescription ="eliminar" )
        }

        Text(text = value.toString(), fontSize = 20.sp)

        IconButton(
            modifier = modifier.size(24.dp),
            onClick = { if (value < maxValue) onValueChange(value + 1) },
            enabled = value < maxValue
        ) {
           Icon(painter =  painterResource(id = R.drawable.add), contentDescription ="añadir" )
        }
    }
}

@Composable
private fun ExerciseImageSection(strengthExerciseModel: StrengthExerciseModel){
    Card(modifier = Modifier
        .fillMaxWidth()
        .height(300.dp)
        .padding(vertical = 16.dp),
        shape = RoundedCornerShape(12)
    ) {
        Box(contentAlignment = Alignment.BottomStart){
            AsyncImage(
                model = strengthExerciseModel.image_url,
                contentDescription = "exercise image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Box(
                Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            0f to Color.White.copy(alpha = 0f),
                            1f to colorResource(id = R.color.orange_low).copy(alpha = 0.9f)
                        )
                    ))
            Column(modifier = Modifier .padding(horizontal = 24.dp, vertical = 16.dp)) {
                Text(strengthExerciseModel.name?:"",
                    fontSize = 40.sp,
                    maxLines = 1,
                    minLines = 1,
                    textAlign = TextAlign.Center,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .basicMarquee()
                )
                Row {
                    Image(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(id = R.drawable.mancuerna),
                        contentDescription = "icon image")
                    Text(
                        modifier = Modifier.padding(start = 8.dp),
                        text = strengthExerciseModel.difficulty?:""
                    )
                }
            }

        }
    }
}

@Composable
private fun ExercisesQuickInfo(strengthExerciseModel: StrengthExerciseModel){
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center) {
        Card(
            modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 8.dp)
                .weight(1f),
            colors = CardColors(
                containerColor = colorResource(id = R.color.orange_low),
                contentColor = Color.Black,
                disabledContentColor = Color.Black,
                disabledContainerColor = Color.Gray)
        )
        {

            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                TitleAndImageIconComponent(title = "Músculos", icon = R.drawable.musculo)

                Text(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    text = strengthExerciseModel.primary_muscle?:""
                )
                Text(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    fontSize = 12.sp,
                    text = strengthExerciseModel.secondary_muscles.toString()?:""
                )
            }


        }
        Card(
            modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 8.dp)
                .weight(1f),
            colors = CardColors(
                containerColor = colorResource(id = R.color.orange_low),
                contentColor = Color.Black,
                disabledContentColor = Color.Black,
                disabledContainerColor = Color.Gray)
        )
        {
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                TitleAndImageIconComponent(title = "Calorías", icon = R.drawable.calorias)

                Text(
                    fontSize = 18.sp,
                    text = strengthExerciseModel.calories_estimated?:""
                )
                Text(
                    fontSize = 12.sp,
                    text = "Estimación"
                )
            }
        }
    }
}

@Composable
private fun ExerciseAllDetails(strengthExerciseModel: StrengthExerciseModel){
    var titleSelected by remember {
        mutableIntStateOf(1)
    }
    Spacer(modifier = Modifier.padding(8.dp))
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .clickable { titleSelected = 1 },
            text = "Descripción",
            fontWeight = if (titleSelected == 1) FontWeight.Bold else FontWeight.Black,
            color = if (titleSelected == 1) Color.Black else Color.LightGray
        )
        Text(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .clickable { titleSelected = 2 },
            text = "Técnica",
            fontWeight = if (titleSelected == 2) FontWeight.Bold else FontWeight.Black,
            color = if (titleSelected == 2) Color.Black else Color.LightGray

        )
        Text(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .clickable { titleSelected = 3 },
            text = "Variaciones",
            fontWeight = if (titleSelected == 3) FontWeight.Bold else FontWeight.Black,
            color = if (titleSelected == 3) Color.Black else Color.LightGray
        )
    }
    HorizontalDivider()


    AnimatedVisibility(
        visible = titleSelected == 1,
        enter = fadeIn() + expandVertically(),
        exit = fadeOut() + shrinkVertically()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = strengthExerciseModel.description ?: "",
                modifier = Modifier.fillMaxWidth(),
                fontSize = 16.sp
            )


            TitleAndImageIconComponent(title = "Ideal para", icon = R.drawable.ideal)
            Row(horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.CenterVertically) {
                strengthExerciseModel.bestFor?.let { ListInfoComponents(list = it ) }
            }
            TitleAndImageIconComponent(title = "Equipamiento necesario", icon = R.drawable.equipment)
            strengthExerciseModel.equipment?.let { ListInfoComponents(list = it ) }
            LastTimesGrafic()
        }
    }

// Para la opción 2
    AnimatedVisibility(
        visible = titleSelected == 2,
        enter = fadeIn() + expandVertically(),
        exit = fadeOut() + shrinkVertically()
    ) {
        Column {
            TitleAndImageIconComponent(title = "Consejos", icon = R.drawable.tip)
            strengthExerciseModel.tips?.let { ListInfoComponents(list = it ) }
            TitleAndImageIconComponent(title = "Errores comunes", icon = R.drawable.error)
            strengthExerciseModel.commonMistakes?.let { ListInfoComponents(list = it ) }
        }
    }

}

@Composable
fun ListDeployed( title:String, list:MutableList<String>, initial:Boolean=false){
    var show by remember {
        mutableStateOf(initial)
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.CenterVertically) {
            Text(text = title, modifier = Modifier.weight(1f))
            IconButton(
                modifier = Modifier.size(16.dp),
                onClick = {
                    show= !show
                }
            ){
                Icon( painter = if (show) painterResource(id = R.drawable.listup) else painterResource(id = R.drawable.listdowns),
                    contentDescription = "desplegar",)
            }
        }

        AnimatedVisibility(visible = show) {
            Card(modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
                colors = CardColors(
                    containerColor = colorResource(id = R.color.orange_low),
                    contentColor = Color.Black,
                    disabledContentColor = Color.Black,
                    disabledContainerColor = Color.Gray)
            ) {
        Column(modifier = Modifier.padding(start = 16.dp)) {
                    list.forEach {
                        Text(text = "${it}.", fontSize = 14.sp)
                    }
                }
            }

        }
    }
}



