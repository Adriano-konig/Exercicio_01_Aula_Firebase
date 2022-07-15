package br.com.zup.exercicio_aulafirebase_01.ui.main.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import br.com.zup.exercicio_aulafirebase_01.R
import br.com.zup.exercicio_aulafirebase_01.databinding.ActivityMainBinding
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
        viewModel.getUserName()
        showUserData()
    }

    private fun showUserData(){
        val name = viewModel.getUserEmail()
        binding.nome.text = "$name"
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