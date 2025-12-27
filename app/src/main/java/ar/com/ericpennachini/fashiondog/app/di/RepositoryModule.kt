package ar.com.ericpennachini.fashiondog.app.di

import ar.com.ericpennachini.fashiondog.app.data.repository.FashionDogRepository
import ar.com.ericpennachini.fashiondog.app.data.repository.Repository
import ar.com.ericpennachini.fashiondog.app.domain.mapper.CustomerMapper
import org.koin.dsl.module

val repositoryModule = module {
    single<Repository> {
        FashionDogRepository(get(), get())
    }

    single {
        CustomerMapper
    }
}
