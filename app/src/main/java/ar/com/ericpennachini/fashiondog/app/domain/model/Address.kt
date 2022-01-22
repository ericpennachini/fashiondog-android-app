package ar.com.ericpennachini.fashiondog.app.domain.model

class Address(
    var id: Long = 0,
    var street: String,
    var number: Int,
    var city: String,
    var province: String,
    var country: String,
    var description: String
)