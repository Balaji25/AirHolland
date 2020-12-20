package com.bg.airholland.network

import com.bg.airholland.model.obj.EventObj
import com.bg.airholland.utils.AppConstant

import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.POST
import java.util.*

interface ApiEndPoint {

   // @FormUrlEncoded
    @GET("dummy-response.json")
    suspend fun getRosterList() : Response<ArrayList<EventObj>>



    companion object{
        operator fun invoke(
            networkConnectionInterceptor: NetworkConnectionInterceptor
        ) : ApiEndPoint {

            val okkHttpclient = OkHttpClient.Builder()
                .addInterceptor(networkConnectionInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okkHttpclient)
                .baseUrl(AppConstant.CONST_APP_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(StringConverterFactory.create())
                .build()
                .create(ApiEndPoint::class.java)
        }
    }

}

