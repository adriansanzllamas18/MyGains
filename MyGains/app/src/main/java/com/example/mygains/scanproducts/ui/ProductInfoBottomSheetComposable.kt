package com.example.mygains.scanproducts.ui

import android.content.ClipData.Item
import androidx.compose.animation.defaultDecayAnimationSpec
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ChipColors
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.rememberAsyncImagePainter
import coil.request.Tags
import com.example.mygains.R
import com.example.mygains.extras.utils.FormatterUtils
import com.example.mygains.scanproducts.data.ProductResponse


@Composable
fun ProductInfoBottomSheetComposable(product: ProductResponse){


//   var product= ProductResponse(barcode="", productName="Cacaolat 0%", brand="Cacaolat", imageUrl="https://images.openfoodfacts.org/images/products/841/270/072/0096/front_es.27.400.jpg", categories="category", calories=53.0, protein=3.0, carbohydrates=4.9, sugar=4.6, fatTotal=2.3, saturatedFat=1.5, fiber=3.0, salt=0.15, nutriScore="a", novaGroup="4", servingSize="", productQuantity="", allergens="en:milk", ingredients_tags= arrayListOf(
//       "en:may-contain-palm-oil","en:non-vegan","en:vegetarian-status-unknown"), ingredients_from_palm_oil_tags = mutableListOf(), ingredients_that_may_be_from_palm_oil_tags = mutableListOf())
    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .padding(8.dp)) {
        item {
            HeaderProduct(product)
        }

        item {
            BodyProduct(product)
        }

        item {
            if (!product.ingredients_tags.isNullOrEmpty()){
                FormatterUtils().getMoreNutriInfo(product.ingredients_tags?: mutableListOf()).forEach {
                    ProductTags(it)
                }
            }
        }

        item{
            NutriScoreAndNova(product)
        }

        item {
            Foot()
        }
    }
}

@Composable
fun Foot() {
    Text(text = "Los datos sobre los productos alimenticios provienen de Open Food Facts, una base de datos abierta y colaborativa de productos alimenticios de todo el mundo.",
        Modifier
            .padding(16.dp), color = Color.Gray,
        textAlign = TextAlign.Center,
        fontSize = 12.sp)
}

@Composable
fun ProductTags(text:String) {
    Card(
        onClick = { /* TODO */ },
        modifier = Modifier
            .padding(end = 16.dp, start = 16.dp, bottom = 8.dp, top = 16.dp)
            .clickable { /* TODO */ }, // Para emular el comportamiento clickeable de un Chip
        colors = CardDefaults.cardColors(
            containerColor = colorResource(id = R.color.orange_low) // Color del fondo
        ),
        shape = MaterialTheme.shapes.small, // Forma similar a un AssistChip
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp) // Ajusta la elevación si lo prefieres
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            // Icono
            Image(
                painter = painterResource(id = FormatterUtils().getTagIcon(text)),
                contentDescription = "",
                modifier = Modifier.size(50.dp)
            )

            Spacer(modifier = Modifier.width(8.dp)) // Espacio entre el icono y el texto

            // Texto
            Text(
                text = text,
                modifier = Modifier.fillMaxWidth(),
                fontWeight = FontWeight.Bold,
                color = Color.Black,
            )
        }
    }

}

@Composable
fun NutriScoreAndNova(productResponse: ProductResponse) {
    Card(Modifier.padding(16.dp),
        colors = CardColors(containerColor = colorResource(
        id = R.color.orange_low
    ), contentColor = Color.Black, disabledContentColor = Color.Transparent, disabledContainerColor = Color.Transparent)) {
        Row(Modifier.padding(16.dp)) {
            Image(painter = painterResource(id = FormatterUtils().getNutriScoreByType(productResponse.nutriScore.toString())), contentDescription = "",
                Modifier
                    .weight(0.33f)
                    .size(100.dp))
            Image(painter = painterResource(id = FormatterUtils().getNovaScoreByType(productResponse.novaGroup.toString())), contentDescription = "",
                Modifier
                    .weight(0.33f)
                    .size(100.dp))

            Image(painter = painterResource(id = FormatterUtils().getEcoScoreByType(productResponse.ecoScore.toString())), contentDescription = "",
                Modifier
                    .weight(0.33f)
                    .size(100.dp))
        }
    }
    }


@Composable
fun BodyProduct(productResponse: ProductResponse) {

    Card(
        Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 28.dp),
        colors = CardColors(containerColor = colorResource(
            id = R.color.orange_low
        ), contentColor = Color.Black, disabledContentColor = Color.Transparent, disabledContainerColor = Color.Transparent)) {

            ConstraintLayout(
                Modifier
                    .fillMaxSize()
                    .padding(top = 16.dp)) {
                val (card,proteinText,protein,carbs,carbsText,fat,fatText,sugar,sugarText,fibre,fibreText,salt,saltText,divider)= createRefs()

                Card(
                    Modifier
                        .constrainAs(card) {
                            start.linkTo(parent.start)
                            width = Dimension.percent(0.28f)
                        }
                        .padding(start = 8.dp), shape = RoundedCornerShape(100.dp), colors = CardColors(containerColor = colorResource(
                    id = R.color.white
                ), contentColor = Color.Black, disabledContentColor = Color.Transparent, disabledContainerColor = Color.Transparent)) {
                    Column(Modifier.padding(16.dp)) {
                        Text(text = FormatterUtils().formatNumber((productResponse.calories?:0.0).toString()),
                            Modifier
                                .align(Alignment.CenterHorizontally)
                                .fillMaxWidth(),
                             color = colorResource(id = R.color.orange), textAlign = TextAlign.Center, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        Text(text = "Kcl",
                            Modifier
                                .padding(8.dp)
                                .fillMaxWidth(), color = colorResource(id = R.color.orange), textAlign = TextAlign.Center, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    }
                }

                Text(text =  FormatterUtils().formatNumber((productResponse.protein?:0.0).toString())+" g",
                    Modifier.constrainAs(proteinText){
                        start.linkTo(card.end)
                        bottom.linkTo(card.bottom)
                        top.linkTo(parent.top)
                        width = Dimension.percent(0.22f)
                    }, textAlign = TextAlign.Center, fontSize = 20.sp, fontWeight = FontWeight.Bold)

                Text(text =  FormatterUtils().formatNumber((productResponse.carbohydrates?:0.0).toString())+" g",
                    Modifier.constrainAs(carbsText){
                        start.linkTo(proteinText.end)
                        bottom.linkTo(card.bottom)
                        top.linkTo(parent.top)
                        width = Dimension.percent(0.22f)
                    }, textAlign = TextAlign.Center, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text(text =  FormatterUtils().formatNumber((productResponse.fatTotal?:0.0).toString())+" g",
                    Modifier.constrainAs(fatText){
                        start.linkTo(carbsText.end)
                        width = Dimension.percent(0.28f)
                        bottom.linkTo(card.bottom)
                        top.linkTo(parent.top)
                    }, textAlign = TextAlign.Center, fontSize = 20.sp, fontWeight = FontWeight.Bold)


                Text(text = "Proteína",
                    Modifier
                        .constrainAs(protein) {
                            top.linkTo(proteinText.bottom)
                            start.linkTo(card.end)
                            
                        }
                        .padding(8.dp), textAlign = TextAlign.Center, fontSize = 14.sp, color =  Color.Gray)
                Text(text = "Carbs",
                    Modifier
                        .constrainAs(carbs) {
                            top.linkTo(carbsText.bottom)
                            start.linkTo(protein.end)
                            width = Dimension.percent(0.23f)
                        }
                        .padding(8.dp), textAlign = TextAlign.Center, fontSize = 14.sp, color =  Color.Gray)
                Text(text = "Grasas",
                    Modifier
                        .constrainAs(fat) {
                            top.linkTo(fatText.bottom)
                            start.linkTo(carbs.end)
                            width = Dimension.percent(0.23f)
                        }
                        .padding(8.dp), textAlign = TextAlign.Center, fontSize = 14.sp, color =  Color.Gray)

                HorizontalDivider(
                    Modifier
                        .constrainAs(divider) {
                            top.linkTo(card.bottom)
                        }
                        .padding(top = 16.dp, end = 16.dp, start = 16.dp), color = colorResource(id = R.color.orange))

                Text(text = "Azúcar",
                    Modifier
                        .constrainAs(sugar) {
                            start.linkTo(parent.start)
                            top.linkTo(divider.bottom)
                            width = Dimension.percent(0.5f)
                        }
                        .padding(start = 16.dp, top = 16.dp), color =  Color.Gray, textAlign = TextAlign.Start, fontSize = 18.sp)

                Text(text =  FormatterUtils().formatNumber((productResponse.sugar?:0.0).toString())+" g",
                    Modifier
                        .constrainAs(sugarText) {
                            end.linkTo(parent.end)
                            top.linkTo(divider.bottom)
                            width = Dimension.percent(0.5f)
                        }
                        .padding(end = 16.dp, top = 16.dp), fontWeight = FontWeight.Bold, textAlign = TextAlign.End, fontSize = 18.sp)

                Text(text = "Fibra",
                    Modifier
                        .constrainAs(fibre) {
                            start.linkTo(parent.start)
                            top.linkTo(sugar.bottom)
                            width = Dimension.percent(0.5f)
                        }
                        .padding(start = 16.dp, top = 16.dp), color =  Color.Gray, textAlign = TextAlign.Start, fontSize = 18.sp)

                Text(text = FormatterUtils().formatNumber((productResponse.fiber?:0.0).toString())+" g",
                    Modifier
                        .constrainAs(fibreText) {
                            end.linkTo(parent.end)
                            top.linkTo(sugarText.bottom)
                            width = Dimension.percent(0.5f)
                        }
                        .padding(end = 16.dp, top = 16.dp), fontWeight = FontWeight.Bold, textAlign = TextAlign.End, fontSize = 18.sp)

                Text(text = "Sal",
                    Modifier
                        .constrainAs(salt) {
                            start.linkTo(parent.start)
                            top.linkTo(fibre.bottom)
                            width = Dimension.percent(0.5f)
                        }
                        .padding(start = 16.dp, top = 16.dp), color =  Color.Gray, textAlign = TextAlign.Start, fontSize = 18.sp)

                Text(text =  FormatterUtils().formatNumber((productResponse.salt?:0.0).toString())+" g",
                    Modifier
                        .constrainAs(saltText) {
                            end.linkTo(parent.end)
                            top.linkTo(fibre.bottom)
                            width = Dimension.percent(0.5f)
                        }
                        .padding(end = 16.dp, top = 16.dp), fontWeight = FontWeight.Bold, textAlign = TextAlign.End, fontSize = 18.sp)
            }

        Text(text = "Información nutricional por cada 100g",
            Modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp), color = colorResource(id = R.color.orange),
            textAlign = TextAlign.Center)
    }

}

@Composable
fun HeaderProduct(productResponse: ProductResponse) {
    Column(Modifier.fillMaxWidth()) {

        Box(
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            // Card que actúa como sombra detrás de la imagen
            Card(
                modifier = Modifier
                    .height(50.dp)
                    .fillMaxWidth()
                    .padding(start = 30.dp, end = 30.dp)
                    .align(Alignment.Center)
                    .offset(y = 50.dp),
                shape = RoundedCornerShape(100.dp),
                colors = CardDefaults.cardColors(
                    containerColor = colorResource(id = R.color.orange_low),
                    contentColor = Color.Transparent
                ),
                elevation = CardDefaults.cardElevation(6.dp)// Pequeña elevación para dar más efecto de sombra
            ) {}

            // Imagen del producto encima de la Card
            Image(
                modifier = Modifier
                    .size(150.dp)  // Tamaño de la imagen más pequeño que la Card
                    .align(Alignment.Center),  // Centrar la imagen respecto a la Card
                contentScale = ContentScale.Fit,
                painter = rememberAsyncImagePainter(productResponse.imageUrl),
                contentDescription = "perfil"
            )
        }


        Text(text = productResponse.productName?:"",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(8.dp),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Text(text = productResponse.brand?:"",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(end = 8.dp, start = 8.dp),
            textAlign = TextAlign.Center
        )

    }
}
