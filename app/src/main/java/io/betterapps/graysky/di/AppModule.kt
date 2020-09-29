package io.betterapps.graysky.di

import android.app.Application
import io.betterapps.graysky.data.api.ApiHelper
import io.betterapps.graysky.data.db.entities.LocationDao
import io.betterapps.graysky.data.db.entities.LocationDatabase
import io.betterapps.graysky.data.network.OkHttpClientWithCache
import io.betterapps.graysky.data.network.OpenWeatherMapService
import io.betterapps.graysky.data.network.RetrofitFactory
import io.betterapps.graysky.repository.LocationRepository
import io.betterapps.graysky.repository.LocationRepositoryImpl
import io.betterapps.graysky.repository.WeatherRepository
import io.betterapps.graysky.repository.WeatherRepositoryImpl
import io.betterapps.graysky.ui.main.MainViewModel
import io.betterapps.graysky.ui.weatherforecast.WeatherViewModel
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val networkModule = module {
    single<OkHttpClient> { OkHttpClientWithCache.instance(get() as Application) }
    single<OpenWeatherMapService> { RetrofitFactory.weatherService(get() as OkHttpClient) }
    factory<ApiHelper> { ApiHelper(get()) }
    single<WeatherRepository> {
        WeatherRepositoryImpl(
            get() as ApiHelper,
            mutableMapOf()
        )
    }
}

val dataModule = module {
    single<LocationDatabase> { LocationDatabase.getDatabase(androidContext()) }
    fun provideDao(database: LocationDatabase): LocationDao {
        return database.locationDao()
    }
    single<LocationDao> { provideDao(get()) }
    single<LocationRepository> {
        LocationRepositoryImpl(
            get() as LocationDao
        )
    }
}

val mvvmModule = module {
    viewModel { WeatherViewModel(get() as WeatherRepository) }
    viewModel { MainViewModel() }
}

val allModules = networkModule + dataModule + mvvmModule
