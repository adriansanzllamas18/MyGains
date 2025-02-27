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
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
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
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
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
                modifier = Modifier.padding(top = 24.dp, bottom = 24.dp).fillMaxWidth(),
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

            var pointTextSelectedText by remember {
                mutableStateOf("")
            }

            // Estado para almacenar el índice del punto seleccionado
            var selectedPointIndex by remember { mutableStateOf<Int?>(null) }

            Card(
                colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                shape = RoundedCornerShape(28.dp)
            ) {
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                    shape = RoundedCornerShape(28.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        // Título
                        Text(
                            color = Color.Black,
                            text = "Gráfico de Peso",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .padding(bottom = 16.dp) // Espaciado entre el título y el gráfico
                        )

                        // Mostrar el texto del punto seleccionado si no está vacío
                        if (pointTextSelectedText.isNotEmpty()) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 16.dp), // Espaciado entre el texto del punto y el gráfico
                                horizontalArrangement = Arrangement.End // Alinear a la derecha
                            ) {
                                Text(
                                    color = colorResource(id = R.color.orange),
                                    text = "$pointTextSelectedText kg",
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        // Puntos de datos para el gráfico
                        val dataPoints = mutableListOf<Float>(0f) // Añadir 0 como primer punto
                        listweights.forEach { dataPoints.add(it.weight.toFloat()) }

                        // Colores y tamaños
                        val lineColor = colorResource(id = R.color.orange)
                        val circleColor = colorResource(id = R.color.black)
                        val strokeWidth = 5f

                        // Animación para el tamaño de los puntos
                        val infiniteTransition = rememberInfiniteTransition()
                        val animatedRadii = dataPoints.mapIndexed { index, _ ->
                            infiniteTransition.animateFloat(
                                initialValue = 6f, // Tamaño inicial del círculo
                                targetValue = 14f, // Tamaño máximo del círculo
                                animationSpec = infiniteRepeatable(
                                    animation = tween(1200, easing = FastOutSlowInEasing),
                                    repeatMode = RepeatMode.Reverse
                                ), label = ""
                            )
                        }

                        // Crear un estado de scroll
                        val scrollState = rememberScrollState()

                        // Usar LaunchedEffect para desplazar al final del gráfico cuando se cargue
                        LaunchedEffect(key1 = dataPoints.size) {
                            scrollState.animateScrollTo(scrollState.maxValue)
                            // Inicialmente mostrar el último valor
                            pointTextSelectedText = dataPoints.last().toString()
                            selectedPointIndex = dataPoints.size - 1 // Seleccionar el último punto por defecto
                        }

                        // Usamos un Box para contener el gráfico y permitir el scroll horizontal
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()  // El Box debe ocupar el ancho completo
                                .height(300.dp)  // Altura del gráfico
                                .horizontalScroll(scrollState) // Permitir desplazamiento horizontal
                        ) {
                            Canvas(
                                modifier = Modifier
                                    .width((listweights.size*100).dp)
                                    .height(300.dp)  // Altura fija
                                    .pointerInput(Unit) {
                                        detectTapGestures { offset ->
                                            // Detectar cuál punto está más cerca de la posición tocada
                                            val closestIndex = findClosestPointIndex(
                                                offset.x,
                                                offset.y,
                                                dataPoints,
                                                size.width.toFloat(),
                                                size.height.toFloat()
                                            )
                                            selectedPointIndex = closestIndex
                                            // Actualizar el texto del punto seleccionado
                                            pointTextSelectedText = dataPoints[selectedPointIndex!!].toString()
                                        }
                                    }
                            ) {
                                // Obtener el tamaño del canvas
                                val canvasWidth = size.width
                                val canvasHeight = size.height

                                // Dibujar un fondo degradado
                                val brush = Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Transparent,
                                        Color(0xFFFFFCE5D8),
                                        Color.Transparent
                                    ),
                                    startY = 0f,
                                    endY = canvasHeight
                                )
                                drawRect(brush = brush)

                                // Normalizamos los datos para ajustarlos al Canvas
                                val maxDataPoint = dataPoints.maxOrNull() ?: 1f
                                val minDataPoint = dataPoints.minOrNull() ?: 0f

                                // Cálculo de la separación horizontal - IMPORTANTE: Ahora usamos el ancho completo
                                val xStart = 0f  // Iniciamos desde el borde izquierdo
                                val xEnd = canvasWidth  // Terminamos en el borde derecho

                                // Función para calcular la posición X de cada punto basado en su índice
                                val calculateX = { index: Int ->
                                    if (dataPoints.size <= 1) {
                                        canvasWidth / 2f // Si solo hay un punto, lo centramos
                                    } else {
                                        xStart + (xEnd - xStart) * (index.toFloat() / (dataPoints.size - 1))
                                    }
                                }

                                // Crear un camino (Path) para la línea curva
                                val path = Path()

                                // Movemos al primer punto
                                val firstX = calculateX(0)
                                val firstY = canvasHeight * (1 - (dataPoints[0] - minDataPoint) / (maxDataPoint - minDataPoint))
                                path.moveTo(firstX, firstY)

                                // Dibujar líneas curvas entre los puntos usando curvas Bézier
                                for (i in 0 until dataPoints.size - 1) {
                                    val x1 = calculateX(i)
                                    val y1 = canvasHeight * (1 - (dataPoints[i] - minDataPoint) / (maxDataPoint - minDataPoint))
                                    val x2 = calculateX(i + 1)
                                    val y2 = canvasHeight * (1 - (dataPoints[i + 1] - minDataPoint) / (maxDataPoint - minDataPoint))

                                    val controlPointX1 = (x1 + x2) / 2
                                    val controlPointY1 = y1
                                    val controlPointX2 = (x1 + x2) / 2
                                    val controlPointY2 = y2

                                    path.cubicTo(
                                        controlPointX1,
                                        controlPointY1,
                                        controlPointX2,
                                        controlPointY2,
                                        x2,
                                        y2
                                    )
                                }

                                // Dibujamos la línea curva
                                drawPath(
                                    path = path,
                                    color = lineColor,
                                    style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                                )

                                // Dibujamos los círculos en los puntos
                                dataPoints.forEachIndexed { index, dataPoint ->
                                    val x = calculateX(index)
                                    val y = canvasHeight * (1 - (dataPoint - minDataPoint) / (maxDataPoint - minDataPoint))

                                    // Dibuja el círculo
                                    drawCircle(
                                        color = if (selectedPointIndex == index || index == dataPoints.size - 1) Color(color = 0xFFCA5300) else circleColor,
                                        radius = if (selectedPointIndex == index) 8.dp.toPx() else animatedRadii[index].value,
                                        center = Offset(x, y)
                                    )
                                }
                            }
                        }
                    }
                }

            }
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


