package com.example.mygains.userinfo.ui.screens

import android.content.Context
import android.util.DisplayMetrics
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.LinearGradientShader
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
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
import coil.compose.rememberAsyncImagePainter
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.mygains.R
import com.example.mygains.userinfo.data.models.WeightRegister
import com.example.mygains.userinfo.data.models.UserData
import com.example.mygains.userinfo.ui.UserInfoViewModel
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

import java.time.LocalDate


@Composable
fun UserInfoComposable(nav: NavHostController) {

    val userInfoViewModel: UserInfoViewModel = hiltViewModel()

    val result: UserData by userInfoViewModel.userLive.observeAsState(initial = UserData())
    userInfoViewModel.readUserInfo()

    // Usamos LazyColumn en lugar de Column para una lista desplazable
    LazyColumn(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        // Header, Imagen, Títulos y demás contenidos se agregan como items en LazyColumn
        item {
            // Aquí puedes agregar tu header, si es necesario
            // Por ejemplo, si tienes un encabezado con título o una imagen de portada.
            Text(
                text = "Información del Usuario",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        // Imagen del Usuario
        item {
            UserInfoImage(
                userData = result
            )
        }

        // Título de Información del Usuario
        item {
            UserInfoTitle(
                modifier = Modifier.padding(top = 24.dp, bottom = 24.dp)
            )
        }

        // Información adicional en tarjeta
        item {
            UserInfiCardView(
                userData = result,
                infoViewModel = userInfoViewModel
            )
        }

        // Progreso diario del usuario
        item {
            UserDailyProgress(
                modifier = Modifier.padding(top = 24.dp, bottom = 24.dp),
                userInfoViewModel = userInfoViewModel
            )
        }
    }
}


@Composable
fun UserDailyProgress(modifier: Modifier, userInfoViewModel: UserInfoViewModel) {
    val listweights: MutableList<WeightRegister> by userInfoViewModel.listWeightsLive.observeAsState(initial = mutableListOf<WeightRegister>())

    Column(modifier) {
        // Estado para almacenar qué botón está seleccionado
        var selectedButton by remember { mutableStateOf("Mes") }

        LaunchedEffect(selectedButton) {
            userInfoViewModel.getWeightsByTime(selectedButton)
        }
        // Lista de botones para seleccionar
        val buttons = listOf("Semana", "Mes", "Año")

        // Estilos de colores para los botones seleccionados y no seleccionados
        val selectedColor = colorResource(id = R.color.orange) // Verde para botón seleccionado
        val unselectedColor = Color(0xFFB0BEC5) // Gris para botón no seleccionado

        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp) // Espaciado entre botones
        ) {
            buttons.forEach { buttonLabel ->
                Button(
                    onClick = {
                        selectedButton = buttonLabel
                              },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedButton == buttonLabel) selectedColor else unselectedColor
                    ),
                    modifier = Modifier
                        .height(50.dp)
                        .width(100.dp)
                ) {
                    Text(text = buttonLabel, color = Color.White)
                }
            }
        }

        if (listweights.isNotEmpty()) {

            val dateWeightList = listweights.map { it.date to it.weight }
            // Extraer etiquetas y valores
            val labels = dateWeightList.map { it.first }

            // Formateador de valores para el eje X
            val xAxisFormatter = AxisValueFormatter<AxisPosition.Horizontal.Bottom> { value, _ ->
                labels.getOrNull(value.toInt()) ?: ""
            }

            // Configurar el eje X en el gráfico de Vico
            val bottomAxis = bottomAxis(valueFormatter = xAxisFormatter, label = textComponent(
                color = Color.Black, // Color del texto
                padding = dimensionsOf(horizontal = 3.dp, vertical = 2.dp),
                margins = dimensionsOf(bottom = 4.dp), )
            )

            val entries = dateWeightList.mapIndexed { index, (_, weight) ->
                entryOf(x = index.toFloat(), y = weight.toFloat())
            }

            val model = entryModelOf(entries)

            // Personalización de los puntos (dots)
            val pointComponent = shapeComponent(
                shape = Shapes.pillShape,
                color = Color(0xFFCA5300),
                strokeWidth = 2.dp,
                strokeColor = Color.White
            )

            // Etiqueta que aparecerá encima de cada punto
            val labelComponent = textComponent(
                color = Color.DarkGray,
                background = shapeComponent(
                    shape = Shapes.pillShape,
                    color = Color.White.copy(alpha = 0.8f),
                    strokeWidth = 1.dp,
                    strokeColor = Color.LightGray
                ),
                padding = dimensionsOf(horizontal = 4.dp, vertical = 2.dp),
                margins = dimensionsOf(bottom = 4.dp),
                textSize = 10.sp
            )

            // Shader dinámico con la firma correcta
            val dynamicShader = DynamicShader { _, left, top, right, bottom ->
                LinearGradientShader(
                    from = Offset(left, top),   // Comienza en la parte superior izquierda
                    to = Offset(left, bottom),  // Termina en la parte inferior izquierda
                    colors = listOf(Color(0xFFCA5300).copy(alpha = 0.5f), Color.Transparent), // Azul a transparente
                    colorStops = listOf(0f, 1f), // Degradado de arriba a abajo
                    tileMode = TileMode.Clamp
                )
            }
            Chart(
                chart = lineChart(
                    lines = listOf(
                        lineSpec(
                            lineColor = Color(0xFFCA5300),
                            lineBackgroundShader = dynamicShader,
                            point = pointComponent,
                            pointSize = 8.dp,
                            dataLabel = labelComponent,
                        )
                    ),
                    spacing = 80.dp,
                    pointPosition = LineChart.PointPosition.Center
                ),
                model = model,
                bottomAxis = bottomAxis
            )
        } else {
            LottieExample(Modifier.fillMaxWidth())
        }


    }
}


@Composable
fun UserInfoTitle(modifier: Modifier) {
    Row(modifier = modifier.fillMaxWidth()){
        Text(text = " Peso y Registro", fontSize = 30.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun UserInfiCardView(userData: UserData, infoViewModel: UserInfoViewModel) {


    var isAlert by remember{mutableStateOf(false)}
    var showSaveButton by remember{mutableStateOf(false)}

    Card(modifier= Modifier.fillMaxWidth(), elevation = CardDefaults.cardElevation(defaultElevation = 8.dp), colors = CardDefaults.cardColors(containerColor = colorResource(
        id = R.color.orange_low
    )), onClick = {isAlert = true}, shape = RoundedCornerShape(28.dp)
    ) {
        ConstraintLayout(
            Modifier
                .fillMaxWidth()
                .padding(16.dp)) {

            val (icon,weight,date) = createRefs()

            Icon(painter = painterResource(id = R.drawable.weight) , contentDescription ="peso",
                Modifier
                    .size(28.dp)
                    .constrainAs(icon) {
                        start.linkTo(parent.start)
                    }, colorResource(id = R.color.black))

            Text(text = userData.weight +" Kg", fontSize = 24.sp, fontStyle = FontStyle(R.font.poppins), modifier = Modifier
                .constrainAs(weight) {
                    start.linkTo(icon.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
                .padding(start = 8.dp), color = Color.Black)

            Text(text = " ${userData.lastUpdateWeight} act.", fontSize = 18.sp, fontStyle = FontStyle(R.font.poppins), modifier = Modifier.constrainAs(date){
                end.linkTo(parent.end)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            }, color = Color.Black)
        }
    }

    if (isAlert){
        var newWeight by remember{ mutableStateOf("") }

        AlertDialog(onDismissRequest = { },
            confirmButton = { Button(enabled = showSaveButton, onClick = {
                isAlert= false
                showSaveButton= false
                infoViewModel.saveWeight(WeightRegister(LocalDate.now().toString(),newWeight))
            }
            ) {
                Text(text = "Guardar")
            }}
            ,
            dismissButton = {
                Button(onClick = {
                    isAlert= false
                    showSaveButton= false
                }
                ) {
                    Text(text = "Cancelar")
                }
            },
            title = { Text(text = "Registar  peso")},
            text = { Box(){

                TextField(value = newWeight, onValueChange = {
                    if (it.isEmpty()) showSaveButton=false else  showSaveButton=true
                    newWeight = it
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(38.dp))
                        .padding(24.dp),
                    suffix = { Text(text = "Kg")},
                    placeholder = { Text(text = "Nuevo peso")},
                    maxLines = 1,
                    singleLine = true,
                    keyboardOptions = KeyboardOptions( keyboardType = KeyboardType.Number),
                    colors = TextFieldDefaults.colors(focusedContainerColor = Color.White,   // Color de fondo cuando está enfocado
                        unfocusedContainerColor = Color.White, // Color de fondo cuando no está enfocado
                        focusedIndicatorColor = Color.Transparent,   // Eliminar la línea inferior cuando está enfocado
                        unfocusedIndicatorColor = Color.Transparent ),
                    shape = RoundedCornerShape(38.dp)
                )
            }},
            icon = { Icon(modifier = Modifier.size(34.dp), painter = painterResource(id = R.drawable.weight), contentDescription ="error",
                tint = Color(LocalContext.current.getColor(R.color.orange)))
            }, containerColor =Color(0xFFFCE5D8))
    }
}

@Composable
fun UserInfoImage( userData: UserData) {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center){
            if (userData.image!!.isNotEmpty()){
                Image(painter = rememberAsyncImagePainter(userData.image), contentDescription = "image",
                    Modifier
                        .size(180.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop)
            }else{
                Image(imageVector = Icons.Filled.AccountCircle, contentDescription = "image",
                    Modifier
                        .size(180.dp))
            }


            Text(text = userData.name + " " + userData.first_name +" " +userData.second_name
                , fontSize = 18.sp, fontWeight = FontWeight.Bold)

            Text(text = userData.email, fontSize = 18.sp, color = Color(color = 0xFFCA5300))
        }
}

@Composable
fun UserInfoHeader(modifier: Modifier, nav: NavHostController, userData: UserData) {


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
                    .clickable { nav.popBackStack() }
            )
            Text(text = "Perfil", modifier = Modifier.constrainAs(title){
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
fun LottieExample(modifier: Modifier = Modifier) {
    // Carga la composición del recurso Lottie
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
            // Renderiza la animación
            LottieAnimation(
                composition = composition,
                progress = progress,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(8.dp)
                    .size(300.dp)
            )
            Row {

                Icon(painter = painterResource(id = R.drawable.weight), contentDescription ="icon" ,
                    Modifier
                        .size(24.dp)
                        .padding(start = 8.dp, top = 8.dp)
                        .align(Alignment.CenterVertically), tint = colorResource(R.color.orange)
                )
                Text(modifier = Modifier
                    .padding(8.dp)
                    , text = "No hemos encontrado datos para mostrar, registra tu peso actual para poder mostar tu progreso."
                    , fontSize = 14.sp, color = Color.Black , fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }

        }

}

// Función para encontrar el punto más cercano al toque
fun findClosestPointIndex(tapX: Float, tapY: Float, dataPoints: List<Float>, canvasWidth: Float, canvasHeight: Float): Int? {
    val maxDataPoint = dataPoints.maxOrNull() ?: return null
    val minDataPoint = dataPoints.minOrNull() ?: return null

    val spacing = canvasWidth / (dataPoints.size - 1)

    dataPoints.forEachIndexed { index, dataPoint ->
        val x = index * spacing
        val y = canvasHeight * (1 - (dataPoint - minDataPoint) / (maxDataPoint - minDataPoint))

        // Si el toque está lo suficientemente cerca del punto
        if (tapX in (x - 30)..(x + 30) && tapY in (y - 30)..(y + 30)) {
            return index
        }
    }

    return null // Si no se encuentra un punto cercano
}

fun getScreenWidth(context: Context): Int {
    val displayMetrics = DisplayMetrics()
    val display = context.getSystemService(Context.WINDOW_SERVICE) as android.view.WindowManager
    display.defaultDisplay.getMetrics(displayMetrics)
    return displayMetrics.widthPixels
}


