package com.finance.trade_learn.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.finance.trade_learn.R
import com.finance.trade_learn.databinding.FragmentCurrentTradeBinding
import com.finance.trade_learn.databinding.FragmentTradePageBinding


class tradePage : Fragment() {


    lateinit var dataBindingTrade: FragmentTradePageBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        dataBindingTrade = DataBindingUtil.inflate(
            inflater, R.layout.fragment_trade_page,
            container, false
        )

        return dataBindingTrade.root
    }







    }
