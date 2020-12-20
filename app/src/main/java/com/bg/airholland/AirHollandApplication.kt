package com.bg.airholland

import android.app.Application
import com.bg.airholland.network.ApiEndPoint
import com.bg.airholland.network.NetworkConnectionInterceptor
import com.bg.airholland.viewmodel.repository.RosterRepository
import com.bg.airholland.viewmodel.modelfactory.RosterModelFactory
import com.bg.airholland.db.AppDatabase
import com.bg.airholland.view.RosterFragment
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

/**
 * Created by Balaji Gaikwad on 16/12/20.
 */
class AirHollandApplication : Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@AirHollandApplication))


     bind() from singleton { NetworkConnectionInterceptor(instance()) }
        bind() from singleton { ApiEndPoint(instance()) }

              bind() from singleton { RosterRepository(instance(),instance(),instance() ) }

             //  bind() from singleton { NetworkConnectionInterceptor(instance()) }
               bind() from provider { RosterModelFactory(instance(),instance()) }




        bind() from singleton { AppDatabase(instance()) }
           //  bind() from provider { CallDetailsFactory(instance()) }


    }

}