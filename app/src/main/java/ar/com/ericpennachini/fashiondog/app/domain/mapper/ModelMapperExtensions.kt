package ar.com.ericpennachini.fashiondog.app.domain.mapper

import ar.com.ericpennachini.fashiondog.app.data.datasource.dto.AddressDTO
import ar.com.ericpennachini.fashiondog.app.data.datasource.dto.CustomerDTO
import ar.com.ericpennachini.fashiondog.app.data.datasource.dto.PetDTO
import ar.com.ericpennachini.fashiondog.app.data.datasource.dto.PhoneDTO
import ar.com.ericpennachini.fashiondog.app.domain.model.Address
import ar.com.ericpennachini.fashiondog.app.domain.model.Customer
import ar.com.ericpennachini.fashiondog.app.domain.model.Pet
import ar.com.ericpennachini.fashiondog.app.domain.model.Phone

fun AddressDTO.toModel() = Address(
    id = id,
    street = street,
    number = number,
    city = city,
    province = province,
    country = country,
    description = description
)

fun CustomerDTO.toModel() = Customer(
    id = id,
    firstName = firstName,
    lastName = lastName,
    description = description,
    isFromNeighborhood = isFromNeighborhood,
    email = email,
    address = address.toModel(),
    pets = pets.toModelList(),
    phones = phones.toModelList()
)

fun PetDTO.toModel() = Pet(
    id = id,
    name = name,
    race = race,
    size = size,
    gender = gender,
    behaviour = behaviour,
    extraDetails = extraDetails
)

fun List<PetDTO>.toModelList() = map {
    it.toModel()
}.toMutableList()

fun PhoneDTO.toModel() = Phone(
    id = id,
    number = number,
    type = type
)

fun List<PhoneDTO>.toModelList() = map {
    it.toModel()
}.toMutableList()