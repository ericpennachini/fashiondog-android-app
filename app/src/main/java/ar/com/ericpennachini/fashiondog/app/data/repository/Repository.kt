package ar.com.ericpennachini.fashiondog.app.data.repository

import ar.com.ericpennachini.fashiondog.app.domain.model.Customer

interface Repository {

    suspend fun addCustomer(customer: Customer)

    suspend fun getCustomer(id: Long): Customer?

    suspend fun getAllCustomers(): List<Customer>

    suspend fun getCustomerCount(): Int

    suspend fun deleteCustomer(customer: Customer)

}