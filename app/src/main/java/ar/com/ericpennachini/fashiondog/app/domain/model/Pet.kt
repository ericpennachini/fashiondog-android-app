package ar.com.ericpennachini.fashiondog.app.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Pet(
    var id: Long,
    var name: String,
    var race: String,
    var size: String,
    var gender: String,
    var behaviour: String,
    var extraDetails: String
) : Parcelable
