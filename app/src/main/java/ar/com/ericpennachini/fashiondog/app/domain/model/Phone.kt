package ar.com.ericpennachini.fashiondog.app.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Phone(
    var id: Long = 0,
    var number: String,
    var type: String
) : Parcelable {

    override fun toString() = "$number ($type)"

    override fun equals(other: Any?): Boolean {
        if (other == null) return false
        if (other !is Phone) return false
        return other.id == this.id && other.number == this.number
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + number.hashCode()
        result = 31 * result + type.hashCode()
        return result
    }

}
