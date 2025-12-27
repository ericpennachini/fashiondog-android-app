package ar.com.ericpennachini.fashiondog.app.di

import android.content.Context
import ar.com.ericpennachini.fashiondog.app.BaseApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {
    single<BaseApplication> {
        androidContext() as BaseApplication
    }

    single<Context> {
        androidContext()
    }
}
