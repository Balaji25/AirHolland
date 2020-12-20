package com.bg.airholland.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bg.airholland.model.obj.EventObj
import retrofit2.Response

@Dao
interface FlightEventDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAllFlightEvents(eventObj : List<EventObj>)

    @Query("SELECT * FROM EventObj")
    fun getAllFlightEvents() : LiveData<List<EventObj>>


    @Query("SELECT * FROM EventObj")
    fun getOfflineFlightInfo() : LiveData<List<EventObj>>

}