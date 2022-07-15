package br.com.zup.exercicio_aulafirebase_01.ui.login.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import br.com.zup.exercicio_aulafirebase_01.ui.main.view.MainActivity
import br.com.zup.exercicio_aulafirebase_01.ui.registro.view.RegistroActivity
import br.com.zup.exercicio_aulafirebase_01.Utils.USER_KEY
import br.com.zup.exercicio_aulafirebase_01.databinding.ActivityLoginBinding
import br.com.zup.exercicio_aulafirebase_01.domain.model.user.User
import br.com.zup.exercicio_aulafirebase_01.ui.login.viewmodel.LoginViewModel
import com.google.android.material.snackbar.Snackbar

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private val viewModel: LoginViewModel by lazy {
        ViewModelProvider(this)[LoginViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvRegisterNow?.setOnClickListener{
            goToRegister()
        }

        binding.bvLogin?.setOnClickListener {
            val user = getDataUser()
            viewModel.validateDataUser(user)
        }

        initObservers()
    }

    private fun getDataUser(): User {
        return User(
            email = binding.etUserEmail?.text.toString(),
            password = binding.etPassword?.text.toString()
        )
    }

    private fun goToRegister() {
        startActivity(Intent(this, RegistroActivity::class.java))
    }

    private fun initObservers() {
        viewModel.loginState.observe(this) {
            goToHome(it)
        }

        viewModel.errorState.observe(this) {
            Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
        }
    }

    private fun goToHome(user: User) {
        val intent = Intent(this, MainActivity::class.java).apply {
            putExtra(USER_KEY, user)
        }
        startActivity(intent)
    }
}