package ar.com.ericpennachini.fashiondog.app.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Customer(
    val id: Long? = null,
    val firstName: String = "",
    val lastName: String = "",
    val description: String = "",
    val isFromNeighborhood: Boolean = false,
    val email: String = "",
    val address: Address = Address(),
    val pets: List<Pet> = listOf(),
    val phones: List<Phone> = listOf()
) : Parcelable
