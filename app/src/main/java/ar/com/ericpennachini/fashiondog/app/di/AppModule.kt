package ar.com.ericpennachini.fashiondog.app.di

import ar.com.ericpennachini.fashiondog.app.BaseApplication
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val appModule = module {
    single<BaseApplication> {
        androidApplication() as BaseApplication
    }
}
