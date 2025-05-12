package com.example.mygains.dashboard.ui.components

import android.graphics.drawable.shapes.Shape
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.fontResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.mygains.R
import com.example.mygains.dashboard.data.models.ShortCutsItem

@Preview(showBackground = true)
@Composable
fun NewHomeScreen() {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.gray_claro))
    )
    {

        val(box,body,header) = createRefs()
        val horizontalGuideline = createGuidelineFromTop(0.1f)

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(box) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .height(200.dp)
                .clip(
                    RoundedCornerShape(
                        topStart = 0.dp,
                        topEnd = 0.dp,
                        bottomStart = 30.dp,
                        bottomEnd = 30.dp
                    )
                )
                .background(
                    brush = Brush.verticalGradient(
                        colorStops = arrayOf(
                            0.4f to Color(0xFFFCE5D8),
                            1f to Color(0xFFCA5300)
                        )
                    )

                )
        )
        Box(
            modifier = Modifier.fillMaxWidth()
                .constrainAs(header){
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        ){
            HeaderHomeScreen()
        }

        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .constrainAs(body) {
                    top.linkTo(header.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .fillMaxWidth()
                .clip(
                    RoundedCornerShape(
                        30.dp
                    )
                )
                .background(Color.White)
        )
        {
            BodyHomeScreen()
        }

    }
}

@Composable
fun BodyHomeScreen() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)

    ){
        Column(
            Modifier
                .fillMaxWidth()
                .padding(8.dp)) {
            Text(
                text = "GYMRAT",
                fontSize = 24.sp,
                fontFamily = FontFamily(Font( R.font.montserratbold))
            )
            HorizontalDivider(modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 16.dp))
            ForTodaySection()
            HorizontalDivider(modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp))
            ShortCutsSection()
        }
    }
}

@Composable
fun ShortCutsSection() {

    var shortCutsList = mutableListOf(
        ShortCutsItem.ScannerShortcut(),
        ShortCutsItem.StatsShortcut(),
        ShortCutsItem.ParchuesShortcut(),
        ShortCutsItem.RatsShortcut()
    )
    
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    )
    {
        Text(
            text = "ACCIONES  RAPIDAS",
            fontSize = 18.sp,
            fontFamily = FontFamily(Font( R.font.montserratbold))
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
        )
        {
          items(shortCutsList){shortcut->
              Card(
                  modifier = Modifier
                      .padding(8.dp)
                      .height(120.dp),
                  elevation = CardDefaults.cardElevation(
                      defaultElevation = 4.dp,
                      pressedElevation = 8.dp
                  ),
                  colors = CardColors(containerColor = colorResource(id = shortcut.color), contentColor = Color.Black, disabledContentColor = Color.White, disabledContainerColor = Color.LightGray)
              )
              {
                  Column(
                      modifier = Modifier.fillMaxSize(),
                      horizontalAlignment = Alignment.CenterHorizontally,
                      verticalArrangement = Arrangement.Center
                  ) {

                      Icon(
                          tint = colorResource(id = shortcut.iconColor),
                          modifier = Modifier.size(40.dp),
                          painter = painterResource(id = shortcut.icon),
                          contentDescription = "scannnerIcon")

                      Text(
                          text = shortcut.title,
                          modifier = Modifier.padding(vertical = 8.dp),
                          fontFamily = FontFamily(Font( R.font.montserratbold))
                      )
                  }
              }
          }
        }
    }
}

@Composable
fun ForTodaySection() {
    Box(
        modifier = Modifier
            .fillMaxWidth()

            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(12.dp),
                clip = true
            )
            .background(colorResource(id = R.color.gray_claro))
    )
    {
        Column(
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "Entrenamiento de hoy",
                fontFamily = FontFamily(Font( R.font.montserratbold))

            )
            Text(
                text = "Pecho y hombros",
                fontFamily = FontFamily(Font( R.font.montserratregular))

            )
            //lista breve resumen volumen de entrenamiento rms destacados en esos grupos musculares
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                onClick = { /*TODO*/ },
                colors = ButtonColors(containerColor = Color.Black,
                    contentColor = Color.White,
                    disabledContentColor = Color.White,
                    disabledContainerColor = Color.LightGray)
            ) {
                Text(
                    text = "Ver rutina",
                    fontFamily = FontFamily(Font( R.font.montserratbold))
                )
            }
        }
    }
}

@Composable
fun HeaderHomeScreen() {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
    )
    {
        Column(Modifier.weight(1f)) {
            Text(
                text = "Hola de nuevo adrian!",
                fontSize = 18.sp,
                fontFamily = FontFamily(Font(R.font.montserratbold))
            )
            Text(
                modifier = Modifier.padding(vertical = 8.dp),
                text = "Un dia mas que cuenta , sigue asi!",
                fontFamily = FontFamily(Font(R.font.montserratregular))

            )
        }

        Icon(
            modifier = Modifier.size(24.dp),
            painter = painterResource(id = R.drawable.campana_config_icon),
            contentDescription ="notification"
        )

    }

}
