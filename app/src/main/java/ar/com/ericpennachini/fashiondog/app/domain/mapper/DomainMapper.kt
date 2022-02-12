package ar.com.ericpennachini.fashiondog.app.domain.mapper

interface DomainMapper<DTO, Model> {
    fun mapToModel(dto: DTO): Model
    fun mapToDTO(model: Model): DTO
}