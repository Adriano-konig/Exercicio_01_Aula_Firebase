package br.com.zup.exercicio_aulafirebase_01.ui.login.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.zup.exercicio_aulafirebase_01.Utils.EMAIL_ERROR_MESSAGE
import br.com.zup.exercicio_aulafirebase_01.Utils.LOGIN_ERROR_MESSAGE
import br.com.zup.exercicio_aulafirebase_01.Utils.PASSWORD_ERROR_MESSAGE
import br.com.zup.exercicio_aulafirebase_01.domain.model.user.User
import br.com.zup.exercicio_aulafirebase_01.domain.repository.AuthenticationRepository

class LoginViewModel: ViewModel() {
    private val authenticationRepository = AuthenticationRepository()

    private var _loginState = MutableLiveData<User>()
    val loginState: LiveData<User> = _loginState

    private var _errorState = MutableLiveData<String>()
    val errorState: LiveData<String> = _errorState

    fun validateDataUser(user: User) {
        when {
            user.email.isEmpty() -> {
                _errorState.value = EMAIL_ERROR_MESSAGE
            }
            user.password.isEmpty() -> {
                _errorState.value = PASSWORD_ERROR_MESSAGE
            }
            else -> {
                loginUser(user)
            }
        }
    }

    private fun loginUser(user: User) {
        try {
            authenticationRepository.loginUser(
                user.email,
                user.password
            ).addOnSuccessListener {
                _loginState.value = user
            }.addOnFailureListener {
                _errorState.value = LOGIN_ERROR_MESSAGE + it.message
            }
        } catch (ex: Exception) {
            _errorState.value = ex.message
        }
    }
}