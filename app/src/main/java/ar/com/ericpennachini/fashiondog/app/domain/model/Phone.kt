package ar.com.ericpennachini.fashiondog.app.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Phone(
    var id: Long = 0,
    var number: String,
    var type: String
) : Parcelable
