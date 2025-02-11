package com.example.mygains.routinedetail.ui

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mygains.exercisesplan.domain.usecases.ExcercisesPlanUseCase
import com.example.mygains.routinedetail.data.RoutineDetailModel
import com.example.mygains.routinedetail.domain.usecases.RoutineDetailUseCase
import com.kizitonwose.calendar.core.DayPosition
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject


@HiltViewModel
class RoutineDetailViewModel @Inject constructor(val routineDetailUseCase:RoutineDetailUseCase):ViewModel() {


    private var _RoutineDetailModel = MutableLiveData<MutableList<RoutineDetailModel>> ()
    var routineDetailModelLife:MutableLiveData<MutableList<RoutineDetailModel>> = _RoutineDetailModel

    private var _Error = MutableLiveData<Boolean> ()
    var errorLife:MutableLiveData<Boolean> = _Error

    private var _currentPage = MutableLiveData<Int> ()
    var currentPageLife:MutableLiveData<Int> = _currentPage

    init {
        getRoutineDetail(LocalDate.now().toString())
    }


    fun setSliderPosition(position:Int){
        this._currentPage.postValue(position)
    }


     fun getRoutineDetail(currentDate:String){

         viewModelScope.launch(Dispatchers.IO) {
             val result = routineDetailUseCase.getRoutineOfDay(currentDate)
             _RoutineDetailModel.postValue(result)
             Log.i("dataroutine", result.toString())

         }
     }
}