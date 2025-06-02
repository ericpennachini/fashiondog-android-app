package ar.com.ericpennachini.fashiondog.app.di

import android.content.Context
import ar.com.ericpennachini.fashiondog.app.data.datasource.DataSource
import ar.com.ericpennachini.fashiondog.app.data.datasource.RemoteDataSource
import ar.com.ericpennachini.fashiondog.app.data.datasource.RoomDataSource
import ar.com.ericpennachini.fashiondog.app.data.service.api.FashionDogService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    fun provideRoomDataSource(
        context: Context
    ): DataSource = RoomDataSource(context)

    @Provides
    fun providesRemoteDataSource(
        service: FashionDogService
    ) = RemoteDataSource(service)

}
