package ar.com.ericpennachini.fashiondog.app.data.datasource

import android.content.Context
import android.util.Log
import ar.com.ericpennachini.fashiondog.app.TAG
import ar.com.ericpennachini.fashiondog.app.data.datasource.dto.AddressDTO
import ar.com.ericpennachini.fashiondog.app.data.datasource.dto.CustomerDTO
import ar.com.ericpennachini.fashiondog.app.data.datasource.dto.PetDTO
import ar.com.ericpennachini.fashiondog.app.data.datasource.dto.PhoneDTO
import ar.com.ericpennachini.fashiondog.app.data.datasource.mapper.Mapper
import ar.com.ericpennachini.fashiondog.app.data.service.db.FashionDogDatabase
import ar.com.ericpennachini.fashiondog.app.data.service.db.entity.AddressEntity
import ar.com.ericpennachini.fashiondog.app.data.service.db.entity.CustomerEntity
import ar.com.ericpennachini.fashiondog.app.data.service.db.entity.PetEntity
import ar.com.ericpennachini.fashiondog.app.data.service.db.entity.PhoneEntity
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.coroutineContext

class RoomDataSource @Inject constructor(
    @ApplicationContext context: Context
) : DataSource {

    private val customerDao = FashionDogDatabase.getInstance(context).customerDao()

    override suspend fun addCustomer(customerDTO: CustomerDTO) {
        val customerEntity = with(customerDTO) {
            CustomerEntity(id, firstName, lastName, description, isFromNeighborhood, email)
        }
        customerDao.addCustomerEntity(customerEntity)
        val lastId = customerDao.getLastId()
        val addressEntity = with(customerDTO.address) {
            AddressEntity(id, street, number, city, province, country, description, lastId)
        }
        val petEntityList = customerDTO.pets.map {
            PetEntity(it.id, it.name, it.race, it.size, it.gender, it.behaviour, it.extraDetails, lastId)
        }
        val phoneEntityList = customerDTO.phones.map {
            PhoneEntity(it.id, it.number, it.type, lastId)
        }
        customerDao.addAddressFromCustomer(addressEntity)
        customerDao.addPetsFromCustomer(petEntityList)
        customerDao.addPhonesFromCustomer(phoneEntityList)
    }

    override suspend fun getCustomer(id: Long): CustomerDTO? {
        val customer = customerDao.getCustomerEntity(id)
        return customer?.let {
            val address = customerDao.getAddressByCustomer(id)
            val pets = customerDao.getPetsByCustomer(id)
            val phones = customerDao.getPhonesByCustomer(id)
            getCustomerDTO(it, address, pets, phones)
        }
    }

    private fun getCustomerDTO(
        customerEntity: CustomerEntity,
        addressEntity: AddressEntity,
        petEntityList: List<PetEntity>,
        phoneEntityList: List<PhoneEntity>
    ) = CustomerDTO(
        id = customerEntity.id,
        firstName = customerEntity.firstName,
        lastName = customerEntity.lastName,
        description = customerEntity.description,
        email = customerEntity.email,
        isFromNeighborhood = customerEntity.isFromNeighborhood,
        address = with(addressEntity) {
            AddressDTO(id, street, number, city, province, country, description)
        },
        pets = petEntityList.map { pet ->
            with(pet) {
                PetDTO(id, name, race, size, gender, behaviour, extraDetails)
            }
        },
        phones = phoneEntityList.map { phone ->
            with(phone) {
                PhoneDTO(id, number, type)
            }
        }
    )

    override suspend fun getAllCustomers(): List<CustomerDTO> {
        val customers = customerDao.getAllCustomerEntities()
        val customerDTOList = customers.map {
            val address = customerDao.getAddressByCustomer(it.id)
            val pets = customerDao.getPetsByCustomer(it.id)
            val phones = customerDao.getPhonesByCustomer(it.id)
            getCustomerDTO(it, address, pets, phones)
        }
        return customerDTOList
    }

    override suspend fun getCustomerCount(): Int {
        return customerDao.getCustomerCount()
    }

    override suspend fun deleteCustomer(customerDTO: CustomerDTO) {
//        customerDao.deleteCustomerEntity(
////        customerEntity = mapper.mapToEntity(customerDTO)
//        )
    }

    override suspend fun editCustomer(customerDTO: CustomerDTO) {
        // To-do sometime
    }

}
