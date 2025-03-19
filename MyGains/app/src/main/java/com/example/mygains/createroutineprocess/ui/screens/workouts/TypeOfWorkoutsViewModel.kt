package com.example.mygains.createroutineprocess.ui.screens.workouts

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mygains.base.response.BaseResponse
import com.example.mygains.createroutineprocess.data.models.TypeOfWorkOutModel
import com.example.mygains.createroutineprocess.domain.usecases.workouts.TypeOfWorkoutsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TypeOfWorkoutsViewModel @Inject constructor(private var useCase: TypeOfWorkoutsUseCase):ViewModel() {

    private var _workouts = MutableLiveData<MutableList<TypeOfWorkOutModel>>()
    val workoutsLive: MutableLiveData<MutableList<TypeOfWorkOutModel>> = _workouts

    fun getAllWorkOuts(){
        viewModelScope.launch(Dispatchers.IO) {
            delay(2000)
            when( val result = useCase.getAllWorkouts()){
                is BaseResponse.Success->{_workouts.postValue(result.data)}
                is BaseResponse.Error->{}
            }
        }
    }
}