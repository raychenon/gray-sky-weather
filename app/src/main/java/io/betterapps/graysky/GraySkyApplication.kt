package io.betterapps.graysky

import android.app.Application
import io.betterapps.graysky.di.allModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.core.context.startKoin
import timber.log.Timber
import timber.log.Timber.DebugTree

class GraySkyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }

        startKoin {

            // use the Android context given there
            androidContext(this@GraySkyApplication)

            // load properties from assets/koin.properties file
            androidFileProperties()

            modules(allModules)
        }
    }
}
