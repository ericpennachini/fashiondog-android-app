package ar.com.ericpennachini.fashiondog.app.domain.mapper

interface DomainListMapper<DTO, Model> {

    fun mapToModelList(dtoList: List<DTO>): List<Model>

    fun mapToDTOList(modelList: List<Model>): List<DTO>

}