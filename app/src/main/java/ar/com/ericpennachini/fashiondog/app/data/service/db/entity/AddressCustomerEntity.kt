package ar.com.ericpennachini.fashiondog.app.data.service.db.entity

import androidx.room.Embedded
import androidx.room.Relation

data class AddressCustomerEntity(
    @Embedded
    val customer: CustomerEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "addressCustomerId"
    )
    val address: AddressEntity
)
