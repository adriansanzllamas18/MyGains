package com.example.mygains.login.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.example.mygains.R
import com.example.mygains.extras.navigationroutes.Routes

@Composable()
//@Preview(showBackground = true)
fun LoginScreen(loginViewModel: LoginViewModel, nav: NavHostController){
    ConstraintLayout(modifier = Modifier
        .fillMaxSize()
        .windowInsetsPadding(WindowInsets.systemBars)) {
        val (header,divider,emailPass,enter,alert) = createRefs()
        LoginTitle(modifier = Modifier
            .constrainAs(header) {
                top.linkTo(parent.top)
                end.linkTo(parent.end)
                start.linkTo(parent.start)
            }
            .padding(top = 200.dp))
        LoginDivider(modifier = Modifier
            .constrainAs(divider) {
                top.linkTo(header.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
            .padding(24.dp))

        LoginEmailPassword(Modifier.constrainAs(emailPass){
            top.linkTo(divider.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        },loginViewModel)

        LoginButtonEnter(Modifier.constrainAs(enter){
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            bottom.linkTo(parent.bottom)
        },loginViewModel, nav)


        val isAlert by loginViewModel.isAlertLive.observeAsState(initial = false)

        val error by loginViewModel.errorLive.observeAsState(initial = "")


        if (isAlert){
            AlertDialog(modifier =  Modifier.constrainAs(alert){
                top.linkTo(parent.top)
                end.linkTo(parent.end)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)

            },onDismissRequest = { loginViewModel.showAlert(false)},
                confirmButton = { Button(onClick = {
                    loginViewModel.showAlert(false)
                }
                ) {
                    Text(text = "Cerrar")
                }}
                , title = { Text(text = "Error")},
                text = { Box(){
                    Text(text = error, textAlign = TextAlign.Center)
                }},
                icon = { Icon(imageVector = Icons.Default.Info , contentDescription ="error",
                    tint = Color(LocalContext.current.getColor(R.color.orange)))
                }, containerColor =Color(0xFFFCE5D8))
        }



        val isLoading:Boolean  by loginViewModel.isLoadingLive.observeAsState(initial = false)
        Loader(isLoading = isLoading)

        // Observa el resultado de inicio de sesión
        val loginResult: Boolean by loginViewModel.loginResultLive.observeAsState(initial = false)
        //esto hace que una vez cambie solo se cree una vez
        LaunchedEffect(loginResult) {
            if (loginResult) {
                nav.navigate(Routes.Home.routes) {
                    // Asegúrate de que no se pueda volver a esta pantalla
                    popUpTo("login") { inclusive = true }
                }
            }
        }

    }
}

@Composable
fun LoginButtonEnter(modifier: Modifier, loginViewModel: LoginViewModel, nav: NavHostController) {

    val buttonEnable : Boolean  by loginViewModel.buttonLive.observeAsState(initial = false)
    val email:String  by loginViewModel.emailLive.observeAsState(initial = "")
    val pass:String  by loginViewModel.passLive.observeAsState(initial = "")



    Column(modifier = modifier){
        Text(text = "¿ Nuevo/a por aquí ? Empieza ahora.",
            Modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp)
                .clickable { nav.navigate(Routes.NewUser.routes) },
            color = Color(0xFFCA5300),
            textDecoration = TextDecoration.Underline,

        )
        Button(onClick = {
            loginViewModel.LogInWithEmailPassword(email,pass)
        }, modifier= Modifier
            .padding(start = 24.dp, end = 24.dp, bottom = 24.dp)
            .fillMaxWidth()
            ,enabled = buttonEnable,
            colors = ButtonDefaults.buttonColors(containerColor = Color(color = 0xFFCA5300 ))) {
            Text(text = "Comenzar !")
        }

    }


}

@Composable
fun LoginEmailPassword(modifier: Modifier , loginViewModel: LoginViewModel) {

    val email:String  by loginViewModel.emailLive.observeAsState(initial = "")
    val pass:String  by loginViewModel.passLive.observeAsState(initial = "")


    // var email = rememberSaveable{ mutableStateOf("") }
    //var pass = rememberSaveable{ mutableStateOf("") }

    Column(modifier) {
        MyEmailText(email){loginViewModel.onLoginChanged(email= it, pass = pass)}
        MyPassText(pass) { loginViewModel.onLoginChanged(email= email, pass = it)}
        MyTitleForgot(Modifier.align(Alignment.End))
    }
}

@Composable
fun MyTitleForgot(modifier: Modifier) {
    Text(text = "¿ Has olvidado tu contraseña ?", modifier.padding(end = 24.dp),
        color = Color(color = 0xFFCA5300 ))
}

@Composable
fun MyPassText(pass: String, onTextChange: (String) -> Unit) {
    TextField(value = pass, onValueChange = {onTextChange(it)}, modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(38.dp))
        .padding(24.dp),
        placeholder = { Text(text = "Contraseña")},
        maxLines = 1,
        singleLine = true,
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions( keyboardType = KeyboardType.Password),
        colors = TextFieldDefaults.colors(focusedContainerColor = Color(0xFFFCE5D8),   // Color de fondo cuando está enfocado
            unfocusedContainerColor = Color(0xFFFCE5D8), // Color de fondo cuando no está enfocado
            focusedIndicatorColor = Color.Transparent,   // Eliminar la línea inferior cuando está enfocado
            unfocusedIndicatorColor = Color.Transparent ),
        shape = RoundedCornerShape(38.dp)
        )
}

@Composable
fun MyEmailText(email: String, onTextChange: (String) -> Unit) {
    TextField(value = email, onValueChange = { onTextChange(it)},
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        maxLines = 1,
        singleLine = true,
        keyboardOptions = KeyboardOptions( keyboardType = KeyboardType.Email),
        placeholder = {Text(text = "Correo")},
        colors = TextFieldDefaults.colors(focusedContainerColor = Color(0xFFFCE5D8),   // Color de fondo cuando está enfocado
            unfocusedContainerColor = Color(0xFFFCE5D8), // Color de fondo cuando no está enfocado
            focusedIndicatorColor = Color.Transparent,   // Eliminar la línea inferior cuando está enfocado
            unfocusedIndicatorColor = Color.Transparent ),
        shape = RoundedCornerShape(38.dp)
    )

}

@Composable
fun LoginDivider(modifier: Modifier) {
    Box(modifier = modifier){
        HorizontalDivider(modifier = Modifier
            .height(8.dp),
            color =Color(0xFFFCE5D8))
        }
    }


@Composable
fun LoginTitle(modifier: Modifier) {
    Box(modifier = modifier ){
        Text(text = "MyGains", fontSize = 34.sp, fontFamily = FontFamily(Font(R.font.poppins)), color = Color(0xFFCA5300))
    }
}

@Composable
fun Loader(isLoading:Boolean){
    ConstraintLayout(modifier = Modifier
        .fillMaxSize()){
        val loader= createRef()
        if (isLoading){
            Box(modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) { // Intercepta los clics
                    // No hacemos nada aquí, solo previene los clics
                }
                .background(if (isLoading) Color.Gray.copy(alpha = 0.6f) else Color.Transparent))

            CircularProgressIndicator(modifier = Modifier.constrainAs(loader){
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            })
        }
    }
}






