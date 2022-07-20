package br.com.zup.exercicio_aulafirebase_01.ui.main.viewmodel

import android.app.Application
import android.app.Notification
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import androidx.lifecycle.*
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import br.com.zup.exercicio_aulafirebase_01.Utils.EDIT_MESSAGE
import br.com.zup.exercicio_aulafirebase_01.data.model.EditResponse
import br.com.zup.exercicio_aulafirebase_01.domain.MyFirebaseCloudService.Companion.CURRENT_TOKEN
import br.com.zup.exercicio_aulafirebase_01.domain.MyFirebaseCloudService.Companion.NEW_NOTIFICATION
import br.com.zup.exercicio_aulafirebase_01.domain.MyFirebaseCloudService.Companion.NOTIFICATION_BODY_KEY
import br.com.zup.exercicio_aulafirebase_01.domain.MyFirebaseCloudService.Companion.NOTIFICATION_TITLE_KEY
import br.com.zup.exercicio_aulafirebase_01.domain.MyFirebaseCloudService.Companion.TOKEN_KEY
import br.com.zup.exercicio_aulafirebase_01.domain.repository.AuthenticationRepository
import br.com.zup.exercicio_aulafirebase_01.domain.repository.ListaEditRepository
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val authenticationRepository = AuthenticationRepository()
    private val listEditRepository = ListaEditRepository()

    private var _currentToken = MutableLiveData<String>()
    var currentToken: LiveData<String> = _currentToken

    private var _lastNotification = MutableLiveData<EditResponse>()
    var lastNotification: LiveData<EditResponse> = _lastNotification

    private var context = application

    private var tokenReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            try {
                _currentToken.value = intent?.getStringExtra(TOKEN_KEY).toString()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private var notificationReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            try {
                val title = intent?.getStringExtra(NOTIFICATION_TITLE_KEY).toString()
                val body = intent?.getStringExtra(NOTIFICATION_BODY_KEY).toString()
                val notification = EditResponse(
                    title = title,
                    body = body
                )
                _lastNotification.value = notification
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    init {
        LocalBroadcastManager.getInstance(context).registerReceiver((tokenReceiver),
        IntentFilter(CURRENT_TOKEN)
        )

        LocalBroadcastManager.getInstance(context).registerReceiver((notificationReceiver),
        IntentFilter(NEW_NOTIFICATION)
        )
    }


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