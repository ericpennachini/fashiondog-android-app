package ar.com.ericpennachini.fashiondog.app.data.service.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pet")
data class PetEntity(
    @PrimaryKey(autoGenerate = true) var id: Long = 0L,
    var name: String,
    var race: String,
    var size: String,
    var gender: String,
    var behaviour: String,
    var extraDetails: String,
    var petCustomerId: Long = 0
)
