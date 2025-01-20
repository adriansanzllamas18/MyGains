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
import com.example.mygains.newuser.domain.NewUserUseCase
import com.example.mygains.userinfo.data.models.UserData
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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
        viewModelScope.launch {
            try {
                // Crea el usuario
                val authResult = newUserUseCase.createUserWithEmailPass(userData)
                // Verificamos que la creación del usuario fue exitosa
                if (authResult != null) {
                    // Guardamos la información del usuario en Firestore
                    val isSaved = newUserUseCase.saveInfoUser(userData)

                    if (isSaved) {
                        // Notificar que el usuario se creó y guardó correctamente
                        _result.postValue("Usuario creado y guardado correctamente").toString()
                        _isLoading.value=false
                    } else {
                        _result.postValue("Error al guardar la información del usuario").toString()
                        _isLoading.value=false
                        showAlert(true)
                    }
                } else {
                    _result.postValue("Error al crear el usuario").toString()
                    _isLoading.value=false
                    showAlert(true)
                }
            } catch (e: Exception) {
                _isLoading.value=false
                _result.postValue("Compruebe la conexión a internet , o intentelo más tarde")
                showAlert(true)
            }
        }
    }

    fun isAllenteredEnable(userData: UserData) =
        Patterns.EMAIL_ADDRESS.matcher(userData.email).matches() && userData.pass.length > 6 && userData.name.isNotEmpty()
                && userData.first_name.isNotEmpty()  && userData.second_name.isNotEmpty()


    fun signInWithGoogle(activity: Activity, signInLauncher: ActivityResultLauncher<Intent>, context: Context) {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(context,R.string.default_web_client_id)) // Obtiene el ID de cliente desde resources
            .requestEmail()
            .build()

        val googleSignInClient = GoogleSignIn.getClient(activity, gso)
        val signInIntent = googleSignInClient.signInIntent
        signInLauncher.launch(signInIntent) // Lanza el intent
    }

    fun handleGoogleSignInResult(account: GoogleSignInAccount?) {
        _isLoading.value=true
        if (account != null) {
            viewModelScope.launch(Dispatchers.IO) {
                // Verifica si el idToken está disponible
                val idToken = account.idToken
                if (idToken != null) {
                    try {
                        // Crear usuario con credenciales de Google
                        val user = newUserUseCase.createUserWithGoogleCredentials(account)
                        // Guardar la información del usuario en Firestore
                        if (user != null) {
                            var separateName = FormatterUtils().getUserNameFirstNameScondName(user.displayName?:"")
                            val saveResult = newUserUseCase.saveInfoUser(
                                UserData(
                                email = user.email ?: "",
                                name = separateName[0],
                                second_name = separateName[2],
                                first_name = separateName[1],
                                image= (user.photoUrl?:"").toString()
                            )
                            )
                            _isLoading.postValue(false)
                            _loginResult.postValue(saveResult)
                        } else {
                            showAlert(true)
                            _result.postValue("Error al crear el usuario con credenciales de Google.")
                            Log.e("Login", "Error al crear el usuario con credenciales de Google.")
                            _isLoading.postValue(false)
                        }
                    } catch (e: Exception) {
                        showAlert(true)
                        Log.e("Login", "Error durante el inicio de sesión: ${e.message}")
                        _result.postValue("Error al crear el usuario con credenciales de Google.")
                        _isLoading.postValue(false)
                    }
                } else {
                    Log.e("Login", "El idToken es nulo.")
                    showAlert(true)
                    _result.postValue("Error al crear el usuario con credenciales de Google.")
                    _isLoading.postValue(false)
                }
            }
        } else {
            Log.e("Login", "El objeto account es nulo.")
            _result.postValue("Error al crear el usuario con credenciales de Google.")
            showAlert(true)
            _isLoading.postValue(false)
        }
    }

}


