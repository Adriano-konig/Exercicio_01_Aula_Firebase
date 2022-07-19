package br.com.zup.exercicio_aulafirebase_01.ui.main.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.zup.exercicio_aulafirebase_01.Utils.EDIT_MESSAGE
import br.com.zup.exercicio_aulafirebase_01.data.model.EditResponse
import br.com.zup.exercicio_aulafirebase_01.domain.repository.AuthenticationRepository
import br.com.zup.exercicio_aulafirebase_01.domain.repository.ListaEditRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel : ViewModel() {
    private val authenticationRepository = AuthenticationRepository()
    private val listEditRepository = ListaEditRepository()

    private var _listaEditState = MutableLiveData<List<EditResponse>>()
    val listaEditState: LiveData<List<EditResponse>> = _listaEditState

    private val _editResponse = MutableLiveData<EditResponse>()
    val editResponse: LiveData<EditResponse> = _editResponse

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

//    fun getEdit() {
//        _loading.value = true
//        viewModelScope.launch {
//            try {
//                val response = withContext(Dispatchers.IO) {
//                   listEditRepository
//                }
//                _editResponse.value = response
//            } catch (ex: Exception) {
//                _message.value = "Tivemos algum problema, tente novamente!"
//            } finally {
//                _loading.value = false
//            }
//        }
//    }

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    fun saveEdit(){
        val edit = _editResponse.value?.edit.toString()
        val editPath = getEditPath()

        listEditRepository.databaseReference().child("$editPath")
            .setValue(edit){
                error, reference ->
                if (error != null){
                    _message.value = error.message
                }
                _message.value = EDIT_MESSAGE
            }
    }

    private fun getEditPath(): String?{
        val edit = _editResponse.value?.edit.toString()
        val uri: Uri = Uri.parse(edit)
        return uri.lastPathSegment?.replace("","")
    }

    fun getUserName() = authenticationRepository.getNameUser()

    fun getUserEmail() = authenticationRepository.getEmailUser()

    fun logout() = authenticationRepository.logoutOut()

}