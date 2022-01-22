package ar.com.ericpennachini.fashiondog.app.domain.mapper

import ar.com.ericpennachini.fashiondog.app.data.datasource.dto.PetDTO
import ar.com.ericpennachini.fashiondog.app.domain.model.Pet

object PetMapper : DomainMapper<PetDTO, Pet>, DomainListMapper<PetDTO, Pet> {
    override fun mapToModel(dto: PetDTO) = Pet(
        id = dto.id,
        name = dto.name,
        race = dto.race,
        gender = dto.gender,
        size = dto.size,
        behaviour = dto.behaviour,
        extraDetails = dto.extraDetails
    )

    override fun mapToDTO(model: Pet) = PetDTO(
        id = model.id,
        name = model.name,
        race = model.race,
        gender = model.gender,
        size = model.size,
        behaviour = model.behaviour,
        extraDetails = model.extraDetails
    )

    override fun mapToModelList(dtoList: List<PetDTO>) = dtoList.map { mapToModel(it) }

    override fun mapToDTOList(modelList: List<Pet>) = modelList.map { mapToDTO(it) }
}