package ar.com.ericpennachini.fashiondog.app.data.datasource.mapper

import ar.com.ericpennachini.fashiondog.app.data.datasource.dto.CustomerDTO
import ar.com.ericpennachini.fashiondog.app.data.service.db.entity.CustomerEntity

object CustomerDTOMapper : Mapper<CustomerEntity, CustomerDTO> {
    override fun mapToDTO(entity: CustomerEntity): CustomerDTO {
        TODO("Not yet implemented")
    }

    override fun mapToEntity(dto: CustomerDTO): CustomerEntity {
        TODO("Not yet implemented")
    }
}