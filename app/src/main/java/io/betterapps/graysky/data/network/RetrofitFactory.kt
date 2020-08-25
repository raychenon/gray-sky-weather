package io.betterapps.graysky.data.network

import io.betterapps.graysky.const.GlobalConstants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitFactory {

    private val client = OkHttpClient.Builder().build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(GlobalConstants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    val weatherService: OpenWeatherMapService = retrofit.create(OpenWeatherMapService::class.java)
}
