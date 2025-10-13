package ar.com.ericpennachini.fashiondog.app.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Customer(
    val id: Long? = null,
    var firstName: String = "",
    var lastName: String = "",
    var description: String = "",
    var isFromNeighborhood: Boolean = false,
    var email: String = "",
    var address: Address = Address(),
    var pets: MutableList<Pet> = mutableListOf(),
    var phones: MutableList<Phone> = mutableListOf()
) : Parcelable
