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
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.finance.trade_learn.Adapters.adapter_for_hot_coins
import com.finance.trade_learn.Adapters.adapter_for_populer_coins
import com.finance.trade_learn.R
import com.finance.trade_learn.databinding.FragmentHomeBinding
import com.finance.trade_learn.utils.IntentNavigate
import com.finance.trade_learn.viewModel.ViewModeHomePage
import kotlinx.coroutines.runBlocking

class home : Fragment() {

    lateinit var adapterForHotList: adapter_for_hot_coins
    lateinit var adapterForPopulerList: adapter_for_populer_coins
    var viewVisible = false
    lateinit var dataBindingHome: FragmentHomeBinding
    lateinit var viewModelHome: ViewModeHomePage
    var runnable = Runnable { }
    var handler = Handler(Looper.getMainLooper())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onAttach(context: Context) {
        providers()
        super.onAttach(context)
    }

    private fun providers() {

        viewModelHome = ViewModelProvider(requireActivity())[ViewModeHomePage::class.java]

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBindingHome = DataBindingUtil.inflate(
            inflater, R.layout.fragment_home,
            container, false
        )


        return dataBindingHome.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        adapterForHotList = adapter_for_hot_coins(requireContext(), arrayListOf())
        dataBindingHome.RecyclerViewCoinsOfToday.layoutManager =
            LinearLayoutManager(requireContext())
        dataBindingHome.RecyclerViewCoinsOfToday.adapter = adapterForHotList


        adapterForPopulerList = adapter_for_populer_coins(requireContext(), arrayListOf())
        dataBindingHome.RecyclerViewPopulerCoins.layoutManager =
            GridLayoutManager(requireContext(), 3, RecyclerView.VERTICAL, false)
        dataBindingHome.RecyclerViewPopulerCoins.adapter = adapterForPopulerList


        clickToSearch()
        startAnimation()
        isViewModelIntialize()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun isViewModelIntialize() {
        val state = viewModelHome.isInitialize
        Log.i("state", state.toString())

        if (state.value!!) {
            viewModelHome.listOfCrypto.observe(viewLifecycleOwner, Observer {

                adapterForHotList.updateData(it)
                viewModelHome.isInitialize.value=true
            })
            viewModelHome.listOfCryptoForPopular.observe(viewLifecycleOwner, Observer {

                adapterForPopulerList.updateData(it)
            })
        }
    }


    //animation to start
    fun startAnimation() {
        val animation =
            AnimationUtils.loadAnimation(requireContext(), R.anim.animation_for_home_view)

        val imageView = dataBindingHome.notices
        imageView.animation = animation
    }


    // We Check State of Loading if loading is succesed we Will initialize adapter here
    // then we will set on recycler view


    fun getData() {
        viewModelHome.runGetAllCryptoFromApi()


        if (viewVisible) {
            //observer state of list of coins
            viewModelHome.state.observe(viewLifecycleOwner, Observer {
                if (it) {
                    if (viewVisible) {
                        viewModelHome.listOfCrypto.observe(
                            viewLifecycleOwner,
                            Observer { list ->

                                adapterForHotList.updateData(list)
                                if (viewModelHome.isInitialize.value!!){
                                    dataBindingHome.progressBar.visibility=View.INVISIBLE
                                }


                            })

                        viewModelHome.listOfCryptoForPopular.observe(
                            viewLifecycleOwner,
                            Observer { list ->
                                adapterForPopulerList.updateData(list)

                            })
                    }
                } else {

                    Log.i("ooooooo", "failed")
                }
            })
        }
    }

    private fun update() {

        runnable = object : Runnable {
            override fun run() {

                runBlocking {
                    getData()

                }

                handler.postDelayed(runnable, 3000)

            }

        }
        handler.post(runnable)


    }

    override fun onPause() {
        viewVisible = false
        handler.removeCallbacks(runnable)
        super.onPause()
    }

    override fun onResume() {

        viewVisible = true
        update()
        super.onResume()
    }


    fun clickToSearch() {
        dataBindingHome.searchCoin.setOnClickListener {
            /* val action = homeDirections.actionHomeToSearchCoin()
             Navigation.findNavController(it).navigate(action)


             */
            IntentNavigate().navigate(requireContext(), SearchActivity::class.java)


        }


    }


}