package com.bg.callhistory.callback

import com.bg.airholland.model.obj.EventItem


interface FlightEventCallback {
    fun onClick(eventItem: EventItem?)
    fun onClickOff()
}