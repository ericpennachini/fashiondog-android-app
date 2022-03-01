package ar.com.ericpennachini.fashiondog.app.domain.model

class Address(
    var id: Long = 0,
    var street: String,
    var number: String,
    var city: String,
    var province: String,
    var country: String,
    var description: String
)
