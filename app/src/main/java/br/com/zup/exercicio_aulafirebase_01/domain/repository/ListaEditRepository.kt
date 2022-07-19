package br.com.zup.exercicio_aulafirebase_01.domain.repository

import br.com.zup.exercicio_aulafirebase_01.Utils.TEXT_edit_PATH
import br.com.zup.exercicio_aulafirebase_01.Utils.Text_PATH
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.ktx.Firebase

class ListaEditRepository {
    private val authentication: FirebaseAuth = Firebase.auth
    private val database = FirebaseDatabase.getInstance()
    private val reference = database.getReference("$TEXT_edit_PATH/${authentication.currentUser?.uid}/$Text_PATH")

    fun databaseReference() = reference

    fun getListEdit(): Query {
        return reference.orderByValue()
    }
}