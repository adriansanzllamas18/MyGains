package com.example.mygains.plan.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mygains.exercisesplan.data.RoutineDayData
import com.example.mygains.plan.domain.PlanUseCase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlanViewModel @Inject constructor(var planUseCase: PlanUseCase):ViewModel(){



    private var  _routineDayList = MutableLiveData<MutableList<RoutineDayData>>()
    var _routineDayListLife: LiveData<MutableList<RoutineDayData>> =_routineDayList

    fun getAllExcercisesForDay(date:String){
        viewModelScope.launch(Dispatchers.IO) {

            try {
                try {
                    var result= planUseCase.getPlanForTheDay(date)
                    _routineDayList.postValue(result)
                    Log.i("dataDay", result.toString())
                }catch (er:Exception){
                    Log.e(er.cause.toString(),er.message.toString())
                }

            }catch (er:Exception){
                Log.e(er.cause.toString(),er.message.toString())
            }
        }
    }
}