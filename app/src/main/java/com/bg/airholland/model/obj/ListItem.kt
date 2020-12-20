package com.bg.airholland.model.obj

abstract class ListItem {
    abstract val type: Int

    companion object {
        const val DATE_TYPE = 0
        const val
                EVENT_TYPE = 1
    }
}