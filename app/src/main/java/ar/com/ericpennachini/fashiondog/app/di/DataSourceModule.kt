package ar.com.ericpennachini.fashiondog.app.di

import android.content.Context
import ar.com.ericpennachini.fashiondog.app.BuildConfig
import ar.com.ericpennachini.fashiondog.app.data.datasource.DataSource
import ar.com.ericpennachini.fashiondog.app.data.datasource.MockDataSource
import ar.com.ericpennachini.fashiondog.app.data.datasource.RoomDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Singleton
    @Provides
    fun provideDataSource(
        context: Context
    ): DataSource {
        return if (BuildConfig.IS_MOCK_ENABLED) {
            MockDataSource(context)
        } else {
            // TODO: acá se debería utilizar el RemoteDataSource, o tener lógica de usar el de Room para levantar cache
            RoomDataSource(context)
        }
    }

}
