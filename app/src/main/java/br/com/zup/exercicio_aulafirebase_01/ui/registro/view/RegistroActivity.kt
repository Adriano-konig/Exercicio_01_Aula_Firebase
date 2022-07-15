package br.com.zup.exercicio_aulafirebase_01.ui.registro.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModelProvider
import br.com.zup.exercicio_aulafirebase_01.R
import br.com.zup.exercicio_aulafirebase_01.Utils.USER_KEY
import br.com.zup.exercicio_aulafirebase_01.databinding.ActivityRegistroBinding
import br.com.zup.exercicio_aulafirebase_01.domain.model.user.User
import br.com.zup.exercicio_aulafirebase_01.ui.main.view.MainActivity
import br.com.zup.exercicio_aulafirebase_01.ui.registro.viewmodel.RegistroViewModel
import com.google.android.material.snackbar.Snackbar

class RegistroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistroBinding

    private val viewModel: RegistroViewModel by lazy {
        ViewModelProvider(this)[RegistroViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)

    binding.btRegister.setOnClickListener {
        val user = getDataUser()
        viewModel.validateDataUser(user)
    }

    initObservers()
}

private fun getDataUser(): User {
    return User(
        name = binding.etNameRegister.text.toString(),
        email = binding.etEmailRegister.text.toString(),
        password = binding.etPasswordRegister.text.toString()
    )
}

private fun initObservers() {
    viewModel.registerState.observe(this) {
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