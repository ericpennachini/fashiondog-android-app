package ar.com.ericpennachini.fashiondog.app.di

import android.content.Context
import ar.com.ericpennachini.fashiondog.app.data.datasource.DataSource
import ar.com.ericpennachini.fashiondog.app.data.datasource.RoomDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    fun provideDataSource(
        context: Context
    ): DataSource = RoomDataSource(context)

}
