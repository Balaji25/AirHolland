package com.bg.airholland.model.obj

class DateItem : ListItem() {
    var date: String? = null
        private set

    fun setDate(date: String?): DateItem {
        this.date = date
        return this
    }

    override val type: Int
        get() = DATE_TYPE


}