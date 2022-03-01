package ar.com.ericpennachini.fashiondog.app.data.datasource.dto

class AddressDTO(
    var id: Long = 0L,
    var street: String,
    var number: String,
    var city: String,
    var province: String,
    var country: String,
    var description: String
)
