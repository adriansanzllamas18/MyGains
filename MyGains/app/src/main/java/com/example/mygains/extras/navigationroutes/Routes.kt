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
     object GainsScanner: Routes("gainsscanner")
     object CameraScaner: Routes("camerascanner")
     object FoodDeatil: Routes("fooddetail/{codebar}"){
         fun createRout(codebar: String)="fooddetail/$codebar"
     }


    object InfoTypeOfWorkout: Routes("infotypeofworkout/{workout_id}"){
         fun createRout(workout_id: String)="infotypeofworkout/$workout_id"
     }


    object TypesWorkOuts: Routes("typesworkouts/{date}"){
        fun createRout(date: String) = "typesworkouts/$date"
    }

    object Exercises: Routes("exercises/{muscle_id}"){
        fun createRout(muscle_id: String)="exercises/$muscle_id"
    }

    object Configuration: Routes("configuration")

}