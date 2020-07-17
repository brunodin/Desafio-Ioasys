package com.example.ioasys.service.remote

import com.example.ioasys.service.constants.IoasysConstants.ServiceClient.ACCESS_TOKEN
import com.example.ioasys.service.constants.IoasysConstants.ServiceClient.CLIENT
import com.example.ioasys.service.constants.IoasysConstants.ServiceClient.UID
import com.example.ioasys.service.constants.IoasysConstants.ServiceConstants.BASE_URL
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient private constructor() {

    companion object {

        private var mUid = ""
        private var mClient = ""
        private var mAccessToken = ""
        private lateinit var retrofit: Retrofit

        private fun getRetrofitInstance(): Retrofit {
            val httpClient =  OkHttpClient.Builder()
            httpClient.addInterceptor(object : Interceptor {
                override fun intercept(chain: Interceptor.Chain): Response {
                    val request =
                        chain.request()
                            .newBuilder()
                            .addHeader(UID, mUid)
                            .addHeader(ACCESS_TOKEN, mAccessToken)
                            .addHeader(CLIENT, mClient)
                            .build()
                    return chain.proceed(request)
                }
            })
            if (!Companion::retrofit.isInitialized) {
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(httpClient.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit
        }

        fun addHeaders(uId: String, client: String, token: String) {
            mUid = uId
            mClient = client
            mAccessToken = token
        }

        fun createService(serviceClass: Class<ClientService>): ClientService {
            return getRetrofitInstance()
                .create(serviceClass)
        }
    }
}