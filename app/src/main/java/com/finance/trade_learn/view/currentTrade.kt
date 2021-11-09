package com.finance.trade_learn.view

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.SeekBar
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.finance.trade_learn.clickListener.ListenerInterface
import com.finance.trade_learn.R
import com.finance.trade_learn.databinding.FragmentCurrentTradeBinding
import com.finance.trade_learn.enums.tradeEnum
import com.finance.trade_learn.models.on_crypto_trade.BaseModelOneCryptoModel
import com.finance.trade_learn.utils.sharedPreferencesManager
import com.finance.trade_learn.viewModel.viewModelCurrentTrade


class currentTrade : Fragment(), TextWatcher {


    private lateinit var toast: Toast
    private var viewVisible = true
    private var currentPrice = 0.0
    private var runnable = Runnable { }
    private var handler = Handler(Looper.getMainLooper())

    private lateinit var dataBindingCurrentTrade: FragmentCurrentTradeBinding
    private var tradeState = tradeEnum.Buy
    private lateinit var viewModelCurrentTrade: viewModelCurrentTrade
    private var coinName = "BTC"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dataBindingCurrentTrade = DataBindingUtil.inflate(
            inflater, R.layout.fragment_current_trade,
            container, false
        )

        return dataBindingCurrentTrade.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        setup()
        super.onViewCreated(view, savedInstanceState)
    }

    //call this function when viewCrated to initialize addTextChangedListener etc.
    // get dataFrom Database and Api // Call fun setInitialize()
    //call fun startAnimation()- set Click Listener
    private fun setup() {

        toast = Toast.makeText(requireContext(), "", Toast.LENGTH_SHORT)

        viewModelCurrentTrade = viewModelCurrentTrade(requireContext())
        setInitialize()
        dataBindingCurrentTrade.coinName.setText(coinName + " / USDT")
        dataBindingCurrentTrade.clickLisener = clickListenerInitialize
        dataBindingCurrentTrade.coinAmount.addTextChangedListener(this)
        dataBindingCurrentTrade.coinPrice.addTextChangedListener(this)
        getDetailsOfCoinFromDatabase()
        startAnimation()
    }


    //first start this to get name of we had clicked
    private fun setInitialize() {

        coinName = sharedPreferencesManager(requireContext())
            .getSharedPreferencesString("coinName")

        seekBarsProgress()

    }


    //animation to start
    private fun startAnimation() {
        val animationBuy =
            AnimationUtils.loadAnimation(requireContext(), R.anim.animation_for_buy_button)
        val buyButton = dataBindingCurrentTrade.Buy
        buyButton.animation = animationBuy

        val animationSell =
            AnimationUtils.loadAnimation(requireContext(), R.anim.animation_for_sell_button)
        val sellButton = dataBindingCurrentTrade.Sell
        sellButton.animation = animationSell

        val animationToRight =
            AnimationUtils.loadAnimation(requireContext(), R.anim.anime_to_right)
        animationToRight.duration = 400
        val coinRice = dataBindingCurrentTrade.coinPrice
        coinRice.animation = animationToRight

        val relayoutAmount = dataBindingCurrentTrade.relayoutAmount
        animationToRight.duration = 700
        relayoutAmount.animation = animationToRight

        val total = dataBindingCurrentTrade.Total
        animationToRight.duration = 1000
        total.animation = animationToRight

        val relayoutAvaible = dataBindingCurrentTrade.relayoutAvaible
        animationToRight.duration = 500
        relayoutAvaible.animation = animationToRight

        val doTradeButton = dataBindingCurrentTrade.doTrade
        animationToRight.duration = 500
        doTradeButton.animation = animationToRight

        val historyOfTrade = dataBindingCurrentTrade.historyOfTrade
        animationToRight.duration = 500
        historyOfTrade.animation = animationToRight

    }


    // this function manager time to get data in per 5 seek.
    //we override here runable as Lambda instead Object
    private fun upDatePer5Sec() {
        runnable = Runnable { //call this function for update
            getDataFromApi()
            handler.postDelayed(runnable, 3000)
        }
        handler.post(runnable)


    }


    // we getting data from database when fragment starting and after any trade
    fun getDetailsOfCoinFromDatabase(coinName: String = "USDT") {
        viewModelCurrentTrade.getDetailsOfCoinFromDatabase(coinName)
        if (viewVisible) {
            viewModelCurrentTrade.coinAmountLiveData.observe(viewLifecycleOwner, {
                if (it != null) {

                    val text = (it.toString())
                    dataBindingCurrentTrade.avaibleAmount.text = text
                    dataBindingCurrentTrade.symbol.text = coinName

                } else
                    dataBindingCurrentTrade.avaibleAmount.text = ""

            })

        }

    }


    // we call this function per 5 second .we getting data from Api on this function
    private fun getDataFromApi() {
        viewModelCurrentTrade.getSelectedCoinDetails(coinName)
        if (viewVisible) {
            viewModelCurrentTrade.selectedCoinToTradeDetails.observe(
                viewLifecycleOwner, { coin ->
                    currentPrice = coin[0].price.toDouble()
                    putDataInItemSettings(coin[0])


                    // after it get data from api initialize max of seek bar
                    maxOfSeekBar()


                })


        }

    }

    // this fun for binding of fata - change percente/price etc.
    private fun putDataInItemSettings(coin: BaseModelOneCryptoModel) {

        try {
            val coinPrice = coin.price
            dataBindingCurrentTrade.coinPrice.setText(coinPrice)


            var coinPercentChange: String = coin.d1.price_change_pct
            coinPercentChange =
                ((coinPercentChange.toDouble() * 100.0).toString() + "0000").subSequence(0, 5)
                    .toString()


            if (coin.d1.price_change_pct.subSequence(0, 1) == "-") {

                dataBindingCurrentTrade.coinChangePercent.setText(coinPercentChange + "%")
                dataBindingCurrentTrade.coinChangePercent.setTextColor(Color.parseColor("#F6465D"))
            } else {
                if (coinPercentChange == "") {
                    dataBindingCurrentTrade.coinChangePercent.setText(R.string.fail)
                    dataBindingCurrentTrade.coinChangePercent.setTextColor(Color.parseColor("#2ebd85"))
                } else {
                    dataBindingCurrentTrade.coinChangePercent.setText("+ " + coinPercentChange + "%")
                    dataBindingCurrentTrade.coinChangePercent.setTextColor(Color.parseColor("#2ebd85"))

                }


            }
        } catch (e: Exception) {
            Log.i("hatam", e.message.toString())
        }


    }


    //create tost message function
    fun toastMessages(messages: Int = 1) {
        if (messages != 1) {
            toast.cancel()
            toast = Toast.makeText(requireContext(), messages, Toast.LENGTH_SHORT)
            toast.show()
        }
    }


    // listener override here and initialize
    private val clickListenerInitialize = object : ListenerInterface {
        override fun ClickListener(view: View) {
            when (view.id) {
                dataBindingCurrentTrade.Buy.id -> {
                    getDetailsOfCoinFromDatabase()
                    tradeState = tradeEnum.Buy
                    buyClicked()
                }
                dataBindingCurrentTrade.Sell.id -> {
                    getDetailsOfCoinFromDatabase(coinName)
                    tradeState = tradeEnum.Sell
                    sellClicked()
                }
                // add one more coin
                dataBindingCurrentTrade.minus.id -> {
                    // if amount not equals zero (0)
                    if (dataBindingCurrentTrade.coinAmount.text.toString() != "") {

                        if (dataBindingCurrentTrade.coinAmount.text.toString().toDouble() != 0.0) {

                            if (dataBindingCurrentTrade.coinAmount.text.toString() != "") {
                                val currentAmount =
                                    dataBindingCurrentTrade.coinAmount.text.toString().toDouble()
                                val newAmount = currentAmount - 1.000
                                dataBindingCurrentTrade.coinAmount.setText(newAmount.toString())
                            }

                        } else {
                            toastMessages(R.string.trueValue)

                        }
                    }
                    // if amount equals zero (0) show messages
                    else {
                        toastMessages(R.string.trueValue)

                    }

                }


                //when we click raise button
                dataBindingCurrentTrade.raise.id -> {

                    if (dataBindingCurrentTrade.coinAmount.text.toString() != "") {
                        val currentAmount =
                            dataBindingCurrentTrade.coinAmount.text.toString().toDouble()
                        val newAmount = currentAmount + 1.000
                        dataBindingCurrentTrade.coinAmount.setText(newAmount.toString())
                    } else {
                        val currentAmount = 0.000
                        val newAmount = currentAmount + 1.000
                        dataBindingCurrentTrade.coinAmount.setText(newAmount.toString())
                    }
                }

                //when we click do trade button
                dataBindingCurrentTrade.doTrade.id -> {

                    val amount = dataBindingCurrentTrade.coinAmount.text.toString()
                    if (amount != "" || amount != "0.0") {
                        //check views and other is emty or not etc.
                        val logicalCompare = compare()
                        if (logicalCompare) {
                            operationTrade()
                        } else {
                            toastMessages(R.string.proggresState)
                        }

                    } else {
                        toastMessages(R.string.enterAmountDialog)
                    }


                }

                // navigate last  trade fragment
                dataBindingCurrentTrade.historyOfTrade.id -> {
                    val action = currentTradeDirections.actionCurrentTradeToHistoryOfTrade()
                    Navigation.findNavController(dataBindingCurrentTrade.root).navigate(action)

                }


            }
        }

    }


    // create ax value of seek bar
    fun maxOfSeekBar(): Int {
        var max = 0
        val avaibleAmount = dataBindingCurrentTrade.avaibleAmount.text.toString().toDouble().toInt()
        when (tradeState) {
            tradeEnum.Sell -> {
                max = avaibleAmount
                dataBindingCurrentTrade.percentOfAvaible.max = max
            }
            tradeEnum.Buy -> {
                max = (avaibleAmount / currentPrice).toInt()
                dataBindingCurrentTrade.percentOfAvaible.max = max
            }
        }
        return max

    }


    // function for trade with seek seekBarsProgress
    private fun seekBarsProgress() {

        dataBindingCurrentTrade.percentOfAvaible.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                //for seek percent
                val percentAmount = dataBindingCurrentTrade.percentOfAvaible.progress
                dataBindingCurrentTrade.percentOfAvaible.minimumHeight = 0
                when (tradeState) {

                    tradeEnum.Buy -> {
                        dataBindingCurrentTrade.percentOfAvaible.max = maxOfSeekBar()

                        dataBindingCurrentTrade.coinAmount.setText(percentAmount.toString())
                        if (percentAmount.toString() == "0") {
                            dataBindingCurrentTrade.Total.setText(R.string.addZeros)
                        }


                    }
                    tradeEnum.Sell -> {

                        dataBindingCurrentTrade.percentOfAvaible.max = maxOfSeekBar()
                        if (percentAmount.toString() == "0") {
                            dataBindingCurrentTrade.Total.setText(R.string.addZeros)
                        }

                        dataBindingCurrentTrade.coinAmount.setText(percentAmount.toString())
                        Log.i("amountt", percentAmount.toString())
                    }
                }

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {


            }


        })


    }


    // this fun for check logical states. is emty or avaible is more than i want buy etc.
    fun compare(): Boolean {

        var operationState = false
        val avaibleAmount = dataBindingCurrentTrade.avaibleAmount.text.toString()
        val total = dataBindingCurrentTrade.Total.text.toString()
        val amount = dataBindingCurrentTrade.coinAmount.text.toString()
        if (avaibleAmount != "" && total != "" && amount != "") {

            operationState = when (tradeState) {
                tradeEnum.Buy -> {
                    avaibleAmount.toDouble() >= total.toDouble()
                }
                tradeEnum.Sell -> {
                    avaibleAmount.toDouble() >= amount.toDouble()
                }

            }

        }

        return operationState

    }


    // this fun for trade operation - buy , sell operation etc.
    fun operationTrade() {
        when (tradeState) {
            tradeEnum.Buy -> {
                val amount = dataBindingCurrentTrade.coinAmount.text.toString()
                val price = dataBindingCurrentTrade.coinPrice.text.toString()
                if (amount != "" && price != "") {

                    val coinAmount = amount.toDouble()
                    val coinPrice = currentPrice
                    val total = coinAmount * coinPrice

                    viewModelCurrentTrade.buyCoin(coinName, coinAmount, total, currentPrice)
                    viewModelCurrentTrade.state.observe(viewLifecycleOwner, {
                        if (it) {
                            getDetailsOfCoinFromDatabase()
                            toastMessages(R.string.succes)

                        } else {
                            toastMessages(R.string.fail)
                        }
                    })

                }


            }
            tradeEnum.Sell -> {
                val amount = dataBindingCurrentTrade.coinAmount.text.toString()
                val price = dataBindingCurrentTrade.coinPrice.text.toString()
                if (amount != "" && price != "") {

                    val coinAmount = amount.toDouble()
                    val coinPrice = currentPrice
                    val total = coinAmount * coinPrice

                    viewModelCurrentTrade.sellCoin(coinName, coinAmount, total, currentPrice)
                    viewModelCurrentTrade.state.observe(viewLifecycleOwner, {
                        if (it) {
                            getDetailsOfCoinFromDatabase(coinName)
                            toastMessages(R.string.succes)

                        } else {
                            toastMessages(R.string.fail)
                        }
                    })


                }

            }

        }

        viewModelCurrentTrade.state

    }


    // this fun for when we click buy button set backGroung color, text color etc.
    fun buyClicked() {
        tradeState = tradeEnum.Buy
        getDetailsOfCoinFromDatabase("USDT")

        dataBindingCurrentTrade.Buy.setTextColor(Color.parseColor("#ffffff"))
        dataBindingCurrentTrade.Buy.setBackgroundColor(Color.parseColor("#2ebd85"))

        dataBindingCurrentTrade.Sell.setTextColor(Color.parseColor("#8e919c"))
        dataBindingCurrentTrade.Sell.setBackgroundColor(Color.parseColor("#f5f5f5"))

        dataBindingCurrentTrade.doTrade.setBackgroundColor(Color.parseColor("#2ebd85"))
        dataBindingCurrentTrade.doTrade.setText(R.string.textBuy)
    }


    // this fun for when we click sell button set backGroung color, text color etc.
    fun sellClicked() {
        tradeState = tradeEnum.Sell
        getDetailsOfCoinFromDatabase(coinName)
        dataBindingCurrentTrade.Buy.setTextColor(Color.parseColor("#8e919c"))
        dataBindingCurrentTrade.Buy.setBackgroundColor(Color.parseColor("#f5f5f5"))

        dataBindingCurrentTrade.Sell.setTextColor(Color.parseColor("#ffffff"))
        dataBindingCurrentTrade.Sell.setBackgroundColor(Color.parseColor("#F6465D"))

        dataBindingCurrentTrade.doTrade.setBackgroundColor(Color.parseColor("#F6465D"))


        dataBindingCurrentTrade.doTrade.setText(R.string.textSell)

    }


    //when program has been suspend stop the api servis operations
    override fun onPause() {
        viewVisible = false
        handler.removeCallbacks(runnable)
        super.onPause()
    }


    // when program on resume start update service and do visible true
    override fun onResume() {
        viewVisible = true
        upDatePer5Sec()
        super.onResume()
    }


    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }


    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {


    }


    // this overrided fun for new tatal when we changing amunt  or when updated price from api
    override fun afterTextChanged(s: Editable?) {

        if (dataBindingCurrentTrade.coinAmount.text.toString() != "" &&
            dataBindingCurrentTrade.coinPrice.text.toString() != ""
        ) {
            val amount = dataBindingCurrentTrade.coinAmount.text.toString().toBigDecimal()
            val price = dataBindingCurrentTrade.coinPrice.text.toString().toBigDecimal()


            if (amount.toDouble() > 0.0 && price.toDouble() > 0.0) {
                val total = (amount * price).toString()
                dataBindingCurrentTrade.Total.setText(total)

            }
        } else {
            dataBindingCurrentTrade.Total.setText("")
        }
    }


}