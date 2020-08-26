package io.betterapps.graysky

import android.app.Application
import io.betterapps.graysky.data.api.ApiHelper
import io.betterapps.graysky.data.network.RetrofitFactory
import io.betterapps.graysky.ui.main.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.viewmodel.compat.ScopeCompat.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.java.KoinJavaComponent.get

class GraySkyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {

            // use the Android context given there
            androidContext(this@GraySkyApplication)

            // load properties from assets/koin.properties file
            androidFileProperties()

            modules(networkModule)
        }
    }

    companion object {
        val networkModule = module {
            //viewModel { MainViewModel(get()) }
            single { ApiHelper(RetrofitFactory.weatherService) }
        }
    }
}