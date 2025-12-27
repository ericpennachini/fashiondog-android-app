package ar.com.ericpennachini.fashiondog.app.di

import ar.com.ericpennachini.fashiondog.app.BuildConfig
import ar.com.ericpennachini.fashiondog.app.data.datasource.DataSource
import ar.com.ericpennachini.fashiondog.app.data.datasource.MockDataSource
import ar.com.ericpennachini.fashiondog.app.data.datasource.RoomDataSource
import org.koin.dsl.module

val dataSourceModule = module {
    single<DataSource> {
        if (BuildConfig.IS_MOCK_ENABLED) {
            MockDataSource(get())
        } else {
            // TODO: acá se debería utilizar el RemoteDataSource, o tener lógica de usar el de Room para levantar cache
            RoomDataSource(get())
        }
    }
}
