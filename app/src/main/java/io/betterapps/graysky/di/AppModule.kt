package io.betterapps.graysky.di

import io.betterapps.graysky.data.api.ApiHelper
import io.betterapps.graysky.data.network.OpenWeatherMapService
import io.betterapps.graysky.data.network.RetrofitFactory
import io.betterapps.graysky.repository.WeatherRepository
import io.betterapps.graysky.repository.WeatherRepositoryImpl
import io.betterapps.graysky.ui.main.MainViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val networkModule = module {
    single<OpenWeatherMapService> { RetrofitFactory.weatherService }
    single<ApiHelper> { ApiHelper(get()) }
    single<WeatherRepository> { WeatherRepositoryImpl(get() as ApiHelper, mutableMapOf()) }
}

val mvvmModule = module {
    viewModel { MainViewModel(get() as WeatherRepository) }
}

val allModules = networkModule + mvvmModule
