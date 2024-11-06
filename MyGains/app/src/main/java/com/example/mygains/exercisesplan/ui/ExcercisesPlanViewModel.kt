package com.example.mygains.exercisesplan.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mygains.exercisesplan.data.Exercises
import com.example.mygains.exercisesplan.data.RoutineDayData
import com.example.mygains.exercisesplan.data.SeriesAndReps
import com.example.mygains.exercisesplan.domain.ExcercisesPlanUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExcercisesPlanViewModel @Inject constructor(private var excercisesPlanUseCase: ExcercisesPlanUseCase):ViewModel() {

    private var _saveResult= MutableLiveData<Boolean>()
    var _saveResultLife:LiveData<Boolean> = _saveResult

    private var _isAlert= MutableLiveData<Boolean>()
    var _isAlertLife:LiveData<Boolean> = _isAlert

    private var _isLoading= MutableLiveData<Boolean>()
    var _isLoadingLife:LiveData<Boolean> = _isLoading

    private var _routineDayData= MutableLiveData<RoutineDayData>()
    var _routineDayDataLife:LiveData<RoutineDayData> = _routineDayData


    fun setAlert(show:Boolean){
        _isAlert.postValue(show)
    }

    fun resetResult(reset:Boolean){
        _saveResult.postValue(reset)
    }



    fun saveDataRoutine(routineDayData: RoutineDayData){
        _isLoading.postValue(true)
        if (!routineDayData.isEmpty()){
            viewModelScope.launch(Dispatchers.IO) {
                if (excercisesPlanUseCase.saveDataRoutine(routineDayData)){
                    _saveResult.postValue(true)
                    _isLoading.postValue(false)
                }else{
                    _isLoading.postValue(false)
                    _saveResult.postValue(false)
                    _isAlert.postValue(true)
                }
            }
        }else{
            _isLoading.postValue(false)
            _saveResult.postValue(false)
            _isAlert.postValue(true)
        }
    }
}