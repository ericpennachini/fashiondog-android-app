package ar.com.ericpennachini.fashiondog.app.data.datasource.dto

data class PetDTO(
    var id: Long = 0L,
    var name: String,
    var race: String,
    var size: String,
    var gender: String,
    var behaviour: String,
    var extraDetails: String
)