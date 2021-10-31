package com.finance.trade_learn.view

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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.finance.trade_learn.Adapters.adapter_for_hot_coins
import com.finance.trade_learn.Adapters.adapter_for_populer_coins
import com.finance.trade_learn.R
import com.finance.trade_learn.databinding.FragmentHomeBinding
import com.finance.trade_learn.models.modelsConvector.CoinsHome
import com.finance.trade_learn.utils.IntentNavigate
import com.finance.trade_learn.viewModel.viewModeHomePage
import kotlinx.coroutines.runBlocking

class home : Fragment() {

    lateinit var adapterForHotList: adapter_for_hot_coins
    var viewVisible = true
    lateinit var dataBindingHome: FragmentHomeBinding
    lateinit var viewModelHomePage: viewModeHomePage
    var runnable = Runnable { }
    var handler = Handler(Looper.getMainLooper())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBindingHome = DataBindingUtil.inflate(
            inflater, R.layout.fragment_home,
            container, false
        )
        viewModelHomePage = viewModeHomePage()


        return dataBindingHome.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        adapterForHotList = adapter_for_hot_coins(requireContext(), arrayListOf())
        dataBindingHome.RecyclerViewCoinsOfToday.layoutManager =
            LinearLayoutManager(requireContext())
        dataBindingHome.RecyclerViewCoinsOfToday.adapter = adapterForHotList
        clickToSearch()
        startAnimation()
        super.onViewCreated(view, savedInstanceState)
    }



    //animation to start
    fun startAnimation(){
        val animation = AnimationUtils.loadAnimation(requireContext(),R.anim.animation_for_home_view)
        val imageView=dataBindingHome.HomeView
        imageView.animation=animation
    }

    // We Check State of Loading if loading is succesed we Will initialize adapter here
    // then we will set on recycler view

    fun GetData(list: List<CoinsHome>) {


        val adapter = adapter_for_populer_coins(requireContext(), list)
        dataBindingHome.RecyclerViewPopulerCoins.layoutManager =
            GridLayoutManager(requireContext(), list.size, RecyclerView.VERTICAL, false)
        dataBindingHome.RecyclerViewPopulerCoins.adapter = adapter


    }

    fun getDataHot() {
        viewModelHomePage.runGetAllCryptoFromApi()


        if (viewVisible) {
            Log.i("ooooooo", "7")
            //observer state of list of coins
            viewModelHomePage.state.observe(viewLifecycleOwner, Observer {
                if (it) {
                    if (viewVisible) {
                        viewModelHomePage.ListOfCrypto.observe(
                            viewLifecycleOwner,
                            Observer { list ->

                                adapterForHotList.updateData(list)

                                var popList = arrayListOf<CoinsHome>()
                                for (i in list) {
                                    if (i.CoinName.subSequence(
                                            0,
                                            3
                                        ) == "BTC" || i.CoinName.subSequence(
                                            0, 3
                                        ) == "BNB" || i.CoinName.subSequence(0, 3) == "ETH"
                                    ) {
                                        popList.add(i)
                                    }
                                }
                                GetData(popList)


                            })
                    }
                } else {

                    Log.i("ooooooo", "failed")
                }
            })
        }
    }

    fun Update() {

        runnable = object : Runnable {
            override fun run() {


                runBlocking {
                    getDataHot()

                }


                handler.postDelayed(runnable, 5000)

            }

        }
        handler.post(runnable)


    }

    override fun onPause() {
        viewVisible = true
        handler.removeCallbacks(runnable)
        super.onPause()
    }

    override fun onResume() {

        viewVisible = true
        Update()
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