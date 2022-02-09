package ar.com.ericpennachini.fashiondog.app.data.repository

import ar.com.ericpennachini.fashiondog.app.data.datasource.DataSource
import ar.com.ericpennachini.fashiondog.app.data.datasource.RoomDataSource
import ar.com.ericpennachini.fashiondog.app.data.datasource.dto.CustomerDTO
import ar.com.ericpennachini.fashiondog.app.domain.mapper.DomainMapper
import ar.com.ericpennachini.fashiondog.app.domain.model.Customer

class FashionDogRepository(
    private val dataSource: DataSource,
    private val mapper: DomainMapper<CustomerDTO, Customer>
) : Repository {
    override suspend fun addCustomer(customer: Customer) {
        dataSource.addCustomer(mapper.mapToDTO(customer))
    }

    override suspend fun getCustomer(id: Long): Customer? {
        val result = dataSource.getCustomer(id)
        return result?.let { mapper.mapToModel(result) }
    }

    override suspend fun getAllCustomers(): List<Customer> {
        return dataSource.getAllCustomers().map { mapper.mapToModel(it) }
    }

    override suspend fun getCustomerCount(): Int {
        return dataSource.getCustomerCount()
    }

    override suspend fun deleteCustomer(customer: Customer) {
        dataSource.deleteCustomer(mapper.mapToDTO(customer))
    }
}