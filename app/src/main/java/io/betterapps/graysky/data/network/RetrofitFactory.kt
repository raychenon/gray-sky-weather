package io.betterapps.graysky.data.network

import io.betterapps.graysky.const.GlobalConstants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitFactory {

    companion object {

        fun createRetrofit(client: OkHttpClient): Retrofit {
            return Retrofit.Builder()
                .baseUrl(GlobalConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }

        fun weatherService(client: OkHttpClient): OpenWeatherMapService {
            return createRetrofit(client).create(OpenWeatherMapService::class.java)
        }
    }
}
