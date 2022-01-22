package ar.com.ericpennachini.fashiondog.app.domain.mapper

import ar.com.ericpennachini.fashiondog.app.data.datasource.dto.PhoneDTO
import ar.com.ericpennachini.fashiondog.app.domain.model.Phone

object PhoneMapper : DomainMapper<PhoneDTO, Phone>, DomainListMapper<PhoneDTO, Phone> {
    override fun mapToModel(dto: PhoneDTO) = Phone(
        id = dto.id,
        number = dto.number,
        type = dto.type
    )

    override fun mapToDTO(model: Phone) = PhoneDTO(
        id = model.id,
        number = model.number,
        type = model.type
    )

    override fun mapToModelList(dtoList: List<PhoneDTO>) = dtoList.map { mapToModel(it) }

    override fun mapToDTOList(modelList: List<Phone>) = modelList.map { mapToDTO(it) }
}