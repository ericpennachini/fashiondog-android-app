package ar.com.ericpennachini.fashiondog.app.data.datasource

import ar.com.ericpennachini.fashiondog.app.data.datasource.dto.CustomerDTO

interface DataSource {

    suspend fun addCustomer(customer: CustomerDTO)

    suspend fun getCustomer(id: Long): CustomerDTO?

    suspend fun getAllCustomers(): List<CustomerDTO>

    suspend fun deleteCustomer(customer: CustomerDTO)

}