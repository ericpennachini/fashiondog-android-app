package ar.com.ericpennachini.fashiondog.app.data.datasource.mapper

interface Mapper<Entity, DTO> {

    fun mapToDTO(entity: Entity): DTO

    fun mapToEntity(dto: DTO): Entity

}