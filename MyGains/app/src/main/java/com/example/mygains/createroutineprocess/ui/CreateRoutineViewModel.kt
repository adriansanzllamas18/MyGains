package com.example.mygains.createroutineprocess.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mygains.base.BaseResponse
import com.example.mygains.createroutineprocess.data.models.InfoTypeOfWorkOutModel
import com.example.mygains.createroutineprocess.data.models.StrengthExerciseModel
import com.example.mygains.createroutineprocess.data.models.TypeOfWorkOutModel
import com.example.mygains.createroutineprocess.domain.usecases.CreateRoutineUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CreateRoutineViewModel @Inject constructor(
    private var createRoutineUsecase:CreateRoutineUsecase)
    :ViewModel() {

    private var _workouts = MutableLiveData<MutableList<TypeOfWorkOutModel>>()
    val workoutsLive: MutableLiveData<MutableList<TypeOfWorkOutModel>> = _workouts

    private var _infoWorkouts = MutableLiveData<MutableList<InfoTypeOfWorkOutModel>>()
    val infoWorkoutsLive: MutableLiveData<MutableList<InfoTypeOfWorkOutModel>> = _infoWorkouts

    private var _exercises = MutableLiveData<MutableList<StrengthExerciseModel>>()
    val exercisesLive: MutableLiveData<MutableList<StrengthExerciseModel>> = _exercises

    private var _showDetail = MutableLiveData<Boolean>()
    val showDetailLive: MutableLiveData<Boolean> = _showDetail

    private var _exercisesSelected = MutableLiveData<StrengthExerciseModel>()
    val exercisesSelectedlLive: MutableLiveData<StrengthExerciseModel> = _exercisesSelected

    private var _exercisesToAdd = MutableLiveData<MutableList<StrengthExerciseModel>>()
    val exercisesToAddLive: MutableLiveData<MutableList<StrengthExerciseModel>> = _exercisesToAdd




    fun setExerciseToAddList(strengthExerciseModel: StrengthExerciseModel){
        val listConverter:MutableList<StrengthExerciseModel> =  _exercisesToAdd.value?: mutableListOf()
        listConverter.add(strengthExerciseModel)
        _exercisesToAdd.postValue(listConverter)
    }

    fun deleteExerciseToAddList(strengthExerciseModel: StrengthExerciseModel){
        val listConverter:MutableList<StrengthExerciseModel> =  _exercisesToAdd.value?: mutableListOf()
        listConverter.remove(strengthExerciseModel)
        _exercisesToAdd.postValue(listConverter)
    }

    fun setExercisesVisibility(isVisble:Boolean){
        _showDetail.postValue(isVisble)
    }

    fun setSelectedExercise(exerciseSelected:StrengthExerciseModel) {
        _exercisesSelected.postValue(exerciseSelected)
    }


        fun getAllWorkOuts(){
        viewModelScope.launch(Dispatchers.IO) {
            delay(2000)
            when( val result = createRoutineUsecase.getAllWorkouts()){
                is BaseResponse.Success->{_workouts.postValue(result.data)}
                is BaseResponse.Error->{}
            }
        }
    }


    fun getAllInfoWorkOuts(workout_id:String){
        viewModelScope.launch(Dispatchers.IO) {
            when(val result = createRoutineUsecase.getAllInfoWorkOuts(workout_id)){
                is BaseResponse.Success->{_infoWorkouts.postValue(result.data)}
                is BaseResponse.Error->{}
            }
        }
    }

    fun getAllExercises(muscle_id:String){
        viewModelScope.launch (Dispatchers.IO){
            when(val result = createRoutineUsecase.getAllExercises(muscle_id)){
                is BaseResponse.Success->{_exercises.postValue(result.data)}
                is BaseResponse.Error->{}
            }
        }
    }
}