package com.bg.airholland.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bg.airholland.R
import com.bg.airholland.databinding.DateLayoutItemBinding
import com.bg.airholland.databinding.EventListItemBinding
import com.bg.airholland.model.obj.DateItem
import com.bg.airholland.model.obj.EventItem
import com.bg.airholland.model.obj.EventObj
import com.bg.airholland.model.obj.ListItem
import com.bg.airholland.utils.AppConstant
import com.bg.airholland.utils.AppUtils
import com.bg.airholland.utils.FlightConverterUtils
import com.bg.airholland.view.RosterFragment
import com.bg.callhistory.callback.FlightEventCallback
import kotlinx.coroutines.CoroutineScope

import java.util.*

class FlightEventAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(){


    private  var eventListModel: ArrayList<EventObj>? = null
    val appUtils= AppUtils

    private var arrayList: ArrayList<ListItem> = ArrayList<ListItem>()
    private var callback: FlightEventCallback? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val mDeveloperListItemBinding = DataBindingUtil.inflate<EventListItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.event_list_item, parent, false
        )


        return when (viewType) {
            ListItem.EVENT_TYPE -> {
                val mDeveloperListItemBinding = DataBindingUtil.inflate<EventListItemBinding>(
                    LayoutInflater.from(parent.context),
                    R.layout.event_list_item, parent, false
                )
                return RosterViewHolder(mDeveloperListItemBinding)
            }
            else ->{
                val mInboxDateItemBinding = DataBindingUtil.inflate<DateLayoutItemBinding>(
                    LayoutInflater.from(parent.context),
                    R.layout.date_layout_item, parent, false
                )
                return DateViewHolder(mInboxDateItemBinding)
            }

        }

    }

        override fun onBindViewHolder(rosterViewHolder: RecyclerView.ViewHolder, position: Int) {

        val listItem = arrayList[position]
      //  val currentStudent = callHistoryListModel!![position]
//        val currentStudent = callHistoryListModel!![position]
        when (rosterViewHolder.getItemViewType()) {
            ListItem.EVENT_TYPE -> {
                val headerViewHolder = rosterViewHolder as RosterViewHolder

                rosterViewHolder.mEventListItemBinding.eventModel = listItem as EventItem?
                rosterViewHolder.mEventListItemBinding.utils = appUtils

                rosterViewHolder.mEventListItemBinding.rootLayout.setOnClickListener {
                    if (listItem.eventListModel?.DutyCode.equals(AppConstant.CONST_STANDBY, true)
                        || listItem.eventListModel?.DutyCode.equals(AppConstant.CONST_LAYOVER, true)
                        || listItem.eventListModel?.DutyCode.equals(AppConstant.CONST_FLIGHT, true)
                    ) {
                        callback?.onClick(listItem as EventItem?)
                    }else{
                        callback?.onClickOff()
                    }
                }

            }else-> {
            val headerViewHolder = rosterViewHolder as DateViewHolder


            ((rosterViewHolder as DateViewHolder).eventDateItemBinding.dateModel)
            rosterViewHolder.eventDateItemBinding.dateModelOne = listItem as DateItem?
        }
        }

        }




    override fun getItemCount(): Int {
        return if (arrayList != null) {
            arrayList!!.size
        } else {
            0
        }
    }


    fun setInboxList(
        context1: RosterFragment,
        context: Context,
        eventListModel: ArrayList<EventObj>
    ) {
        this.eventListModel = eventListModel
        arrayList= FlightConverterUtils.mapEventsToListItemWithDates(context,eventListModel) as ArrayList<ListItem>
        this.callback=context1
        notifyDataSetChanged()
    }


     class RosterViewHolder(var mEventListItemBinding: EventListItemBinding) :
        RecyclerView.ViewHolder(mEventListItemBinding.root)


     class DateViewHolder(var eventDateItemBinding: DateLayoutItemBinding) :
        RecyclerView.ViewHolder(eventDateItemBinding.root)


    override fun getItemViewType(position: Int): Int {
        return arrayList?.get(position).type
    }

}