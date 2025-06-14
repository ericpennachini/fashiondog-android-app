package ar.com.ericpennachini.fashiondog.app.di

import ar.com.ericpennachini.fashiondog.app.data.datasource.DataSource
import ar.com.ericpennachini.fashiondog.app.data.datasource.RoomDataSource
import ar.com.ericpennachini.fashiondog.app.data.repository.FashionDogRepository
import ar.com.ericpennachini.fashiondog.app.data.repository.Repository
import ar.com.ericpennachini.fashiondog.app.domain.mapper.CustomerMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideRepository(
        dataSource: DataSource,
        mapper: CustomerMapper
    ): Repository = FashionDogRepository(dataSource, mapper)

    @Singleton
    @Provides
    fun provideDomainMapper() = CustomerMapper

}
