package com.example.mygains.newuser.ui


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.example.mygains.R
import com.example.mygains.extras.navigationroutes.Routes
import com.example.mygains.login.ui.Loader
import com.example.mygains.userinfo.data.UserData

@Composable
fun NewUserComposable(newUserViewModel: NewUserViewModel, navHostController: NavHostController, onSignInClick : () -> Unit ) {

    val result:String  by newUserViewModel.resultLive.observeAsState(initial = "")

    ConstraintLayout(
        Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.systemBars)) {
        val (header, body, footer, snack, googleSignIn, alert) = createRefs()
        HeadScreen(
            Modifier
                .constrainAs(header) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
                .padding(16.dp), navHostController)

        BodyInfo(
            Modifier
                .constrainAs(body) {
                    top.linkTo(header.bottom)
                    start.linkTo(parent.start)
                }
                .padding(16.dp), newUserViewModel)

        // Botón para iniciar sesión con Google
        SignInScreen(onSignInClick = onSignInClick, modifier = Modifier.constrainAs(googleSignIn) {
            top.linkTo(body.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            bottom.linkTo(footer.top, margin = 16.dp)
        })


        Footer(
            Modifier
                .constrainAs(footer) {
                    bottom.linkTo(parent.bottom)
                }, newUserViewModel
        )

        val isLoading: Boolean by newUserViewModel.isLoadingLive.observeAsState(initial = false)
        Loader(isLoading = isLoading)



        val isAlert by newUserViewModel.isAlertLive.observeAsState(initial = false)

        if (isAlert){
            AlertDialog(modifier =  Modifier.constrainAs(alert){
                top.linkTo(parent.top)
                end.linkTo(parent.end)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)

            },onDismissRequest = { newUserViewModel.showAlert(false)},
                confirmButton = { Button(onClick = {
                newUserViewModel.showAlert(false)
            }
                ) {
                    Text(text = "Cerrar")
            }}
                , title = { Text(text = "Error")},
                text = { Text(text = result)},
                icon = { Icon(imageVector = Icons.Default.Info , contentDescription ="error",
                 tint = Color(LocalContext.current.getColor(R.color.orange)))}, containerColor =Color(0xFFFCE5D8))
        }

        val loginResult by newUserViewModel.loginResultLive.observeAsState(initial = null)


        LaunchedEffect(loginResult) {
            if (loginResult == true) {
                navHostController.navigate(Routes.Home.routes) {
                    // Asegúrate de que no se pueda volver a esta pantalla
                    popUpTo(Routes.Login.routes) { inclusive = true }
                }
            }else if(loginResult == false){
                navHostController.navigate(Routes.Login.routes) {
                    // Asegúrate de que no se pueda volver a esta pantalla
                    popUpTo(Routes.NewUser.routes) { inclusive = true }
                }
            }
        }
    }



}

@Composable
fun Footer(modifier: Modifier, newUserViewModel: NewUserViewModel) {

    val buttonEnable : Boolean  by newUserViewModel.buttonLive.observeAsState(initial = false)

    LoginButtonCreate(modifier,buttonEnable, newUserViewModel)
}

@Composable
fun BodyInfo(modifier: Modifier, newUserViewModel: NewUserViewModel) {

    val user: UserData by newUserViewModel.userLive.observeAsState(initial = UserData())
    val isTextInfoVisible : Boolean  by newUserViewModel.textInfoLive.observeAsState(initial = false)


    ConstraintLayout(modifier) {
        val (card,textinfo, icon) = createRefs()
        Card(Modifier.constrainAs(card){},elevation = CardDefaults.cardElevation(defaultElevation = 8.dp), colors = CardDefaults.cardColors(containerColor = Color(color =  0xFFFCE5D8))) {
            Column(modifier) {
                Text(text = "Crea tu cuenta", fontSize = 28.sp, fontStyle = FontStyle(R.font.poppinsbold), fontWeight = FontWeight.Bold, color = Color.Black)
                NameText(user.name.toString()){newUserViewModel.onParamsChanged(user.copy(name = it))}
                FirstNameText(user.first_name.toString()){newUserViewModel.onParamsChanged(user.copy(first_name = it))}
                SecondNameText(user.second_name.toString()){newUserViewModel.onParamsChanged(user.copy(second_name = it))}
                EmailText(user.email.toString()){newUserViewModel.onParamsChanged(user.copy(email = it))}
                PassText(user.pass.toString()) {newUserViewModel.onParamsChanged(user.copy(pass = it))}
            }
        }



        if (!isTextInfoVisible){

            Text(text = "Asegurate de que introduces todos los campos de manera correcta, la contraseña debe tenaer almenos 6 caracteres.",
                Modifier
                    .constrainAs(textinfo) {
                        top.linkTo(card.bottom)
                        end.linkTo(parent.end)
                        start.linkTo(icon.end)
                    }
                    .padding(start = 16.dp, top = 8.dp, end = 8.dp), fontSize = 14.sp)

            Icon(imageVector = Icons.Filled.Info, contentDescription = "info",
                Modifier
                    .constrainAs(icon) {
                        top.linkTo(card.bottom)
                        start.linkTo(card.start)
                    }
                    .padding(8.dp))
        }

    }


}


@Composable
fun HeadScreen(modifier: Modifier, navHostController: NavHostController) {
    Row(modifier) {
        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription ="atras",
            modifier= Modifier.clickable { navHostController.popBackStack() })
    }
}


@Composable
fun NameText(name: String, onTextChange: (String) -> Unit) {
    TextField(value = name, onValueChange = {onTextChange(it)}, modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(38.dp))
        .padding(8.dp),
        placeholder = { Text(text = "Nombre") },
        maxLines = 1,
        singleLine = true,
        keyboardOptions = KeyboardOptions( keyboardType = KeyboardType.Password),
        colors = TextFieldDefaults.colors(focusedContainerColor = Color.White,   // Color de fondo cuando está enfocado
            unfocusedContainerColor = Color.White, // Color de fondo cuando no está enfocado
            focusedIndicatorColor = Color.Transparent,   // Eliminar la línea inferior cuando está enfocado
            unfocusedIndicatorColor = Color.Transparent ),
        shape = RoundedCornerShape(38.dp)
    )
}

@Composable
fun FirstNameText(firstName: String, onTextChange: (String) -> Unit) {
    TextField(value = firstName, onValueChange = { onTextChange(it)},
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        maxLines = 1,
        singleLine = true,
        placeholder = { Text(text = "Apellido") },
        colors = TextFieldDefaults.colors(focusedContainerColor = Color.White,   // Color de fondo cuando está enfocado
            unfocusedContainerColor = Color.White, // Color de fondo cuando no está enfocado
            focusedIndicatorColor = Color.Transparent,   // Eliminar la línea inferior cuando está enfocado
            unfocusedIndicatorColor = Color.Transparent ),
        shape = RoundedCornerShape(38.dp)
    )

}


@Composable
fun SecondNameText(secondName: String, onTextChange: (String) -> Unit) {
    TextField(value = secondName, onValueChange = {onTextChange(it)}, modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(38.dp))
        .padding(8.dp),
        placeholder = { Text(text = "Segundo apellido") },
        maxLines = 1,
        singleLine = true,
        colors = TextFieldDefaults.colors(focusedContainerColor = Color.White,   // Color de fondo cuando está enfocado
            unfocusedContainerColor = Color.White, // Color de fondo cuando no está enfocado
            focusedIndicatorColor = Color.Transparent,   // Eliminar la línea inferior cuando está enfocado
            unfocusedIndicatorColor = Color.Transparent ),
        shape = RoundedCornerShape(38.dp)
    )
}

@Composable
fun EmailText(email: String, onTextChange: (String) -> Unit) {
    TextField(value = email, onValueChange = { onTextChange(it)},
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        maxLines = 1,
        singleLine = true,
        keyboardOptions = KeyboardOptions( keyboardType = KeyboardType.Email),
        placeholder = { Text(text = "Correo") },
        colors = TextFieldDefaults.colors(focusedContainerColor = Color.White,   // Color de fondo cuando está enfocado
            unfocusedContainerColor = Color.White, // Color de fondo cuando no está enfocado
            focusedIndicatorColor = Color.Transparent,   // Eliminar la línea inferior cuando está enfocado
            unfocusedIndicatorColor = Color.Transparent ),
        shape = RoundedCornerShape(38.dp)
    )

}

@Composable
fun PassText(pass: String, onTextChange: (String) -> Unit) {
    TextField(value = pass, onValueChange = {onTextChange(it)}, modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(38.dp))
        .padding(8.dp),
        placeholder = { Text(text = "Contraseña")},
        maxLines = 1,
        singleLine = true,
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions( keyboardType = KeyboardType.Password),
        colors = TextFieldDefaults.colors(focusedContainerColor = Color.White,   // Color de fondo cuando está enfocado
            unfocusedContainerColor = Color.White, // Color de fondo cuando no está enfocado
            focusedIndicatorColor = Color.Transparent,   // Eliminar la línea inferior cuando está enfocado
            unfocusedIndicatorColor = Color.Transparent ),
        shape = RoundedCornerShape(38.dp)
    )
}


@Composable
fun LoginButtonCreate(modifier: Modifier, isButtonEnable:Boolean, newUserViewModel: NewUserViewModel) {

    Column(modifier = modifier){
        Button(onClick = {
            newUserViewModel.createUser(newUserViewModel.userLive.value?: UserData())
        }, modifier= Modifier
            .padding(start = 24.dp, end = 24.dp, bottom = 16.dp)
            .fillMaxWidth(),
            enabled = isButtonEnable,
            colors = ButtonDefaults.buttonColors(containerColor = Color(color = 0xFFCA5300 ))) {
            Text(text = "Crear!")
        }
    }

}

@Composable
fun SignInScreen(onSignInClick: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth() // Ocupar todo el ancho disponible
            .padding(16.dp), // Aplicar padding
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "O iniciar sesión con Google")
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onSignInClick, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Iniciar sesión con Google")
        }
    }
}



