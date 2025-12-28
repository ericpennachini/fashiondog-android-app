package ar.com.ericpennachini.fashiondog.app.data.datasource

import android.content.Context
import ar.com.ericpennachini.fashiondog.app.R
import ar.com.ericpennachini.fashiondog.app.data.datasource.dto.CustomerDTO
import ar.com.ericpennachini.fashiondog.app.replace
import ar.com.ericpennachini.fashiondog.app.ENTITY_CUSTOMER
import ar.com.ericpennachini.fashiondog.app.ENTITY_PET
import ar.com.ericpennachini.fashiondog.app.ENTITY_PHONE
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.delay
import java.util.concurrent.atomic.AtomicLong
import kotlin.time.Duration

class MockDataSource(
    context: Context
) : DataSource {

    private val customers: MutableSet<CustomerDTO> = mutableSetOf()
    private val jsonAdapter: JsonAdapter<Set<CustomerDTO>>

    private val lastCustomerId = AtomicLong(0)
    private val lastPetId = AtomicLong(0)
    private val lastPhoneId = AtomicLong(0)

    init {
        val jsonResource: String = context.resources.openRawResource(R.raw.mock).use { inputStream ->
            return@use inputStream.bufferedReader().use { it.readText() }
        }

        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        val type = Types.newParameterizedType(Set::class.java, CustomerDTO::class.java)
        jsonAdapter = moshi.adapter(type)

        customers += jsonAdapter.fromJson(jsonResource)?.toMutableSet().orEmpty()

        customers.forEach { customer ->
            lastCustomerId.set(maxOf(lastCustomerId.get(), customer.id ?: 0))
            customer.pets.forEach { lastPetId.set(maxOf(lastPetId.get(), it.id)) }
            customer.phones.forEach { lastPhoneId.set(maxOf(lastPhoneId.get(), it.id)) }
        }
    }

    override suspend fun addCustomer(customerDTO: CustomerDTO) {
        delay(DEFAULT_SIMULATED_API_CALL_DELAY)
        if (customerDTO.id == null) {
            customerDTO.id = lastCustomerId.incrementAndGet()
        }
        customers.add(customerDTO)
    }

    override suspend fun getCustomer(id: Long): CustomerDTO? {
        delay(DEFAULT_SIMULATED_API_CALL_DELAY)
        return customers.find {
            it.id == id
        }
    }

    override suspend fun getAllCustomers(): List<CustomerDTO> {
        delay(DEFAULT_SIMULATED_API_CALL_DELAY)
        return customers.toList()
    }

    override suspend fun getCustomerCount(): Int {
        delay(DEFAULT_SIMULATED_API_CALL_DELAY)
        return customers.size
    }

    override suspend fun deleteCustomer(customerDTO: CustomerDTO): Boolean {
        delay(DEFAULT_SIMULATED_API_CALL_DELAY)
        return customers.find { it.id == customerDTO.id }?.let {
            customers.remove(it)
        } ?: return false
    }

    override suspend fun editCustomer(customerDTO: CustomerDTO) {
        delay(DEFAULT_SIMULATED_API_CALL_DELAY)
        customers.replace(customerDTO) {
            it.id == customerDTO.id
        }
    }

    override suspend fun getNextId(entityName: String): Long {
        return when (entityName) {
            ENTITY_CUSTOMER -> lastCustomerId.incrementAndGet()
            ENTITY_PET -> lastPetId.incrementAndGet()
            ENTITY_PHONE -> lastPhoneId.incrementAndGet()
            else -> 0L
        }
    }

    companion object {
        private const val DEFAULT_SIMULATED_API_CALL_DELAY: Long = 1000
    }
}