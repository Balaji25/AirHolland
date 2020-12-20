package com.bg.airholland.viewmodel.modelfactory

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bg.airholland.network.NetworkConnectionInterceptor
import com.bg.airholland.viewmodel.repository.RosterRepository
import com.bg.airholland.viewmodel.vmodel.RosterViewModel

/**
 * Created by Balaji Gaikwad on 17/12/20.
 */
class RosterModelFactory (private val repository: RosterRepository,val networkConnectionInterceptor: NetworkConnectionInterceptor) : ViewModelProvider.NewInstanceFactory() {

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RosterViewModel(repository,networkConnectionInterceptor) as T
    }
}