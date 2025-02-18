package com.example.mygains.splashscreen.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mygains.splashscreen.domain.usecases.SplashScreenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(private var splashScreenUseCase: SplashScreenUseCase):ViewModel() {

    private val _isAlreadyLoged= MutableLiveData<Boolean>()
    val isAlreadyLogedLive : LiveData<Boolean> = _isAlreadyLoged


    init {
        isAlreadyLoged()
    }

    fun isAlreadyLoged(){
        viewModelScope.launch(Dispatchers.IO) {
            delay(2000)
            _isAlreadyLoged.postValue(splashScreenUseCase.isAlreadyLoged()!= null)
        }
    }
}