package ar.com.ericpennachini.fashiondog.app.data.service.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "customer")
data class CustomerEntity(
    @PrimaryKey(autoGenerate = true) var id: Long = 0L,
    var firstName: String,
    var lastName: String,
    var description: String,
    var isFromNeighborhood: Boolean,
    var email: String
)