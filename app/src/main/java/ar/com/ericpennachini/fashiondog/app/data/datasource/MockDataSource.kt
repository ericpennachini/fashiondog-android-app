package ar.com.ericpennachini.fashiondog.app.data.datasource

import android.content.Context
import ar.com.ericpennachini.fashiondog.app.R
import ar.com.ericpennachini.fashiondog.app.data.datasource.dto.CustomerDTO
import ar.com.ericpennachini.fashiondog.app.replace
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import javax.inject.Inject
import kotlin.time.Duration

class MockDataSource @Inject constructor(
    @ApplicationContext context: Context
) : DataSource {

    private val customers: MutableSet<CustomerDTO> = mutableSetOf()
    private val jsonAdapter: JsonAdapter<Set<CustomerDTO>>

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
    }

    override suspend fun addCustomer(customerDTO: CustomerDTO) {
        delay(DEFAULT_SIMULATED_API_CALL_DELAY)
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

    override suspend fun deleteCustomer(customerDTO: CustomerDTO) {
        delay(DEFAULT_SIMULATED_API_CALL_DELAY)
        customers.find { it.id == customerDTO.id }?.let {
            customers.remove(it)
        }
    }

    override suspend fun editCustomer(customerDTO: CustomerDTO) {
        delay(DEFAULT_SIMULATED_API_CALL_DELAY)
        customers.replace(customerDTO) {
            it.id == customerDTO.id
        }
    }

    companion object {
        private const val DEFAULT_SIMULATED_API_CALL_DELAY: Long = 1000
    }
}