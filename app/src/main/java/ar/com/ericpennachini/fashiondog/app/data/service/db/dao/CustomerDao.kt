package ar.com.ericpennachini.fashiondog.app.data.service.db.dao

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import ar.com.ericpennachini.fashiondog.app.data.service.db.entity.*

@Dao
interface CustomerDao {

    @Insert(onConflict = REPLACE)
    suspend fun addCustomerEntity(customerEntity: CustomerEntity)

    @Query("SELECT max(id) FROM customer")
    suspend fun getLastId(): Long

    @Insert(onConflict = REPLACE)
    suspend fun addPetsFromCustomer(pets: List<PetEntity>)

    @Insert(onConflict = REPLACE)
    suspend fun addPhonesFromCustomer(phones: List<PhoneEntity>)

    @Insert(onConflict = REPLACE)
    suspend fun addAddressFromCustomer(address: AddressEntity)

    @Query("SELECT * FROM customer WHERE id = :id")
    suspend fun getCustomerEntity(id: Long): CustomerEntity?

    @Query("SELECT * FROM address WHERE addressCustomerId = :customerId")
    suspend fun getAddressByCustomer(customerId: Long): AddressEntity

    @Query("SELECT * FROM phone WHERE phoneCustomerId = :customerId")
    suspend fun getPhonesByCustomer(customerId: Long): List<PhoneEntity>

    @Query("SELECT * FROM pet WHERE petCustomerId = :customerId")
    suspend fun getPetsByCustomer(customerId: Long): List<PetEntity>

    @Query("SELECT * FROM customer")
    suspend fun getAllCustomerEntities(): List<CustomerEntity>

    @Query("SELECT count(*) FROM customer")
    suspend fun getCustomerCount(): Int

    @Query("SELECT count(*) FROM address")
    suspend fun getAddressCount(): Int

    @Delete
    suspend fun deleteCustomerEntity(customerEntity: CustomerEntity)

}