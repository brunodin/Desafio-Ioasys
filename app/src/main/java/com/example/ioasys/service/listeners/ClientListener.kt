package com.example.ioasys.service.listeners

import com.example.ioasys.service.models.EnterpriseList
import okhttp3.Headers
import retrofit2.Response

interface ClientListener{
    fun onSuccess(model: Response<EnterpriseList>, code: Int)
    fun onFailure(model: String, code: Int)
}