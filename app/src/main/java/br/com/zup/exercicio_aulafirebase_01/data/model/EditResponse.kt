package br.com.zup.exercicio_aulafirebase_01.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class EditResponse(
    @SerializedName("file")
    val edit: String = "",
    var title: String = "",
    var body: String = ""
):Parcelable