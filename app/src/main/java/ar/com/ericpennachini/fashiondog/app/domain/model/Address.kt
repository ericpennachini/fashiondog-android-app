package ar.com.ericpennachini.fashiondog.app.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Address(
    var id: Long? = null,
    var street: String = "",
    var number: String = "",
    var city: String = "",
    var province: String = "",
    var country: String = "",
    var description: String = ""
) : Parcelable
