package com.example.ioasys.ui.viewholder

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout
import android.view.View
import com.example.ioasys.R
import com.example.ioasys.service.constants.IoasysConstants.KeyWords.KEY
import com.example.ioasys.service.models.EnterpriseInfo
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        volta.setOnClickListener(this)
        mostrar()
    }
    //Função encarregada dos eventos de clique.
    override fun onClick(v: View?) {
        val id = v!!.id
        if (id == R.id.volta) {
            onBackPressed()
            finish()
        }
    }

    //função encaregada de mostrar as informações no front-end
    @SuppressLint("WrongConstant")
    private fun mostrar() {
        val model = intent.getSerializableExtra(KEY)
        txtempresa.justificationMode = Layout.JUSTIFICATION_MODE_INTER_WORD
        txtempresa.text = (model as EnterpriseInfo).enterpriseName
        txtdescricao.text = model.description
        val url = "https://empresas.ioasys.com.br/api/v1${model.photo}"
        val picasso = Picasso.Builder(this)
            .build()
        picasso.load(url).placeholder(R.drawable.ic_camera).into(imgempresa)
    }
}
