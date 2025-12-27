package ar.com.ericpennachini.fashiondog.app

import android.app.Application
import ar.com.ericpennachini.fashiondog.app.di.appModule
import ar.com.ericpennachini.fashiondog.app.di.dataSourceModule
import ar.com.ericpennachini.fashiondog.app.di.repositoryModule
import ar.com.ericpennachini.fashiondog.app.di.viewModelModule
import com.google.android.material.color.DynamicColors
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@BaseApplication)
            modules(
                appModule,
                dataSourceModule,
                repositoryModule,
                viewModelModule
            )
        }

        DynamicColors.applyToActivitiesIfAvailable(this)
    }

}
