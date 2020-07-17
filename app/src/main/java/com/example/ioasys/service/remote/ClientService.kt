package com.example.ioasys.service.remote

import com.example.ioasys.service.models.EnterpriseList
import com.example.ioasys.service.models.LoginParameters
import com.example.ioasys.service.constants.IoasysConstants.ServiceConstants.INFO_URL
import com.example.ioasys.service.constants.IoasysConstants.ServiceConstants.LOGIN_URL
import retrofit2.Call
import retrofit2.http.*

interface ClientService {

    @POST(LOGIN_URL)
    fun login(@Body request: LoginParameters): Call<EnterpriseList>

    @GET(INFO_URL)
    fun info (@Query("enterprise_types") search: String?, @Query("name") name: String): Call<EnterpriseList>

}