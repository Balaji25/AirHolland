package com.bg.airholland.view

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bg.airholland.R
import com.bg.airholland.adapter.FlightEventAdapter
import com.bg.airholland.databinding.FragmentFirstBinding
import com.bg.airholland.model.obj.EventItem
import com.bg.airholland.model.obj.EventObj
import com.bg.airholland.model.obj.ListItem
import com.bg.airholland.network.NetworkConnectionInterceptor
import com.bg.airholland.utils.*
import com.bg.airholland.viewmodel.modelfactory.RosterModelFactory
import com.bg.airholland.viewmodel.vmodel.RosterViewModel
import com.bg.callhistory.callback.FlightEventCallback
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import java.util.ArrayList

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class RosterFragment : Fragment() , KodeinAware ,FlightEventCallback, SwipeRefreshLayout.OnRefreshListener{


    private lateinit var progressBar: ProgressBar
    private lateinit var textviewDataNotAvail: TextView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var flightEventAdapter: FlightEventAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var totalListSize: ArrayList<ListItem>
    private lateinit var rosterViewModel: RosterViewModel
    private val factory: RosterModelFactory by instance()
    override val kodein by kodein()

    val networkConnection: NetworkConnectionInterceptor by instance()

    companion object {
      //  fun newInstance() = CallDetailsFragment()
        const val EXTRA_ROSTER_DETAILS="EXTRA_ROSTER_DETAILS"
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        rosterViewModel = ViewModelProvider(this, factory).get(RosterViewModel::class.java)

        val inboxBinding:FragmentFirstBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_first, container, false
        )


        (activity as MainActivity).supportActionBar?.show()
        // bind RecyclerView
        recyclerView = inboxBinding?.recyclerViewList

        textviewDataNotAvail = inboxBinding?.textviewDataNotAvailable
        progressBar = inboxBinding?.loadBar



        // bind swipreRefreshLayout
        swipeRefreshLayout = inboxBinding?.swipeRefreshLayout
        swipeRefreshLayout.setOnRefreshListener(this)
        getUiData()


        recyclerView!!.setLayoutManager(LinearLayoutManager(activity))
        recyclerView!!.setHasFixedSize(true)

        //init the Custom adataper
        flightEventAdapter = FlightEventAdapter()
        //set the CustomAdapter
        recyclerView.setAdapter(flightEventAdapter)

        return inboxBinding.root
    }



    private fun bindUI() = Coroutines.main {
        try {
            rosterViewModel.callList.await().observe(viewLifecycleOwner, Observer {
                updateList(it)
            })
        } catch (e: ApiException) {
            e.printStackTrace()
        } catch (e: NoInternetException) {
            e.printStackTrace()
        }

        progressBar.visibility=View.GONE
        swipeRefreshLayout.isRefreshing=false
    }


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun bindUIFromDB(){
        try {
            lifecycleScope.launch {
                rosterViewModel.getFlightOfflineInfo().observe(viewLifecycleOwner, {
                updateList(it  as ArrayList<EventObj>)
                })
            }

        } catch (e: ApiException) {
            e.printStackTrace()
        } catch (e: NoInternetException) {
            e.printStackTrace()
        }
        progressBar.visibility=View.GONE
        swipeRefreshLayout.isRefreshing=false
    }


    fun updateList(arrayList: ArrayList<EventObj>) {
        if (arrayList?.size==0){
            textviewDataNotAvail.visibility=View.VISIBLE
        }else{
            textviewDataNotAvail.visibility=View.GONE
            flightEventAdapter?.setInboxList(this,requireActivity(),  arrayList)

        }


    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun getUiData(){
        progressBar.visibility=View.VISIBLE

        if(networkConnection.isInternetAvailable()){
            bindUI()
        }else{
            bindUIFromDB()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onClick(eventItem: EventItem?) {

        var bundle =Bundle()
        bundle.putSerializable(EXTRA_ROSTER_DETAILS,eventItem)
        findNavController().navigate(R.id.action_FirstFragment_to_RosterDetailsFragment, bundle)
    }

    override fun onClickOff() {
        Toast.makeText(activity,getString(R.string.str_info_not_available),Toast.LENGTH_LONG).show()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onRefresh() {
        getUiData()
    }
}