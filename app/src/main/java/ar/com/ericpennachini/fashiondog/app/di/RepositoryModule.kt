package ar.com.ericpennachini.fashiondog.app.di

import ar.com.ericpennachini.fashiondog.app.data.repository.FashionDogRepository
import ar.com.ericpennachini.fashiondog.app.data.datasource.dto.CustomerDTO
import ar.com.ericpennachini.fashiondog.app.data.repository.Repository
import ar.com.ericpennachini.fashiondog.app.domain.mapper.CustomerMapper
import ar.com.ericpennachini.fashiondog.app.domain.model.Customer
import ar.com.ericpennachini.fashiondog.app.domain.mapper.DomainMapper
import org.koin.dsl.module

val repositoryModule = module {
    single<Repository> {
        FashionDogRepository(
            dataSource = get(),
            mapper = get()
        )
    }

    single<DomainMapper<CustomerDTO, Customer>> {
        CustomerMapper
    }
}
