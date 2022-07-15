package br.com.zup.exercicio_aulafirebase_01.domain.model.user

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    var name: String = "",
    var email: String = "",
    var password: String = ""
): Parcelable