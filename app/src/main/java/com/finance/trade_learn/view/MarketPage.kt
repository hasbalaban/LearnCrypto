package com.finance.trade_learn.view

import android.content.Context
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
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.finance.trade_learn.Adapters.adapter_for_market
import com.finance.trade_learn.R
import com.finance.trade_learn.databinding.FragmentMarketPageBinding
import com.finance.trade_learn.utils.IntentNavigate
import com.finance.trade_learn.viewModel.ViewModelMarket
import kotlinx.coroutines.*
import java.lang.Runnable


var firstSet = true

class MarketPage : Fragment() {


    private lateinit var viewModelMarket: ViewModelMarket
    private lateinit var dataBindingMarket: FragmentMarketPageBinding
    private var viewVisible = true
    private var job: Job? = null

    var runnable = Runnable { }
    var handler = Handler(Looper.getMainLooper())
    private lateinit var adapter: adapter_for_market

    override fun onAttach(context: Context) {

        providers()
        super.onAttach(context)
    }

    private fun providers() {

        viewModelMarket = ViewModelProvider(requireActivity())[ViewModelMarket::class.java]
        // update()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        dataBindingMarket = DataBindingUtil.inflate(
            inflater, R.layout.fragment_market_page,
            container, false
        )

        return dataBindingMarket.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        clickToSearch()
        firstSet = true

        setAdapter()
        isIntializeViewModel()

        super.onViewCreated(view, savedInstanceState)
    }

    private fun setAdapter() {
        adapter = adapter_for_market(requireContext(), arrayListOf())
        val layoutManager = LinearLayoutManager(requireContext())
        dataBindingMarket.RecyclerViewMarket.layoutManager = layoutManager
        dataBindingMarket.RecyclerViewMarket.adapter = adapter
    }

    private fun isIntializeViewModel() {

        val status = viewModelMarket.isInitialized
        if (status.value!!) {


            viewModelMarket.listOfCrypto.observe(viewLifecycleOwner, {

                adapter.updateData(it)
                viewModelMarket.isInitialized.value = true
            })
        }

    }

    fun getData() {

        if (viewVisible) {
            //observer state of list of coins
            viewModelMarket.state.observe(viewLifecycleOwner, {
                if (it) {
                    viewModelMarket.listOfCrypto.observe(viewLifecycleOwner, Observer { list ->
                        adapter.updateData(list)
                        if (viewModelMarket.isInitialized.value!!){
                            dataBindingMarket.progressBar.visibility=View.INVISIBLE
                        }
                    })
                }
            })
        }
    }

    private fun update() {
        job = CoroutineScope(Dispatchers.Main + Job()).launch {
            runnable = object : Runnable {
                override fun run() {
                    viewModelMarket.runGetAllCryptoFromApi()
                    getData()
                    handler.postDelayed(runnable, 6000)
                }
            }
            handler.post(runnable)
        }
    }


    override fun onPause() {
        viewVisible = false
        job?.cancel()
        Log.i("onPause", "onPause")
        handler.removeCallbacks(runnable)
        super.onPause()
    }

    override fun onResume() {

        update()
        viewVisible = true
        super.onResume()
    }

    private fun clickToSearch() {
        dataBindingMarket.searchedCoin.setOnClickListener {


            IntentNavigate().navigate(requireContext(), SearchActivity::class.java)


        }
    }


}