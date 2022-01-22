package ar.com.ericpennachini.fashiondog.app.data.service.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "phone")
data class PhoneEntity(
    @PrimaryKey(autoGenerate = true) var id: Long = 0L,
    var number: String,
    var type: String,
    var phoneCustomerId: Long = 0
)
