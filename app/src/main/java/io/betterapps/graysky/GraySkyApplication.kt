package io.betterapps.graysky

import android.app.Application
import io.betterapps.graysky.di.allModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.core.context.startKoin

class GraySkyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {

            // use the Android context given there
            androidContext(this@GraySkyApplication)

            // load properties from assets/koin.properties file
            androidFileProperties()

            modules(allModules)
        }
    }
}
