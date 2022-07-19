package br.com.zup.exercicio_aulafirebase_01.ui.listaEdit.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.zup.exercicio_aulafirebase_01.databinding.ActivityListaEditBinding
import br.com.zup.exercicio_aulafirebase_01.ui.listaEdit.viewmodel.ListaEditViewModel

class ListaEditActivity : AppCompatActivity() {

    private lateinit var binding : ActivityListaEditBinding

    private val viewModel: ListaEditViewModel by lazy {
        ViewModelProvider(this)[ListaEditViewModel::class.java]
    }

    private val adapter: ListaEditAdapter by lazy {
        ListaEditAdapter(arrayListOf(),::removeEditTexto)
    }


    override fun onStart(){
        super.onStart()
        val atualizar = viewModel.getCurrentUser()
        atualizar?.reload()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListaEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.getListEdit()
        setUpRecyclerView()
        initObservers()

    }

    private fun setUpRecyclerView() {
        binding.rvListaEdit.layoutManager = LinearLayoutManager(this)
        binding.rvListaEdit.adapter = adapter
    }

    private fun initObservers() {
        viewModel.listaEditState.observe(this) {
            adapter.updateEditList(it.toMutableList())
        }

        viewModel.messageState.observe(this) {
            loadMessage(it)
        }
    }

    private fun loadMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }


    private fun removeEditTexto(edit: String) {
        viewModel.removeListaEdit(edit)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            this.finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}