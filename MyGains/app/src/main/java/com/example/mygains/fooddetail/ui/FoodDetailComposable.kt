package com.example.mygains.fooddetail.ui

import android.widget.GridLayout
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.mygains.R
import com.example.mygains.extras.globalcomponents.loadercomponent.LoaderComponent
import com.example.mygains.extras.utils.FormatterUtils
import com.example.mygains.fooddetail.data.FoodDetailModel
import com.example.mygains.fooddetail.data.NutrimentsModel
import com.example.mygains.fooddetail.ui.states.FoodDetailUIState

@Composable
fun FoodDetailComposable (navController: NavController, codebar: String){


    val viewmodel:FoodDetailViewModel = hiltViewModel()

    val uiState by  viewmodel.stateui.collectAsState()

    val currentCodebar  by remember {
        mutableStateOf(codebar)
    }

    LaunchedEffect(Unit) {
        viewmodel.getProductByBarCode(currentCodebar)
    }

    Box(
        modifier = Modifier.fillMaxSize()
    )
    {
        when(val  state = uiState){
            is FoodDetailUIState.Loading->{
                LoaderComponent()
            }
            is FoodDetailUIState.Succes->{
                DetailSection(state.openFoodModel.product?: FoodDetailModel())
            }
            is FoodDetailUIState.Error->{
                ShowAnimationNotFound(state.error)
            }
        }
    }
}

@Composable
fun DetailSection(foodDetailModel: FoodDetailModel) {

    Column (
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16f / 9f),
            model = foodDetailModel.imageUrl,
            error = painterResource(id = R.drawable.pan),
            contentDescription = "food image" ,
            placeholder = painterResource(id = R.drawable.pan),
            contentScale = ContentScale.Crop
        )

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        )
        {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                text = foodDetailModel.productName?:"",
                fontSize = 14.sp,
                textAlign = TextAlign.Start,
                fontFamily = FontFamily(Font( R.font.montserratregular)
                )
            )
            Text(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .fillMaxWidth()
                    .weight(1f),
                text ="#${foodDetailModel.code}",
                fontSize = 14.sp,
                textAlign = TextAlign.End,
                fontFamily = FontFamily(Font( R.font.montserratbold)
                )
            )
        }

        Column {
            Text(
                modifier = Modifier.padding(horizontal = 8.dp),
                text = "Cantidad a servir",
                fontSize = 24.sp,
                fontFamily = FontFamily(Font( R.font.montserratbold)
                )
            )

            TextField(value = "100", onValueChange = {}, modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(38.dp))
                .padding(24.dp),
                placeholder = { Text(text = "Contraseña")},
                maxLines = 1,
                singleLine = true,
                keyboardOptions = KeyboardOptions( keyboardType = KeyboardType.Password),
                colors = TextFieldDefaults.colors(focusedContainerColor = Color(0xFFFCE5D8),   // Color de fondo cuando está enfocado
                    unfocusedContainerColor = Color(0xFFFCE5D8), // Color de fondo cuando no está enfocado
                    focusedIndicatorColor = Color.Transparent,   // Eliminar la línea inferior cuando está enfocado
                    unfocusedIndicatorColor = Color.Transparent ),
                shape = RoundedCornerShape(38.dp)
            )
            HorizontalDivider(modifier = Modifier.padding(8.dp))
            NutrimentsSection(foodDetailModel.nutriments?: NutrimentsModel())
            HorizontalDivider(modifier = Modifier.padding(8.dp))
            AdvancedNutrimentsSection(foodDetailModel.nutriments?: NutrimentsModel())
            ScoresSection(foodDetailModel)
        }
    }
}

@Composable
fun AdvancedNutrimentsSection(nutrimentsModel: NutrimentsModel){
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    )
    {
        Text(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 16.dp),
            text = "Datos Nutricionales Avanzados",
            fontSize = 20.sp,
            fontFamily = FontFamily(Font( R.font.montserratbold)
            )
        )

        BoxItemDataDetail("Grasas Saturadas",
            (nutrimentsModel.saturatedFat100g?:"Sin datos").toString()
        )
        BoxItemDataDetail("Azúcares",(nutrimentsModel.sugars100g?:"Sin datos").toString())
        BoxItemDataDetail("Sal",(nutrimentsModel.salt100g?:"Sin datos").toString())
        BoxItemDataDetail("Sodio", (nutrimentsModel.sodium100g?:"Sin datos").toString())
        BoxItemDataDetail("Fibra", (nutrimentsModel.fiber100g?:"Sin datos").toString())
    }
}

@Composable
fun ScoresSection(product: FoodDetailModel) {
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    )
    {
        Card(Modifier.padding(16.dp),
            colors = CardColors(containerColor = colorResource(
                id = R.color.orange_low
            ), contentColor = Color.Black, disabledContentColor = Color.Transparent, disabledContainerColor = Color.Transparent)
        ) {
            Row(Modifier.padding(16.dp)) {
                Image(painter = painterResource(id = FormatterUtils().getNutriScoreByType(product.nutritionGrades.toString())), contentDescription = "",
                    Modifier
                        .weight(0.33f)
                        .size(100.dp))
                Image(painter = painterResource(id = FormatterUtils().getNovaScoreByType(product.novaScore.toString())), contentDescription = "",
                    Modifier
                        .weight(0.33f)
                        .size(100.dp))

                Image(painter = painterResource(id = FormatterUtils().getEcoScoreByType(product.ecoscore.toString())), contentDescription = "",
                    Modifier
                        .weight(0.33f)
                        .size(100.dp))
            }
        }
    }
}

@Composable
fun NutrimentsSection(nutrimentsModel: NutrimentsModel) {
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    )
    {
        Text(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 16.dp),
            text = "Macronutrientes",
            fontSize = 20.sp,
            fontFamily = FontFamily(Font( R.font.montserratbold)
            )
        )

        BoxItemDataDetail("Calorias", (nutrimentsModel.energyKcal100g?:"Sin datos").toString(),"2000")
        BoxItemDataDetail("Proteina", (nutrimentsModel.proteins100g?:"Sin datos").toString(),"30")
        BoxItemDataDetail("Carbohidratos", (nutrimentsModel.carbohydrates100g?:"Sin datos").toString(),"56")
        BoxItemDataDetail("Grasas", (nutrimentsModel.fat100g?:"Sin datos").toString(),"34")
    }
}

@Composable
fun BoxItemWithIcon(title:String,value:String, icon:Int){
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    )
    {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            text = value,
            textAlign = TextAlign.End,
            fontSize = 14.sp,
            fontFamily = FontFamily(Font( R.font.montserratbold)
            )
        )

        Icon(painter = painterResource(id = R.drawable.nutriscore_e), contentDescription = "icon image")
    }
}


@Composable
fun BoxItemDataDetail(title:String,value:String,currentValue:String=""){
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    )
    {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        )
        {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = title,
                fontSize = 14.sp,
                fontFamily = FontFamily(Font( R.font.montserratregular)
                )
            )

            if (currentValue.isNotEmpty()){
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = currentValue,
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font( R.font.montserratregular)
                    )
                )
            }
        }
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            text = value,
            textAlign = TextAlign.End,
            fontSize = 14.sp,
            fontFamily = FontFamily(Font( R.font.montserratbold)
            )
        )
    }
}

@Composable
private fun ShowAnimationNotFound(textError: String) {
    // Carga la composición del recurso Lottie
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.nofoundfoondanimation))

    var isPlaying by remember {
        mutableStateOf(true)
    }
    // Animación con progreso controlado
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever,
        isPlaying = isPlaying // Añadido el control de reproducción
    )

    Column( Modifier
        .fillMaxSize()
        .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    )
    {
        LottieAnimation(
            composition = composition,
            progress = progress,
            modifier = Modifier
                .size(400.dp)
                .weight(1f)
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            text = textError,
            textAlign = TextAlign.Center,
            fontSize = 14.sp,
            fontFamily = FontFamily(Font( R.font.montserratbold)
            )
        )
    }
}

