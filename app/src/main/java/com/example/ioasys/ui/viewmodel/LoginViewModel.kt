package com.example.ioasys.ui.viewmodel

import android.app.Application
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
import com.example.ioasys.service.local.SecuritySharedPreferences
import com.example.ioasys.service.models.EnterpriseList
import retrofit2.Response

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val  mClientRepository = ClientRepository()
    private val mSecurityPreferences = SecuritySharedPreferences(application)

    private var  mVariable = MutableLiveData<Int>()
    val variable: LiveData<Int> = mVariable

    fun login(email: String, password: String){
        mClientRepository.loginValidation(email, password, object : ClientListener {
            override fun onSuccess(model: Response<EnterpriseList>, code: Int) {
                mSecurityPreferences.storeString(UID, model.headers()["uid"].toString())
                mSecurityPreferences.storeString(CLIENT, model.headers()["client"].toString())
                mSecurityPreferences.storeString(ACCESS_TOKEN, model.headers()["access-token"].toString())
                mSecurityPreferences.storeString(EMAIL, email)
                mSecurityPreferences.storeString(PASSWORD, password)
                mVariable.value = code
            }

            override fun onFailure(model: String, code: Int) {
                mVariable.value = code
            }
        })
    }
}