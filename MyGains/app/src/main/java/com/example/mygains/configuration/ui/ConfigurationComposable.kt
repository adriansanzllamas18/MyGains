package com.example.mygains.configuration.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.mygains.R
import com.example.mygains.base.response.BaseResponse
import com.example.mygains.base.response.BaseResponseUi
import com.example.mygains.configuration.data.model.ConfigurationItemModel
import com.example.mygains.extras.globalcomponents.CustomAlertDialog
import com.example.mygains.extras.globalcomponents.ItemListComponent
import com.example.mygains.extras.navigationroutes.Routes
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun ConfigurationComposable(nav: NavHostController) {

    
    
    var viewModel:ConfigurationViewModel = hiltViewModel()
    val coroutineScope = rememberCoroutineScope()
    val resultAlert by viewModel.logOutResultLife.observeAsState(null)


    var list = mutableListOf(
        ConfigurationItemModel(leftIcon = R.drawable.usuario_config_icon, text = "Cuenta y Perfil", action = {}),
        ConfigurationItemModel(R.drawable.cerrar_config_icon,"Privacidad y Seguridad",{}),
        ConfigurationItemModel(R.drawable.campana_config_icon,"Notificaciones",{}),
        ConfigurationItemModel(R.drawable.modo_oscuro_on_config_icon,"Apariencia",{}),
        ConfigurationItemModel(R.drawable.help_support_config_icon,"Ayuda y Soporte",{}),
        ConfigurationItemModel(R.drawable.informacion_sobre_terminos_y_condiciones_config_icon,"Terminos y Condiciones",{})
    )

    val context = LocalContext.current

    val packageInfo = remember {
        context.packageManager.getPackageInfo(context.packageName, 0)
    }
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .verticalScroll(rememberScrollState())
    )
    {
        list.forEach { item ->
            Box(modifier = Modifier.padding(8.dp)) {
                ItemListComponent(configModel = item)
            }
        }

        val state = rememberSwipeToDismissBoxState(
            positionalThreshold = { fullSize -> fullSize * 0.3f },
            confirmValueChange = { value ->
                if (value != SwipeToDismissBoxValue.Settled) {
                    coroutineScope.launch {
                        delay(300) // Esperar a que termine la animación
                        viewModel.logOut()
                    }
                    true
                } else {
                    false
                }
            }
        )

        SwipeToDismissBox(
            modifier =  Modifier.padding(8.dp),
            state = state,
            enableDismissFromStartToEnd = false,
            backgroundContent = {
                val direction = state.dismissDirection

                // Solo aplicamos cuando se desliza de derecha a izquierda
                if (direction == SwipeToDismissBoxValue.EndToStart) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                shape = CircleShape,
                                brush = Brush.horizontalGradient(
                                    colors = listOf(
                                        Color(0xFFFF5252),  // Rojo medio
                                        Color(0xFFB71C1C)   // Rojo oscuro
                                    )
                                )
                            ),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        Row(
                            modifier = Modifier.padding(end = 16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Cerrar Sesión",
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            },
            content = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    colorResource(id = R.color.orange),
                                    colorResource(id = R.color.orange_low),
                                ),
                                start = Offset(1000f, -100f),
                                end = Offset(-100f, 100f),
                                tileMode = TileMode.Clamp
                            ), shape = CircleShape
                        ) // Fondo de la tarjeta principal
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(
                        modifier = Modifier.padding(8.dp),
                        text = "Conectado"
                    )
                    Image(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(id = R.drawable.cerarsesion),
                        contentDescription = "logout"
                    )
                }
            }
        )

        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center

        )
        {
            Text(text = "MyGains " + "v" + packageInfo.versionName.toString())
        }
    }

    if (resultAlert != null){

        when(resultAlert!!.response){
            is BaseResponse.Success->{
                nav.navigate(Routes.Login.routes){
                    //  elimina pantallas de la pila que estén por encima y la destino
                    nav.graph.startDestinationRoute?.let { rout ->
                        popUpTo(Routes.Home.routes) {
                            inclusive = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            }

            else ->{
                AnimatedVisibility (resultAlert!!.show){
                    CustomAlertDialog(
                        responseUi = resultAlert!!,
                        actionDismiss = {
                           // createRoutineViewModel.upDateDialog(false)
                        },
                        actionConfirm = {
                           // createRoutineViewModel.upDateDialog(false)
                        })
                }
            }
        }
    }
}