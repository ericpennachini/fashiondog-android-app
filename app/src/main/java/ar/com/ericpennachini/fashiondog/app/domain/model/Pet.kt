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
) : Parcelable {

    override fun toString() = "$name ($race)"

    override fun equals(other: Any?): Boolean {
        if (other == null) return false
        if (other !is Pet) return false
        return other.name == this.name
            && other.race == this.race
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + race.hashCode()
        result = 31 * result + size.hashCode()
        result = 31 * result + gender.hashCode()
        result = 31 * result + behaviour.hashCode()
        result = 31 * result + extraDetails.hashCode()
        return result
    }

}
