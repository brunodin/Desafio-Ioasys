package com.example.ioasys.ui.viewholder


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.ioasys.R
import com.example.ioasys.service.local.SecuritySharedPreferences
import com.example.ioasys.ui.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.activity_main.*


class LoginActivity : AppCompatActivity(), View.OnClickListener {

    //Variaveis.
    private lateinit var mSecurityPreferences: SecuritySharedPreferences
    private lateinit var mLoginActivity: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Instanciando as variaveis.
        mSecurityPreferences = SecuritySharedPreferences(this)
        mLoginActivity = ViewModelProvider(this).get(LoginViewModel::class.java)
        //Funções de controle
        listener()
        observers()
    }

    //Função encarregada de receber os observers da view model
    private fun observers() {
        mLoginActivity.variable.observe(this, Observer { result ->
            if (result == 200) {
                val i = Intent(this, InfoActivity::class.java)
                //Finaliza o processo de loading
                progress.visibility = View.GONE
                window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                entrar.isEnabled = true
                startActivity(i)
                finish()
            } else if (result == 401) {
                falha.visibility = View.VISIBLE
                entrar.setBackgroundColor(getColor(R.color.charcoal_grey))
                layoutEmail.errorIconDrawable =
                    getDrawable(com.google.android.material.R.drawable.mtrl_ic_error)
                layoutEmail.setErrorIconTintList(getColorStateList(R.color.colorAccent))
                layoutPassword.errorIconDrawable =
                    getDrawable(com.google.android.material.R.drawable.mtrl_ic_error)
                layoutPassword.setErrorIconTintList(getColorStateList(R.color.colorAccent))
                //Finaliza o processo de loading
                progress.visibility = View.GONE
                window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                entrar.isEnabled = true
            } else {
                //Finaliza o processo de loading
                progress.visibility = View.GONE
                window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                entrar.isEnabled = true

                Toast.makeText(this, "Houve um erro inesperado, por favor tente mais tarde.", Toast.LENGTH_LONG).show()
            }
        })
    }

    //Função encarregada de receber os eventos de clique no front-end
    private fun listener() {
        entrar.setOnClickListener(this)
    }

    //Função encarregada dos eventos de clique.
    override fun onClick(v: View?) {
        val id = v!!.id
        if (id == R.id.entrar) {
            val email = emailtxt.text.toString()//"testeapple@ioasys.com.br"//
            val senha = password.text.toString()//"12341234"//
            entrar.isEnabled = false
            progress.visibility = View.VISIBLE
            window.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            )
            mLoginActivity.login(email, senha)
        }
    }
}
