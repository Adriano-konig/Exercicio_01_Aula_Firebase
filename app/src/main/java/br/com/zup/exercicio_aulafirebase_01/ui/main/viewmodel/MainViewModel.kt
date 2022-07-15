package br.com.zup.exercicio_aulafirebase_01.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.zup.exercicio_aulafirebase_01.domain.repository.AuthenticationRepository

class MainViewModel : ViewModel() {
    private val authenticationRepository = AuthenticationRepository()


    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    fun getUserName() = authenticationRepository.getNameUser()

    fun getUserEmail() = authenticationRepository.getEmailUser()

    fun logout() = authenticationRepository.logoutOut()

}