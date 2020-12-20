package com.bg.airholland.utils

import android.annotation.SuppressLint
import android.content.Context
import com.bg.airholland.model.obj.DateItem
import com.bg.airholland.model.obj.EventItem
import com.bg.airholland.model.obj.EventObj
import com.bg.airholland.model.obj.ListItem
import com.bg.airholland.utils.AppUtils.getDisplayableOperationDate
import com.bg.airholland.utils.AppUtils.getFormattedTime

import java.text.ParseException
import java.text.SimpleDateFormat


object FlightConverterUtils {
    @SuppressLint("SimpleDateFormat")
    private val formatter = SimpleDateFormat("MM/dd/yyyy")
   // yyyy-MM-dd'T'HH:mm:ss.SSS'Z'

    fun mapEventsToListItemWithDates(
        context: Context,
        transactionList: ArrayList<EventObj>
    ): List<ListItem>? {


        val groupedHashMap: LinkedHashMap<String, MutableSet<EventObj>> =
            LinkedHashMap<String, MutableSet<EventObj>>()
        var list: MutableSet<EventObj>
        for (historyListItem in transactionList) {
            try {
                val hashMapKey: String = getDisplayableOperationDate(
                    context,
                    formatter.parse(
                        historyListItem.Date
                    )
                )!!
                if (groupedHashMap.containsKey(hashMapKey)) {
                    groupedHashMap[hashMapKey]!!.add(historyListItem)
                } else {
                    list = LinkedHashSet<EventObj>()
                    list.add(historyListItem)
                    groupedHashMap[hashMapKey] = list
                }


            } catch (ignored: ParseException) {
            }
        }
        return generateListFromMap(groupedHashMap)
    }

    private fun generateListFromMap(groupedHashMap: LinkedHashMap<String, MutableSet<EventObj>>): ArrayList<ListItem>? {
        val consolidatedList: ArrayList<ListItem> = ArrayList()
        for (date in groupedHashMap.keys) {
            val dateItem = DateItem()
            dateItem.setDate(date)
            consolidatedList.add(dateItem)
            for (transaction in groupedHashMap[date]!!) {
                val generalItem = EventItem()
                generalItem.eventListModel=transaction
                consolidatedList.add(generalItem)
                generalItem.eventListModel!!.Date?.let { getFormattedTime(it) };
            }
        }


        return consolidatedList
    }



}