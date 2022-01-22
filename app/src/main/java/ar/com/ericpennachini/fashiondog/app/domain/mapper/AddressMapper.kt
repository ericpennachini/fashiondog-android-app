package ar.com.ericpennachini.fashiondog.app.domain.mapper

import ar.com.ericpennachini.fashiondog.app.data.datasource.dto.AddressDTO
import ar.com.ericpennachini.fashiondog.app.domain.model.Address

object AddressMapper : DomainMapper<AddressDTO, Address> {
    override fun mapToModel(dto: AddressDTO) = Address(
        id = dto.id,
        number = dto.number,
        street = dto.street,
        description = dto.description,
        city = dto.city,
        province = dto.province,
        country = dto.country
    )

    override fun mapToDTO(model: Address) = AddressDTO(
        id = model.id,
        number = model.number,
        street = model.street,
        description = model.description,
        city = model.city,
        province = model.province,
        country = model.country
    )
}