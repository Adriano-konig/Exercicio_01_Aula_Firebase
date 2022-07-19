package br.com.zup.exercicio_aulafirebase_01.ui.listaEdit.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.zup.exercicio_aulafirebase_01.databinding.EditItemBinding



class ListaEditAdapter(
    private var editList: MutableList<String>,
    private val onCLick: (edit: String) -> Unit

):
    RecyclerView.Adapter<ListaEditAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val binding =
                EditItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ViewHolder(binding)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val edit = editList[position]
            holder.adicionarEdit(edit)
            holder.binding.ivRemoveEdit.setOnClickListener {
                onCLick(edit)
            }
        }

        override fun getItemCount() = editList.size

        fun updateEditList(newList: MutableList<String>) {
            editList = newList
            notifyDataSetChanged()
        }

        class ViewHolder(val binding:  EditItemBinding) :
            RecyclerView.ViewHolder(binding.root) {
            fun adicionarEdit(edit: String){
                binding.editText.text = edit
            }
        }
}