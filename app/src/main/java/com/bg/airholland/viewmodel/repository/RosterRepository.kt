package com.bg.airholland.viewmodel.repository

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bg.airholland.model.obj.EventObj
import com.bg.airholland.network.AbstractTaskApiRequest
import com.bg.airholland.network.ApiEndPoint
import com.bg.airholland.utils.Coroutines
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.bg.airholland.db.AppDatabase
import com.bg.airholland.network.NetworkConnectionInterceptor
import java.util.ArrayList

/**
 * Created by Balaji Gaikwad on 18/12/20.
 */
class RosterRepository(val apiEndPoint: ApiEndPoint, val appDatabase: AppDatabase,val networkConnectionInterceptor: NetworkConnectionInterceptor) :AbstractTaskApiRequest(){

    private val eventMutableLiveData = MutableLiveData<ArrayList<EventObj>>()


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    suspend fun fetchRosterList(): MutableLiveData<ArrayList<EventObj>> {
        return withContext(Dispatchers.IO) {
              var   response = apiRequest { apiEndPoint.getRosterList() }
                eventMutableLiveData.postValue(response)
                saveEvents(response)
            eventMutableLiveData
        }
    }



    private fun saveEvents(eventObj: ArrayList<EventObj>) {
        Coroutines.io {
            appDatabase.getFlightEventDao().saveAllFlightEvents(eventObj as List<EventObj>)
        }
    }


    suspend fun getOfflineFlightInfo():LiveData<List<EventObj>>{
        return withContext(Dispatchers.IO) {
            appDatabase.getFlightEventDao().getOfflineFlightInfo()
        }
    }

}