package ar.com.ericpennachini.fashiondog.app.data.service.db.dao

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import ar.com.ericpennachini.fashiondog.app.data.service.db.entity.*

@Dao
interface CustomerDao {

    @Insert(onConflict = REPLACE)
    suspend fun addCustomerEntity(customerEntity: CustomerEntity)

    @Insert(onConflict = REPLACE)
    suspend fun addPetsFromCustomer(pets: List<PetEntity>)

    @Insert(onConflict = REPLACE)
    suspend fun addPhonesFromCustomer(phones: List<PhoneEntity>)

    @Insert(onConflict = REPLACE)
    suspend fun addAddressFromCustomer(address: AddressEntity)

    @Query("SELECT * FROM customer WHERE id = :id")
    suspend fun getCustomerEntity(id: Long): CustomerEntity?

    @Transaction
    @Query("SELECT * FROM customer WHERE id = :id")
    suspend fun getCustomerWithAddress(id: Long): AddressCustomerEntity

    @Transaction
    @Query("SELECT * FROM customer WHERE id = :id")
    suspend fun getCustomerWithPets(id: Long): PetCustomerEntity

    @Transaction
    @Query("SELECT * FROM customer WHERE id = :id")
    suspend fun getCustomerWithPhones(id: Long): PhoneCustomerEntity

    @Query("SELECT * FROM customer")
    suspend fun getAllCustomerEntities(): List<CustomerEntity>

    @Delete
    suspend fun deleteCustomerEntity(customerEntity: CustomerEntity)

}