package com.example.ioasys.service

import com.example.ioasys.service.listeners.ClientListener
import com.example.ioasys.service.models.EnterpriseList
import com.example.ioasys.service.models.LoginParameters
import com.example.ioasys.service.remote.ClientService
import com.example.ioasys.service.remote.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ClientRepository {

    private val mRemote = RetrofitClient.createService(ClientService::class.java)

    fun loginValidation(email: String, password: String, listener: ClientListener) {
        val call = mRemote.login(
            LoginParameters(
                email = email,
                password = password
            )
        )
        callBack (call, listener)
    }

    fun getEnterpriseInfo(str: String, listener: ClientListener) {
        val call = mRemote.info(null, str)
        callBack(call, listener)
    }

    private fun callBack(call: Call<EnterpriseList>, listener: ClientListener) {
        call.enqueue(object : Callback<EnterpriseList> {
            override fun onFailure(call: Call<EnterpriseList>, t: Throwable) {
                listener.onFailure(t.message.toString(), 404)
            }

            override fun onResponse(call: Call<EnterpriseList>, response: Response<EnterpriseList>) {
                response.let {
                    when {
                        response.isSuccessful -> {
                            listener.onSuccess(it, response.code())
                        }
                        else -> {
                            listener.onFailure(response.errorBody()!!.string(), response.code())
                        }
                    }
                }
            }

        })
    }
}