package ar.com.ericpennachini.fashiondog.app.data.datasource

import ar.com.ericpennachini.fashiondog.app.data.datasource.dto.CustomerDTO
import ar.com.ericpennachini.fashiondog.app.data.service.api.FashionDogService

class RemoteDataSource(
    service: FashionDogService
) : DataSource {
    override suspend fun addCustomer(customerDTO: CustomerDTO) {
        TODO("Not yet implemented")
    }

    override suspend fun getCustomer(id: Long): CustomerDTO? {
        TODO("Not yet implemented")
    }

    override suspend fun getAllCustomers(): List<CustomerDTO> {
        TODO("Not yet implemented")
    }

    override suspend fun searchCustomers(pattern: String) {
        TODO("Not yet implemented")
    }

    override suspend fun getCustomerCount(): Int {
        TODO("Not yet implemented")
    }

    override suspend fun deleteCustomer(customerDTO: CustomerDTO) {
        TODO("Not yet implemented")
    }

    override suspend fun editCustomer(customerDTO: CustomerDTO) {
        TODO("Not yet implemented")
    }
}