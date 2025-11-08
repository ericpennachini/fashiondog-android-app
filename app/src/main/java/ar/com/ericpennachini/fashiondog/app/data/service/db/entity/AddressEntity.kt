package ar.com.ericpennachini.fashiondog.app.data.service.db.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "address")
data class AddressEntity(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    var street: String,
    var number: String,
    var city: String,
    var province: String,
    var country: String,
    var description: String,
    var addressCustomerId: Long = 0
)
