package ar.com.ericpennachini.fashiondog.app.domain.model

class Customer(
    val id: Long = 0,
    var firstName: String,
    var lastName: String,
    var description: String,
    var isFromNeighborhood: Boolean,
    var email: String,
    var address: Address,
    var pets: List<Pet>,
    var phones: List<Phone>
)