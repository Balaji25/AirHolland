package com.bg.airholland.model.obj

import android.os.Parcelable
import java.io.Serializable


class EventItem : ListItem(),Serializable {
    var eventListModel: EventObj? = null
    override val type: Int
        get() = EVENT_TYPE
}