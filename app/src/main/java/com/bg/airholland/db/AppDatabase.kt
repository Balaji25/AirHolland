package com.bg.airholland.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bg.airholland.model.obj.EventObj

@Database(
    entities = [EventObj::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {


    abstract fun getFlightEventDao(): FlightEventDao

    companion object {

        @Volatile
        private var instance: AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "AirHolland.db"
            ).build()
    }
}