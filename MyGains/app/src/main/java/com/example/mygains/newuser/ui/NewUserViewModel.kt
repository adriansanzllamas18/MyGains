package com.example.mygains.newuser.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.util.Patterns
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat.getString
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mygains.extras.utils.FormatterUtils
import com.example.mygains.R
import com.example.mygains.base.BaseAuthError
import com.example.mygains.base.BaseResponse
import com.example.mygains.newuser.domain.NewUserUseCase
import com.example.mygains.userinfo.data.models.UserData
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okio.IOException
import javax.inject.Inject

@HiltViewModel
class NewUserViewModel @Inject constructor(private var newUserUseCase: NewUserUseCase): ViewModel() {

    private val _user= MutableLiveData<UserData>(UserData())
    val userLive : LiveData<UserData> = _user

    private val _button= MutableLiveData<Boolean>()
    val buttonLive : LiveData<Boolean> = _button

    private val _textInfo= MutableLiveData<Boolean>()
    val textInfoLive : LiveData<Boolean> = _textInfo

    private val _result= MutableLiveData<String>()
    val resultLive : LiveData<String> = _result

    private val _isLoading= MutableLiveData<Boolean>()
    val isLoadingLive :LiveData<Boolean> = _isLoading

    private val _loginResult = MutableLiveData<Boolean>()
    val loginResultLive: LiveData<Boolean> get() = _loginResult

    private val _isAlert= MutableLiveData<Boolean>()
    val isAlertLive :LiveData<Boolean> = _isAlert


    fun showAlert(view:Boolean){
        if (view){
            _isAlert.postValue(true)
        }else{
            _isAlert.postValue(false)
        }
    }


    fun onParamsChanged(userData: UserData){
        _user.value = userData
        _button.value= isAllenteredEnable(userData)
        _textInfo.value = isAllenteredEnable(userData)
    }

    fun createUser(userData: UserData){
        _isLoading.value=true
        viewModelScope.launch(Dispatchers.IO) {

            try {
                when( val response = newUserUseCase.createUserWithEmailPass(userData)){
                    is BaseResponse.Error ->{
                        _result.postValue(response.mapError())
                        _isAlert.postValue(true)
                        _isLoading.postValue(false)
                    }
                    is BaseResponse.Success->{_result.postValue(response.data)}
                }
            }catch (ex:Exception){
                _result.postValue("Error interno intentelo más tarde.")
            }

        }
    }

    fun isAllenteredEnable(userData: UserData) =
        Patterns.EMAIL_ADDRESS.matcher(userData.email).matches() && userData.pass.length > 6 && userData.name.isNotEmpty()
                && userData.first_name.isNotEmpty()  && userData.second_name.isNotEmpty()

}


