package com.bg.airholland.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bg.airholland.R


@BindingAdapter("image")
fun loadImage(view: ImageView, dutyCode: String) {
    if (dutyCode.equals(AppConstant.CONST_FLIGHT,true)){
        view.setImageResource(R.drawable.ic_flights)
    }else if (dutyCode.equals(AppConstant.CONST_STANDBY,true)) {
        view.setImageResource(R.drawable.ic_standby)
    }else if (dutyCode.equals(AppConstant.CONST_POSITIONING,true)){
        view.setImageResource(R.drawable.ic_positioning)
    }else if (dutyCode.equals(AppConstant.CONST_OFF,true)){
        view.setImageResource(R.drawable.ic_day_off)
    }else if (dutyCode.equals(AppConstant.CONST_LAYOVER,true)){
        view.setImageResource(R.drawable.ic_lay_over)
    }else{
        view.setImageResource(R.drawable.ic_flights)
    }


}