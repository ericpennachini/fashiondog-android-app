package ar.com.ericpennachini.fashiondog.app.data.datasource

import ar.com.ericpennachini.fashiondog.app.data.datasource.dto.CustomerDTO

interface DataSource {

    suspend fun addCustomer(customerDTO: CustomerDTO)

    suspend fun getCustomer(id: Long): CustomerDTO?

    suspend fun getAllCustomers(): List<CustomerDTO>

    suspend fun getCustomerCount(): Int

    suspend fun deleteCustomer(customerDTO: CustomerDTO)

    suspend fun editCustomer(customerDTO: CustomerDTO)

}