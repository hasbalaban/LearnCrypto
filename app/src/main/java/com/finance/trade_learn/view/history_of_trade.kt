package com.finance.trade_learn.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.finance.trade_learn.Adapters.adapter_for_history_trade
import com.finance.trade_learn.R
import com.finance.trade_learn.database.dataBaseEntities.SaveCoin
import com.finance.trade_learn.database.dataBaseService
import com.finance.trade_learn.databinding.FragmentHistoryOfTradeBinding
import com.finance.trade_learn.viewModel.viewModelHistoryTrade
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class history_of_trade : Fragment() {
    //  private lateinit var adapter: adapter_for_history_trade
    private lateinit var viewModelHistory: viewModelHistoryTrade

    private lateinit var dataBindinghistory_of_trade: FragmentHistoryOfTradeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        dataBindinghistory_of_trade = DataBindingUtil.inflate(
            inflater, R.layout.fragment_history_of_trade,
            container, false
        )


        return dataBindinghistory_of_trade.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setup()
        super.onViewCreated(view, savedInstanceState)
    }

    // function holder
    fun setup() {
        callOneTime()

    }

    // just call when fragment start
    fun callOneTime() {
        settingViewModel()
    }

    // this fun for viewModel Settings
    fun settingViewModel() {
        viewModelHistory = viewModelHistoryTrade()
        viewModelHistory.getDataFromDatabase(requireContext())
        getDataFromDatabase()
    }


    // this fun for get data from database as observe data
    fun getDataFromDatabase() {
        viewModelHistory.listOfTrade.observe(viewLifecycleOwner, Observer {
            settingsOfRecyclerView(it as ArrayList<SaveCoin>)

        })

    }

    // this fun for recycler view set data
    fun settingsOfRecyclerView(list_of_trade: ArrayList<SaveCoin>) {

        dataBindinghistory_of_trade.recyclerViewHistory.layoutManager =
            LinearLayoutManager(requireContext())
        val adapter = adapter_for_history_trade(list_of_trade)
        dataBindinghistory_of_trade.recyclerViewHistory.adapter = adapter

    }

}