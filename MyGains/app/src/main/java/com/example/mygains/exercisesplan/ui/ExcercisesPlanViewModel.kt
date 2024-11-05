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

    private var _routineDayData= MutableLiveData<RoutineDayData>()
    var _routineDayDataLife:LiveData<RoutineDayData> = _routineDayData



    fun saveDataRoutine(routineDayData: RoutineDayData){
        viewModelScope.launch(Dispatchers.IO) {
            _saveResult.postValue(
                excercisesPlanUseCase.saveDataRoutine(
               routineDayData
            )
            )

        }

    }
}