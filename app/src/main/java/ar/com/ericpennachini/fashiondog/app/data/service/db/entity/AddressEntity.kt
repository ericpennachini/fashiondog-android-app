package ar.com.ericpennachini.fashiondog.app.data.service.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "address")
data class AddressEntity(
    @PrimaryKey(autoGenerate = true) var id: Long = 0L,
    var street: String,
    var number: Int,
    var city: String,
    var province: String,
    var country: String,
    var description: String,
    var addressCustomerId: Long = 0
)
