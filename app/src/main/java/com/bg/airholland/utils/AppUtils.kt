package com.bg.airholland.utils

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.bg.airholland.R
import com.bg.airholland.model.obj.EventItem
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object AppUtils {

    val DISPLAYABLE_OPERATION_FORMAT_FULL="EEEE d MMMM yyyy"

    @SuppressLint("SimpleDateFormat")
    fun getFormattedTime(isoTime: String): String? {
       // 2018-04-18T16:53:22.000Z
        val sdf = SimpleDateFormat("MM/dd/yyyy")
        var convertedDate: Date? = null
        var formattedDate: String? = null
        try {
            convertedDate = sdf.parse(isoTime)
            formattedDate = SimpleDateFormat("HH:mm a", Locale.US).format(convertedDate)
            Log.d("date time ", formattedDate)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return formattedDate
    }


    @SuppressLint("SimpleDateFormat")
    fun getFormattedDateTime(isoTime: String): String? {
        // 2018-04-18T16:53:22.000Z
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        var convertedDate: Date? = null
        var formattedDate: String? = null
        try {
            convertedDate = sdf.parse(isoTime)
            formattedDate = SimpleDateFormat("EEE d MMMM yyyy HH:mm a", Locale.US).format(
                convertedDate
            )
            Log.d("date time ", formattedDate)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return formattedDate
    }

    fun getDisplayableOperationDate(context: Context?, operationDate: Date?): String? {
        val instance = Calendar.getInstance()
        instance.time = operationDate
        return context?.let {
            getDisplayableOperationDate(
                it,
                Calendar.getInstance(),
                instance
            )
        }
    }

    fun getDisplayableOperationDate(
        context: Context,
        now: Calendar?,
        operationDate: Calendar?
    ): String? {
        if (now == null || operationDate == null) {
            return null
        }
        val operationDay = operationDate[Calendar.DAY_OF_YEAR]
        val nowDay = now[Calendar.DAY_OF_YEAR]
        if (operationDay == nowDay && operationDate[Calendar.YEAR] == now[Calendar.YEAR]) {
            return context.getString(R.string.today)
        }
        if (operationDay == nowDay - 1 && operationDate[Calendar.YEAR] == now[Calendar.YEAR]) {
            return context.getString(R.string.yesterday)
        }
        return if (operationDate[Calendar.YEAR] != now[Calendar.YEAR]) {
            Formatter.DISPLAYABLE_OPERATION_FORMAT_FULL.format(
                operationDate.time
            )
        } else Formatter.DISPLAYABLE_OPERATION_FORMAT.format(operationDate.time)
    }



    enum class Formatter(pattern: String) {
        DISPLAYABLE_OPERATION_FORMAT("EEE d MMMM. yyyy"),
        DISPLAYABLE_OPERATION_FORMAT_FULL("EEE d MMMM yyyy"),
        DISPLAYABLE_MONTH_AND_YEAR("MMMM yyyy"), DISPLAYABLE_YEAR("yyyy");

        private val formatter: SimpleDateFormat
        fun format(d: Date?): String? {
            return if (d == null) {
                null
            } else formatter.format(d)
        }

        @Throws(ParseException::class)
        fun parse(date: String?): Date {
            return formatter.parse(date)
        }

        init {
            formatter = SimpleDateFormat(pattern, Locale.getDefault())
            //            formatter.setTimeZone(TimeZone.getTimeZone("GMT+2"));
        }
    }

    fun getTotalTime(sec: String):String{
        var seconds=sec.toInt()

      var  hours = seconds / 3600;
       var minutes = (seconds % 3600) / 60;
        seconds = seconds % 60;

      return   String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }



    fun checkLayOverDesc(eventItem: EventItem):String{
        if (eventItem.eventListModel?.DutyCode.equals(AppConstant.CONST_STANDBY, true)){
            return eventItem.eventListModel?.Departure+"(ATL)"
        }else if (eventItem.eventListModel?.DutyCode.equals(AppConstant.CONST_LAYOVER, true)){
            return eventItem.eventListModel?.Departure?:""
        }else{
            return ""
        }
    }


    fun makeDepDestinationTitle(eventItem: EventItem):String{
        if (eventItem.eventListModel?.DutyCode.equals(AppConstant.CONST_FLIGHT, true)){
            return eventItem.eventListModel?.Departure+"-"+eventItem.eventListModel?.Destination
        }else {
            return eventItem.eventListModel?.DutyCode?:""
        }
    }


    fun getFormattedFlightTime(eventItem: EventItem):String {
        if(eventItem.eventListModel?.DutyCode.equals(AppConstant.CONST_OFF, true)){
            return ""
        }else if (eventItem.eventListModel?.DutyCode.equals(AppConstant.CONST_STANDBY, true)
            || eventItem.eventListModel?.DutyCode.equals(AppConstant.CONST_FLIGHT, true))
        {
           return eventItem.eventListModel?.Time_Depart+"-"+eventItem.eventListModel?.Time_Arrive
        }else{
            return getLayOverTime(eventItem)
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun getLayOverTime(eventItem: EventItem) :String{

        val simpleDateFormat = SimpleDateFormat("HH:mm")
        val startDate = simpleDateFormat.parse(eventItem.eventListModel?.Time_Arrive)
        val endDate = simpleDateFormat.parse(eventItem.eventListModel?.Time_Depart)

        var difference = endDate.time - startDate.time
        if (difference < 0) {
            val dateMax = simpleDateFormat.parse("24:00:00")
            val dateMin = simpleDateFormat.parse("00:00:00")
            difference = dateMax.time - startDate.time + (endDate.time - dateMin.time)
        }
        val days = (difference / (1000 * 60 * 60 * 24)).toInt()
        val hours = ((difference - 1000 * 60 * 60 * 24 * days) / (1000 * 60 * 60)).toInt()
        val min =
            (difference - 1000 * 60 * 60 * 24 * days - 1000 * 60 * 60 * hours).toInt() / (1000 * 60)
        val sec =
            (difference - 1000 * 60 * 60 * 24 * days - 1000 * 60 * 60 * hours - 1000 * 60 * min).toInt() / 1000

        return hours.toString()+":"+min+"hours"
    }



    fun getStandByDesc(dutyCode:String): String{
        if (dutyCode.equals(AppConstant.CONST_STANDBY,true)){
            return AppConstant.CONST_MATCH_CREW
        }

        return ""

    }

}