package io.betterapps.graysky.di

import android.app.Application
import io.betterapps.graysky.data.api.ApiHelper
import io.betterapps.graysky.data.network.OkHttpClientWithCache
import io.betterapps.graysky.data.network.OpenWeatherMapService
import io.betterapps.graysky.data.network.RetrofitFactory
import io.betterapps.graysky.repository.WeatherRepository
import io.betterapps.graysky.repository.WeatherRepositoryImpl
import io.betterapps.graysky.ui.main.MainViewModel
import io.betterapps.graysky.ui.weatherforecast.WeatherViewModel
import okhttp3.OkHttpClient
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val networkModule = module {
    single<OkHttpClient> { OkHttpClientWithCache.instance(get() as Application) }
    single<OpenWeatherMapService> { RetrofitFactory.weatherService(get() as OkHttpClient) }
    single<ApiHelper> { ApiHelper(get()) }
    single<WeatherRepository> { WeatherRepositoryImpl(get() as ApiHelper, mutableMapOf()) }

}

val mvvmModule = module {
    viewModel { WeatherViewModel(get() as WeatherRepository) }
    viewModel { MainViewModel() }
}

val allModules = networkModule + mvvmModule
