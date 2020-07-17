package com.example.ioasys.ui.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.ioasys.service.ClientRepository
import com.example.ioasys.service.constants.IoasysConstants.ServiceClient.ACCESS_TOKEN
import com.example.ioasys.service.constants.IoasysConstants.ServiceClient.CLIENT
import com.example.ioasys.service.constants.IoasysConstants.ServiceClient.EMAIL
import com.example.ioasys.service.constants.IoasysConstants.ServiceClient.PASSWORD
import com.example.ioasys.service.constants.IoasysConstants.ServiceClient.UID
import com.example.ioasys.service.listeners.ClientListener
import com.example.ioasys.service.listeners.ValidationListeners
import com.example.ioasys.service.local.SecuritySharedPreferences
import com.example.ioasys.service.models.EnterpriseInfo
import com.example.ioasys.service.models.EnterpriseList
import com.example.ioasys.service.remote.RetrofitClient
import retrofit2.Response

class InfoViewModel(application: Application) : AndroidViewModel(application) {

    private val mClientRepository = ClientRepository()
    private val mSecurityPreferences = SecuritySharedPreferences(application)

    //Live Data Variaveis
    private var mModel = MutableLiveData<ValidationListeners>()
    val model: LiveData<ValidationListeners> = mModel
    //Valida os Headers
    private fun validateHeaders() {
        RetrofitClient.addHeaders(
            mSecurityPreferences.getString(UID),
            mSecurityPreferences.getString(CLIENT),
            mSecurityPreferences.getString(ACCESS_TOKEN)
        )
    }
    //Função realiza a pesquisa.
    fun getEnterpriseList(string: String) {
        validateHeaders()

        mClientRepository.getEnterpriseInfo(string, object : ClientListener {

            override fun onSuccess(model: Response<EnterpriseList>, code: Int) {
                mModel.value = ValidationListeners(model.body()?.empresas ?: listOf(), code)
            }

            override fun onFailure(model: String, code: Int) {
                if (code == 401) {
                    LoginViewModel(getApplication()).login(
                        mSecurityPreferences.getString(EMAIL),
                        mSecurityPreferences.getString(PASSWORD)
                    )
                    validateHeaders()
                    mModel.value = ValidationListeners(int = code)
                } else {
                    mModel.value = ValidationListeners(int = code)
                }

            }
        })
    }
}