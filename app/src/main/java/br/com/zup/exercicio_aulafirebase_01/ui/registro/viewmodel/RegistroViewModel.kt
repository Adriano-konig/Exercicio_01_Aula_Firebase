package br.com.zup.exercicio_aulafirebase_01.ui.registro.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.zup.exercicio_aulafirebase_01.Utils.CREATE_USER_ERROR_MESSAGE
import br.com.zup.exercicio_aulafirebase_01.Utils.EMAIL_ERROR_MESSAGE
import br.com.zup.exercicio_aulafirebase_01.Utils.NAME_ERROR_MESSAGE
import br.com.zup.exercicio_aulafirebase_01.Utils.PASSWORD_ERROR_MESSAGE
import br.com.zup.exercicio_aulafirebase_01.domain.model.user.User
import br.com.zup.exercicio_aulafirebase_01.domain.repository.AuthenticationRepository

class RegistroViewModel: ViewModel() {
    private val authenticationRepository = AuthenticationRepository()

    private var _registerState = MutableLiveData<User>()
    val registerState: LiveData<User> = _registerState

    private var _errorState = MutableLiveData<String>()
    val errorState: LiveData<String> = _errorState

    fun validateDataUser(user: User) {
        when {
            user.name.isEmpty() -> {
                _errorState.value = NAME_ERROR_MESSAGE
            }
            user.email.isEmpty() -> {
                _errorState.value = EMAIL_ERROR_MESSAGE
            }
            user.password.isEmpty() -> {
                _errorState.value = PASSWORD_ERROR_MESSAGE
            }
            else -> {
                registerUser(user)
            }
        }
    }

    private fun registerUser(user: User) {
        try {
            authenticationRepository.registerUser(
                user.email,
                user.password
            ).addOnSuccessListener {

                authenticationRepository.updateUserProfile(user.name)?.addOnSuccessListener {
                    _registerState.value = user
                }

            }.addOnFailureListener {
                _errorState.value = CREATE_USER_ERROR_MESSAGE + it.message
            }
        } catch (ex: Exception) {
            _errorState.value = ex.message
        }
    }
}