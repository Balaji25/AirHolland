package com.bg.airholland.viewmodel.vmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import com.bg.airholland.model.obj.EventObj
import com.bg.airholland.network.NetworkConnectionInterceptor
import com.bg.airholland.util.lazyDeferred
import com.bg.airholland.viewmodel.repository.RosterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Created by Balaji Gaikwad on 19/12/20.
 */
@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class RosterViewModel(
    val repository: RosterRepository,
   val networkConnectionInterceptor: NetworkConnectionInterceptor
): ViewModel() {

    val callList by lazyDeferred {
            repository.fetchRosterList()
    }

    suspend fun getFlightOfflineInfo() = withContext(Dispatchers.IO) { repository.getOfflineFlightInfo() }



}