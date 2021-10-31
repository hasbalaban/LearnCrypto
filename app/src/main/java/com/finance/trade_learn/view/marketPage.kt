package com.finance.trade_learn.view

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.finance.trade_learn.Adapters.adapter_for_market
import com.finance.trade_learn.R
import com.finance.trade_learn.databinding.FragmentMarketPageBinding
import com.finance.trade_learn.utils.IntentNavigate
import com.finance.trade_learn.viewModel.viewModelMarket
import kotlinx.coroutines.*
import java.lang.Runnable


var firstSet=true
class marketPage : Fragment() {

    lateinit var dataBindingMarket: FragmentMarketPageBinding
    var viewVisible = true
    lateinit var Job: Job

    lateinit var viewModelMarket: viewModelMarket
    var runnable = Runnable { }
    var handler = Handler(Looper.getMainLooper())
    private lateinit var adapter: adapter_for_market

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dataBindingMarket = DataBindingUtil.inflate(
            inflater, R.layout.fragment_market_page,
            container, false
        )

        viewModelMarket = viewModelMarket()
        return dataBindingMarket.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = adapter_for_market(requireContext(), arrayListOf())

        clickToSearch()
        firstSet=true



        dataBindingMarket.RecyclerViewMarket.layoutManager =
            LinearLayoutManager(requireContext())
        dataBindingMarket.RecyclerViewMarket.adapter = adapter
        super.onViewCreated(view, savedInstanceState)
    }


    fun getDataHot() {


        if (viewVisible) {
            //observer state of list of coins
            viewModelMarket.state.observe(viewLifecycleOwner, Observer {
                if (it) {
                    viewModelMarket.ListOfCrypto.observe(viewLifecycleOwner, Observer { list ->
                        adapter.UpdateMarket(list)
                    })
                }
            })
        }
    }

    fun Update() {
        Job = CoroutineScope(Dispatchers.Main + Job()).launch {
            runnable = object : Runnable {
                override fun run() {
                    viewModelMarket.runGetAllCryptoFromApi()
                    getDataHot()
                    handler.postDelayed(runnable, 6000)
                }
            }
            handler.post(runnable)
        }
    }


    override fun onPause() {
        viewVisible = false
        Job.cancel()
        Log.i("onPause", "onPause")
        handler.removeCallbacks(runnable)
        super.onPause()
    }

    override fun onResume() {
        viewVisible = true
   //     getDataHot()
        Update()
        super.onResume()
    }

    fun clickToSearch() {
        dataBindingMarket.searchedCoin.setOnClickListener {


            IntentNavigate().navigate(requireContext(),SearchActivity::class.java)


        }
    }


}