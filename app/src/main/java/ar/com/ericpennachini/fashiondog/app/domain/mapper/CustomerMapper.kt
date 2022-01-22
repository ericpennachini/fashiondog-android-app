package ar.com.ericpennachini.fashiondog.app.domain.mapper

import ar.com.ericpennachini.fashiondog.app.data.datasource.dto.CustomerDTO
import ar.com.ericpennachini.fashiondog.app.domain.model.Customer

object CustomerMapper : DomainMapper<CustomerDTO, Customer> {
    override fun mapToModel(dto: CustomerDTO) = Customer(
        id = dto.id,
        firstName = dto.firstName,
        lastName = dto.lastName,
        description = dto.description,
        email = dto.email,
        isFromNeighborhood = dto.isFromNeighborhood,
        address = AddressMapper.mapToModel(dto.address),
        phones = PhoneMapper.mapToModelList(dto.phones),
        pets = PetMapper.mapToModelList(dto.pets)
    )

    override fun mapToDTO(model: Customer) = CustomerDTO(
        id = model.id,
        firstName = model.firstName,
        lastName = model.lastName,
        description = model.description,
        email = model.email,
        isFromNeighborhood = model.isFromNeighborhood,
        address = AddressMapper.mapToDTO(model.address),
        phones = PhoneMapper.mapToDTOList(model.phones),
        pets = PetMapper.mapToDTOList(model.pets)
    )
}