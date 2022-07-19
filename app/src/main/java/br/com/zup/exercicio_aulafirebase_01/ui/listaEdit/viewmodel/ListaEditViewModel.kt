package br.com.zup.exercicio_aulafirebase_01.ui.listaEdit.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.zup.exercicio_aulafirebase_01.domain.repository.AuthenticationRepository
import br.com.zup.exercicio_aulafirebase_01.domain.repository.ListaEditRepository
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class ListaEditViewModel : ViewModel() {

    private val listaEditRepository = ListaEditRepository()
    private val auth = AuthenticationRepository()

    private var _listaEditState = MutableLiveData<List<String>>()
    val listaEditState: LiveData<List<String>> = _listaEditState

    private var _messageState = MutableLiveData<String>()
    val messageState: LiveData<String> = _messageState

    fun getListEdit() {
        listaEditRepository.getListEdit()
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    val editList = mutableListOf<String>()

                    for (resultSnapshot in snapshot.children) {
                        val editResponse = resultSnapshot.getValue(String::class.java)
                        editResponse?.let { editList.add(it) }
                    }
                    _listaEditState.value = editList
                }

                override fun onCancelled(error: DatabaseError) {
                    _messageState.value = error.message
                }
            })
    }

    fun removeListaEdit(edit: String){
        val uri: Uri = Uri.parse(edit)
        val pathEdit: String? = uri.lastPathSegment?.replace("", "")
        listaEditRepository.databaseReference().child("$pathEdit").removeValue()
    }

    fun getCurrentUser() = auth.getCurrentUser()
}