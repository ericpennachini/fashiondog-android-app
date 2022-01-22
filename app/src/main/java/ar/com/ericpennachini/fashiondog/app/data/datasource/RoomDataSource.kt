package ar.com.ericpennachini.fashiondog.app.data.datasource

import android.content.Context
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

class RoomDataSource(
    context: Context,
    private val mapper: Mapper<CustomerEntity, CustomerDTO>
) : DataSource {

    private val customerDao = FashionDogDatabase.getInstance(context).customerDao()

    override suspend fun addCustomer(customer: CustomerDTO) {
        val customerEntity = with(customer) {
            CustomerEntity(id, firstName, lastName, description, isFromNeighborhood, email)
        }
        val addressEntity = with(customer.address) {
            AddressEntity(id, street, number, city, province, country, description)
        }
        val petEntityList = with(customer.pets) {
            map { PetEntity(
                it.id, it.name, it.race, it.size, it.gender, it.behaviour, it.extraDetails
            ) }
        }
        val phoneEntityList = with(customer.phones) {
            map { PhoneEntity(it.id, it.number, it.type) }
        }

        customerDao.addCustomerEntity(customerEntity)
        customerDao.addAddressFromCustomer(addressEntity)
        customerDao.addPetsFromCustomer(petEntityList)
        customerDao.addPhonesFromCustomer(phoneEntityList)
    }

    override suspend fun getCustomer(id: Long): CustomerDTO? {
        val customer = customerDao.getCustomerEntity(id)
        return customer?.let {
            val address = customerDao.getCustomerWithAddress(id)
            val pets = customerDao.getCustomerWithPets(id)
            val phones = customerDao.getCustomerWithPhones(id)
            CustomerDTO(
                id = it.id,
                firstName = it.firstName,
                lastName = it.lastName,
                description = it.description,
                email = it.email,
                isFromNeighborhood = it.isFromNeighborhood,
                address = AddressDTO(
                    id = address.address.id,
                    description = address.address.description,
                    city = address.address.city,
                    country = address.address.country,
                    number = address.address.number,
                    province = address.address.province,
                    street = address.address.street
                ),
                pets = pets.pets.map { pet ->
                    PetDTO(
                        id = pet.id,
                        name = pet.name,
                        gender = pet.gender,
                        race = pet.race,
                        size = pet.size,
                        behaviour = pet.behaviour,
                        extraDetails = pet.extraDetails
                    )
                },
                phones = phones.phones.map { ph ->
                    PhoneDTO(
                        id = ph.id,
                        number = ph.number,
                        type = ph.type
                    )
                }
            )
        }
    }

    override suspend fun getAllCustomers() = customerDao.getAllCustomerEntities().map {
        mapper.mapToDTO(it)
    }

    override suspend fun deleteCustomer(customer: CustomerDTO) = customerDao.deleteCustomerEntity(
        customerEntity = mapper.mapToEntity(customer)
    )

}