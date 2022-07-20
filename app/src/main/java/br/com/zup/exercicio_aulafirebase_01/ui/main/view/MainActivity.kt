package br.com.zup.exercicio_aulafirebase_01.ui.main.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import br.com.zup.exercicio_aulafirebase_01.R
import br.com.zup.exercicio_aulafirebase_01.databinding.ActivityMainBinding
import br.com.zup.exercicio_aulafirebase_01.ui.listaEdit.view.ListaEditActivity
import br.com.zup.exercicio_aulafirebase_01.ui.login.view.LoginActivity
import br.com.zup.exercicio_aulafirebase_01.ui.main.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initObsrvable()
        viewModel.getUserName()
        showUserData()
        favoritedImage()
        crashButton()
    }

    private fun crashButton(){
        val crashButton = Button(this)
        crashButton.text = getString(R.string.test_crash)
        crashButton.setOnClickListener {
            throw RuntimeException("Test Crash") // Force a crash
        }

        addContentView(crashButton, ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT))
    }

    private fun initObsrvable(){
        viewModel.currentToken.observe(this){
            binding.tvToken.text = it
        }
        viewModel.lastNotification.observe(this){
            binding.tvNotificationTitle.text = it.title
            binding.tvNotificationBody.text = it.body
        }
    }


    private fun showUserData(){
        val name = viewModel.getUserEmail()
        binding.nome.text = "$name"
    }

    private fun favoritedImage(){
        binding.buttonSalvar.setOnClickListener {
//            val editText = binding.editText.text.toString()
            viewModel.saveEdit()
            startActivity(Intent(this, ListaEditActivity::class.java))
//            binding.ivFavorite.setImageResource(R.drawable.ic_favorite)
        }
    }


    private fun goToLogin(){
        startActivity(Intent(this, LoginActivity::class.java))
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.exit -> {
                viewModel.logout()
                this.finish()
                goToLogin()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}