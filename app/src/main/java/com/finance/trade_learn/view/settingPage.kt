package com.finance.trade_learn.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.finance.trade_learn.R
import com.finance.trade_learn.databinding.FragmentSettingPageBinding


class settingPage : Fragment() {


    lateinit var dataBindingSetting: FragmentSettingPageBinding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBindingSetting= DataBindingUtil.inflate(inflater,R.layout.fragment_setting_page,
            container,false)
        return  dataBindingSetting.root
    }


}