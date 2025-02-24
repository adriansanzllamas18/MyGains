package com.example.mygains.createroutineprocess.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mygains.base.BaseResponse
import com.example.mygains.createroutineprocess.data.models.TypeOfWorkOutModel
import com.example.mygains.createroutineprocess.domain.CreateRoutineUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CreateRoutineViewModel @Inject constructor(var usecase: CreateRoutineUsecase):ViewModel() {

    private var _workouts = MutableLiveData<MutableList<TypeOfWorkOutModel>>()
    val workoutsLive: MutableLiveData<MutableList<TypeOfWorkOutModel>> = _workouts

    init {
        viewModelScope.launch(Dispatchers.IO) {
            delay(2000)
            when( val result = usecase.getAllWorkouts()){
                is BaseResponse.Success->{_workouts.postValue(result.data)}
                is BaseResponse.Error->{}
            }
        }
    }
}