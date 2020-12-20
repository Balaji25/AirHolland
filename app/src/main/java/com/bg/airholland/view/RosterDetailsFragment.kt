package com.bg.airholland.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bg.airholland.R
import com.bg.airholland.databinding.RosterDetailsFragmentBinding
import com.bg.airholland.model.obj.EventItem
import com.bg.airholland.utils.AppUtils
import com.bg.airholland.view.RosterFragment.Companion.EXTRA_ROSTER_DETAILS


class RosterDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = RosterDetailsFragment()
    }

    private lateinit var backArrowView: ImageView
    private lateinit var eventItem: EventItem
    private lateinit var rosterDeatilsFragmentBinding: RosterDetailsFragmentBinding

    //private var rosterDeatilsFragmentBinding: Any
    private lateinit var viewModel: RosterDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        (activity as MainActivity).supportActionBar?.hide()


        viewModel = ViewModelProvider(this).get(RosterDetailsViewModel::class.java)


        rosterDeatilsFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.roster_details_fragment, container, false
        )

        backArrowView = rosterDeatilsFragmentBinding?.backArrow

        backArrowView.setOnClickListener {
            findNavController().navigate(R.id.action_RosterDetailsFragment_to_FirstFragment)
        }




        return rosterDeatilsFragmentBinding.root
    }



    fun setUpUI(eventObj: EventItem) {

        rosterDeatilsFragmentBinding.callDetailsModel=eventObj
        rosterDeatilsFragmentBinding.utils=AppUtils
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // var args=arguments?.getBundle("test")
        if (arguments!=null && arguments?.getSerializable(EXTRA_ROSTER_DETAILS)!=null){
            // Log.d("data", (args.toString()))
            eventItem= arguments?.getSerializable(EXTRA_ROSTER_DETAILS) as EventItem

            eventItem.let { setUpUI(it) }
        }

        // TODO: Use the ViewModel
    }


}