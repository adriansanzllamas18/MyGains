package com.example.mygains.login.ui

import android.content.Intent
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.runtime.currentCompositionLocalContext
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.mygains.R
import com.example.mygains.extras.navigationroutes.Routes
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import org.checkerframework.common.returnsreceiver.qual.This

@Composable()
//@Preview(showBackground = true)
fun LoginScreen( nav: NavHostController){
    val loginViewModel: LoginViewModel = hiltViewModel()

    var googleAccount: GoogleSignInAccount? by remember { mutableStateOf(null) }

    val isAlert by loginViewModel.isAlertLive.observeAsState(initial = false)

    val error by loginViewModel.errorLive.observeAsState(initial = "")

    val isLoading:Boolean  by loginViewModel.isLoadingLive.observeAsState(initial = false)


    // Resultado del inicio de sesión con Google

    val googleSignInLauncher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == android.app.Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            val account = task.result
            if (account != null) {
                googleAccount = account
                loginViewModel.loginWithGoogle(account) // Usamos la cuenta para autenticarnos
            } else {
                loginViewModel.showAlert(true)
            }
        }
    }

        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .windowInsetsPadding(WindowInsets.systemBars),
            state = rememberLazyListState(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        )  {

            item {
                LoginTitle()
            }


            item {
                LoginDivider()
            }


            item {
                LoginEmailPassword(loginViewModel)
            }


            item {
                LoginButtonEnter(loginViewModel, nav)
            }

            item {
                GoogleSignInButton (googleSignInLauncher)
            }

            item {
                 if (isAlert){
                     AlertDialog(modifier = Modifier.fillMaxWidth(),onDismissRequest = { loginViewModel.showAlert(false)},
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
             }

        }

    Loader(isLoading = isLoading)

    // Observa el resultado de inicio de sesión
    val loginResult: Boolean by loginViewModel.loginResultLive.observeAsState(initial = false)
    //esto hace que una vez cambie solo se cree una vez
    LaunchedEffect(loginResult) {
        if (loginResult) {
            nav.navigate(Routes.Home.routes){
                    popUpTo(Routes.Login.routes) { inclusive = true }
                }
            }
    }
}

@Composable
fun LoginButtonEnter( loginViewModel: LoginViewModel, nav: NavHostController) {

    val buttonEnable : Boolean  by loginViewModel.buttonLive.observeAsState(initial = false)
    val email:String  by loginViewModel.emailLive.observeAsState(initial = "")
    val pass:String  by loginViewModel.passLive.observeAsState(initial = "")



    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 32.dp)){
        Text(text = "¿ Nuevo/a por aquí ? Empieza ahora.",
            Modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp)
                .clickable {
                    nav.navigate(Routes.NewUser.routes){

                        popUpTo(Routes.Login.routes) { // Mantiene Login en la pila  ya que el splash lo hemos borrado desde su screen
                            inclusive = false // No borra Login, para que su estado se mantenga
                            saveState = true // Guarda su estado para restaurarlo al volver
                        }
                        launchSingleTop = true // Evita duplicar NewUser
                        restoreState = true // Restaura el estado si ya se visitó antes
                    }
                           },
            color = Color(0xFFCA5300),
            textDecoration = TextDecoration.Underline,

        )
        Button(onClick = {
            loginViewModel.LogInWithEmailPassword(email,pass)
        }, modifier= Modifier
            .padding(start = 24.dp, end = 24.dp, bottom = 8.dp)
            .fillMaxWidth()
            ,enabled = buttonEnable,
            colors = ButtonDefaults.buttonColors(containerColor = Color(color = 0xFFCA5300 ))) {
            Text(text = "Comenzar !")
        }

    }


}

@Composable
fun LoginEmailPassword( loginViewModel: LoginViewModel) {

    val email:String  by loginViewModel.emailLive.observeAsState(initial = "")
    val pass:String  by loginViewModel.passLive.observeAsState(initial = "")


    // var email = rememberSaveable{ mutableStateOf("") }
    //var pass = rememberSaveable{ mutableStateOf("") }

        Column(Modifier.fillMaxWidth()) {
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
fun LoginDivider() {
    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(24.dp)){
        HorizontalDivider(modifier = Modifier
            .height(8.dp),
            color =Color(0xFFFCE5D8))
        }
    }


@Composable
fun LoginTitle() {
    Box(modifier = Modifier
        .fillMaxWidth(), contentAlignment = Alignment.Center){
        Text(text = "MyGains",
            fontSize = 34.sp,
            fontFamily = FontFamily(Font(R.font.poppins)),
            color = Color(0xFFCA5300)
        )
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


@Composable
fun GoogleSignInButton(googleAccount: ManagedActivityResultLauncher<Intent, ActivityResult>) {

    val context= LocalContext.current

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp),
            onClick = {
                val signInIntent = GoogleSignIn.getClient(
                    context,
                    GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken("730789139932-b7p0pv037h8si8afh7vchkoj9anknah1.apps.googleusercontent.com") // Web Client ID
                        .requestEmail()
                        .build()
                ).signInIntent

                googleAccount.launch(signInIntent)

            }) {
            Row() {
                Image(
                    painter = painterResource(id = R.drawable.google),
                    contentDescription ="google icon",
                    modifier = Modifier.size(24.dp)
                )
                Text("Comenzar con  Google", modifier = Modifier.padding(start = 16.dp))
            }

        }

}







