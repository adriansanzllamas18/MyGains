package com.example.mygains.login.ui

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mygains.login.domain.LoginUseCase
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okio.IOException
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private var loginUseCase: LoginUseCase):ViewModel() {


    private val _email= MutableLiveData<String>()
    val emailLive :LiveData<String> = _email

    private val _pass= MutableLiveData<String>()
    val passLive :LiveData<String> = _pass

    private val _button= MutableLiveData<Boolean>()
    val buttonLive :LiveData<Boolean> = _button

    private val _loginResult= MutableLiveData<Boolean>()
    val loginResultLive :LiveData<Boolean> = _loginResult

    private val _isLoading= MutableLiveData<Boolean>()
    val isLoadingLive :LiveData<Boolean> = _isLoading

    private val _isAlert= MutableLiveData<Boolean>()
    val isAlertLive :LiveData<Boolean> = _isAlert

    private val _error= MutableLiveData<String>()
    val errorLive : LiveData<String> = _error




    fun onLoginChanged(pass:String, email:String){
        _pass.value =  pass
        _email.value =  email
        _button.value= isLoginEnable(pass = pass,email= email)
    }

    fun isLoginEnable(pass:String, email:String) =
        Patterns.EMAIL_ADDRESS.matcher(email).matches() && pass.length > 6


    fun LogInWithEmailPassword(user:String,pass: String){
        _isLoading.value= true
        _loginResult.value= false
        try {
            viewModelScope.launch(Dispatchers.IO) {

                try {
                    // esto quiere decir que si no devuelve un objeto user es decir que devuelva null significa que ha ido mal el inicio de sesion
                    _loginResult.postValue(loginUseCase.logInWithEmailPassword(user,pass) != null)
                    _isLoading.postValue(false)

                }catch (e:Exception){
                    _isLoading.postValue(false)
                    showAlert(true)
                    _error.postValue("Compruebe la conexión a internet , o intentelo más tarde")
                }
            }
        }catch (e:Exception){
                _isLoading.postValue(false)
                showAlert(true)
                _error.postValue("Compruebe la conexión a internet , o intentelo más tarde")
            }
        }


    fun showAlert(show: Boolean) {
        if (show) _isAlert.postValue(true) else  _isAlert.postValue(false)
    }


    fun loginWithGoogle(account: GoogleSignInAccount){
        _isLoading.value= true
        viewModelScope.launch(Dispatchers.IO) {
            var idToken= account.idToken
            if (idToken!= null){
                try {
                    // esto quiere decir que si no devuelve un objeto user es decir que devuelva null significa que ha ido mal el inicio de sesion
                    _loginResult.postValue(loginUseCase.createUserWithGoogleCredentials(account) != null)
                    _isLoading.postValue(false)

                }catch (ex:IOException){
                    _isLoading.postValue(false)
                    showAlert(true)
                    _error.postValue("Compruebe la conexión a internet , o intentelo más tarde")
                }
            }
        }
    }


}