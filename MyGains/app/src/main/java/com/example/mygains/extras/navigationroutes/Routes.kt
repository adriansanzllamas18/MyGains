package com.example.mygains.extras.navigationroutes

 sealed class Routes(val routes: String) {

     object Home: Routes("home")
     object Perfil: Routes("perfil")
     object Login: Routes("login")
     object NewUser: Routes("newuser")
     object Splash: Routes("splash")
     object Plan: Routes("plan")
     object ExcercisesPlan: Routes("excercisesplan/{date}"){
         fun createRout(date: String)="excercisesplan/$date"
     }

 }