package ar.com.ericpennachini.fashiondog.app.data.datasource.dto

data class CustomerDTO(
    var id: Long? = null,
    var firstName: String,
    var lastName: String,
    var description: String,
    var isFromNeighborhood: Boolean,
    var email: String,
    var address: AddressDTO,
    var pets: List<PetDTO>,
    var phones: List<PhoneDTO>
)