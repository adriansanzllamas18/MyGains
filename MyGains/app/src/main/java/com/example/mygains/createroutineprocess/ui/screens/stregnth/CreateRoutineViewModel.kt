package com.example.mygains.createroutineprocess.ui.screens.stregnth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mygains.base.response.BaseResponse
import com.example.mygains.base.response.BaseResponseUi
import com.example.mygains.createroutineprocess.data.models.ExerciseSet
import com.example.mygains.createroutineprocess.data.models.ExerciseWithSets
import com.example.mygains.createroutineprocess.data.models.InfoTypeOfWorkOutModel
import com.example.mygains.createroutineprocess.data.models.StrengthExerciseModel
import com.example.mygains.createroutineprocess.data.models.TypeOfWorkOutModel
import com.example.mygains.createroutineprocess.domain.usecases.strength.CreateRoutineUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CreateRoutineViewModel @Inject constructor(
    private var createRoutineUsecase: CreateRoutineUsecase
) :ViewModel() {

    private var _infoWorkouts = MutableLiveData<MutableList<InfoTypeOfWorkOutModel>>()
    val infoWorkoutsLive: MutableLiveData<MutableList<InfoTypeOfWorkOutModel>> = _infoWorkouts

    private var _exercises = MutableLiveData<MutableList<StrengthExerciseModel>>()
    val exercisesLive: MutableLiveData<MutableList<StrengthExerciseModel>> = _exercises

    private var _showDetail = MutableLiveData<Boolean>()
    val showDetailLive: MutableLiveData<Boolean> = _showDetail

    private var _exercisesSelected = MutableLiveData<StrengthExerciseModel>()
    val exercisesSelectedlLive: MutableLiveData<StrengthExerciseModel> = _exercisesSelected

    private var _exerciseWithSets = MutableLiveData<MutableList<ExerciseWithSets>>()
    val exerciseWithSetsLive: MutableLiveData<MutableList<ExerciseWithSets>> = _exerciseWithSets

    private var _ResultSave = MutableLiveData<BaseResponseUi?>()
    val resultSaveLive: MutableLiveData<BaseResponseUi?> = _ResultSave



    fun upDateDialog(show:Boolean){
        val re =_ResultSave.value?.copy(show = show)
        _ResultSave.postValue(re)
    }

    fun addExercise(exercise: StrengthExerciseModel) {
        val currentList = _exerciseWithSets.value ?: mutableListOf()
        val newList = currentList.toMutableList()
        // Añadir el ejercicio solo si no existe ya
        if (newList.none { it.exercise == exercise }) {
            newList.add(ExerciseWithSets(exercise))
        }
        _exerciseWithSets.postValue(newList)
    }

    fun updateSetsForExercise(exercise: StrengthExerciseModel, sets: List<ExerciseSet>) {
        val currentList = _exerciseWithSets.value ?: mutableListOf()
        val newList = currentList.toMutableList()

        // Encuentra el ejercicio y actualiza sus series
        val exerciseIndex = newList.indexOfFirst { it.exercise == exercise }
        if (exerciseIndex != -1) {
            newList[exerciseIndex] = newList[exerciseIndex].copy(sets = sets.toMutableList())
        }

        _exerciseWithSets.postValue(newList)
    }

    fun removeExercise(exercise: StrengthExerciseModel) {
        val currentList = _exerciseWithSets.value ?: mutableListOf()
        val newList = currentList.toMutableList()
        newList.removeIf { it.exercise == exercise }
        _exerciseWithSets.postValue(newList)
    }

    fun setExercisesVisibility(isVisble:Boolean){
        _showDetail.postValue(isVisble)
    }

    fun setSelectedExercise(exerciseSelected:StrengthExerciseModel) {
        _exercisesSelected.postValue(exerciseSelected)
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

    fun saveRoutine(exercises:MutableList<ExerciseWithSets>,date: String){
        viewModelScope.launch(Dispatchers.IO) {
            when(val result = createRoutineUsecase.saveRoutine(exercises,date)){
                is BaseResponse.Success->{}
                is BaseResponse.Error->{_ResultSave.postValue(BaseResponseUi(show = true, response = result, message = result.mapError()))}
            }
        }
    }
}