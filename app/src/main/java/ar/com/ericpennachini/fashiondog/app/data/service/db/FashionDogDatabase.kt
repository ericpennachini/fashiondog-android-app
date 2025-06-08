package ar.com.ericpennachini.fashiondog.app.data.service.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ar.com.ericpennachini.fashiondog.app.data.service.db.dao.CustomerDao
import ar.com.ericpennachini.fashiondog.app.data.service.db.entity.AddressEntity
import ar.com.ericpennachini.fashiondog.app.data.service.db.entity.CustomerEntity
import ar.com.ericpennachini.fashiondog.app.data.service.db.entity.PetEntity
import ar.com.ericpennachini.fashiondog.app.data.service.db.entity.PhoneEntity

@Database(entities = [
    CustomerEntity::class,
    AddressEntity::class,
    PetEntity::class,
    PhoneEntity::class
], version = 1)
abstract class FashionDogDatabase : RoomDatabase() {

    companion object {
        private const val DATABASE_NAME = "fashion_dog.db"

        private var instance: FashionDogDatabase? = null

        private fun create(context: Context): FashionDogDatabase {
            return Room.databaseBuilder(context, FashionDogDatabase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()
        }

        fun getInstance(context: Context) = (instance ?: create(context)).also { instance = it }
    }

    abstract fun customerDao(): CustomerDao

}