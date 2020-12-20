package com.bg.airholland.model.obj

import androidx.room.Entity
import androidx.room.PrimaryKey


const val CURRENT_EVENT_ID = 0
@Entity
data class EventObj(val Destination: String? ,
                    val DutyID: String? ,
                    val AircraftType: String ? ,
                    val Time_Depart: String?  ,
                    val DutyCode: String?  ,
                    val Flightnr: String?  ,
                    val Captain: String ? ,
                    val Date: String?  ,
                    val FirstOfficer: String?  ,
                    val Time_Arrive: String? ,
                    val FlightAttendant: String? ,
                    val Departure: String? ,
                    val Tail: String? )

{
    @PrimaryKey(autoGenerate = true)
    var eid: Int = CURRENT_EVENT_ID
}