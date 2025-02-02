package com.example.mygains.routinedetail.ui

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mygains.routinedetail.data.RoutineDetailModel
import com.example.mygains.routinedetail.domain.usecases.RoutineDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject


@HiltViewModel
class RoutineDetailViewModel @Inject constructor(val routineDetailUseCase:RoutineDetailUseCase):ViewModel() {


    private var _RoutineDetailModel = MutableLiveData<RoutineDetailModel?> ()
    var routineDetailModelLife:MutableLiveData<RoutineDetailModel?> = _RoutineDetailModel

    private var _Error = MutableLiveData<Boolean> ()
    var errorLife:MutableLiveData<Boolean> = _Error

    init {
        getRoutineDetail(LocalDate.now().toString())
    }


     fun getRoutineDetail(currentDate:String){

         viewModelScope.launch(Dispatchers.IO) {

             val result = routineDetailUseCase.getRoutineOfDay(currentDate)
             if (result!= null){
               _RoutineDetailModel.postValue(result)
                 Log.i("dataroutine", result.toString())
             } else{
                 _Error.postValue(true)
             }

         }
     }
}