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
import androidx.lifecycle.Observer
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
    var viewVisible = true
    var currentPrice = 0.0
    var runnable = Runnable { }
    var handler = Handler(Looper.getMainLooper())

    lateinit var dataBindingCurrentTrade: FragmentCurrentTradeBinding
    private var tradeState = tradeEnum.Buy
    private lateinit var viewModelCurrentTrade: viewModelCurrentTrade
    var coinName = "BTC"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
    fun setup() {

        toast = Toast.makeText(requireContext(), "", Toast.LENGTH_SHORT)

        viewModelCurrentTrade = viewModelCurrentTrade(requireContext())
        setInitialize()
        dataBindingCurrentTrade.coinName.setText(coinName + " / USDT")
        dataBindingCurrentTrade.clickLisener = ClickListenerInitialize
        dataBindingCurrentTrade.coinAmount.addTextChangedListener(this)
        dataBindingCurrentTrade.coinPrice.addTextChangedListener(this)
        getDetailsOfCoinFromDatabase()
        startAnimation()
    }


    //first start this to get name of we had clicked
    fun setInitialize() {

        coinName = sharedPreferencesManager(requireContext())
            .getSharedPreferencesString("coinName")

        seekBarsProgress()

    }


    //animation to start
    fun startAnimation() {
        val animationBuy =
            AnimationUtils.loadAnimation(requireContext(), R.anim.animation_for_buy_button)
        val buyButton = dataBindingCurrentTrade.Buy
        buyButton.animation = animationBuy

        val animationSell =
            AnimationUtils.loadAnimation(requireContext(), R.anim.animation_for_sell_button)
        val sellButton = dataBindingCurrentTrade.Sell
        sellButton.animation = animationSell

        val animation_to_right =
            AnimationUtils.loadAnimation(requireContext(), R.anim.anime_to_right)

        val coin_rice = dataBindingCurrentTrade.coinPrice
        animation_to_right.duration = 400
        coin_rice.animation = animation_to_right

        val relayout_amount = dataBindingCurrentTrade.relayoutAmount
        animation_to_right.duration = 700
        relayout_amount.animation = animation_to_right

        val total = dataBindingCurrentTrade.Total
        animation_to_right.duration = 1000
        total.animation = animation_to_right

        val relayout_avaible = dataBindingCurrentTrade.relayoutAvaible
        animation_to_right.duration = 500
        relayout_avaible.animation = animation_to_right

        val do_trade_button = dataBindingCurrentTrade.doTrade
        animation_to_right.duration = 500
        do_trade_button.animation = animation_to_right

        val history_of_trade = dataBindingCurrentTrade.historyOfTrade
        animation_to_right.duration = 500
        history_of_trade.animation = animation_to_right

    }


    // this function manager time to get data in per 5 seek.
    fun upDatePer5Sec() {
        runnable = object : Runnable {
            override fun run() {

                //call this function for update
                getDataFromApi()
                handler.postDelayed(runnable, 3000)
            }


        }
        handler.post(runnable)


    }


    // we getting data from database when fragment starting and after any trade
    fun getDetailsOfCoinFromDatabase(coinName: String = "USDT") {
        viewModelCurrentTrade.getDetailsOfCoinFromDatabase(coinName)
        if (viewVisible) {
            viewModelCurrentTrade.coinAmountLiveData.observe(viewLifecycleOwner, Observer {
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
    fun getDataFromApi() {
        viewModelCurrentTrade.GetSelectedCoinDetails(coinName)
        if (viewVisible) {
            viewModelCurrentTrade.selectedCoinToTradeDetails.observe(
                viewLifecycleOwner,
                Observer { coin ->
                    currentPrice = coin[0].price
                    putDataInItemSettings(coin[0])


                    // after it get data from api initialize max of seek bar
                    maxOfSeekBar()


                })


        }

    }

    // this fun for binding of fata - change percente/price etc.
    fun putDataInItemSettings(coin: BaseModelOneCryptoModel) {

        try {
            val coinPrice = coin.price.toString()+"0"
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
                    dataBindingCurrentTrade.coinChangePercent.setText("Hata!")
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
    fun toastMessages(messages: String = "") {
        if (messages != "") {
            toast.cancel()
            toast = Toast.makeText(requireContext(), messages, Toast.LENGTH_SHORT)
            //   toast.cancel()
            toast.show()
        }
    }


    // listener override here and initialize
    val ClickListenerInitialize = object : ListenerInterface {
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
                            toastMessages("Lütfen Geçerli bir değer giriniz")

                        }
                    }
                    // if amount equals zero (0) show messages
                    else {
                        toastMessages("Lütfen Geçerli bir değer giriniz")

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
                            toastMessages("İşlem başarısız. Bakiyeyi kontrol et.")
                        }

                    } else {
                        toastMessages("please enter amount")
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
    fun seekBarsProgress() {

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
                            dataBindingCurrentTrade.Total.setText("0.000000")
                        }


                    }
                    tradeEnum.Sell -> {

                        dataBindingCurrentTrade.percentOfAvaible.max = maxOfSeekBar()
                        if (percentAmount.toString() == "0") {
                            dataBindingCurrentTrade.Total.setText("0.000000")
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

            when (tradeState) {
                tradeEnum.Buy -> {
                    operationState = avaibleAmount.toDouble() >= total.toDouble()
                }
                tradeEnum.Sell -> {
                    operationState = avaibleAmount.toDouble() >= amount.toDouble()
                }

            }

        }

        return operationState

    }


    // this fun for trade operation - buy , sell operation etc.
    fun operationTrade() {
        Log.i("den1", "1")
        when (tradeState) {
            tradeEnum.Buy -> {
                Log.i("den1", "4")
                val Amount = dataBindingCurrentTrade.coinAmount.text.toString()
                val Price = dataBindingCurrentTrade.coinPrice.text.toString()
                if (Amount != "" && Price != "") {

                    val coinAmount = Amount.toDouble()
                    val coinPrice = currentPrice
                    val total = coinAmount * coinPrice

                    viewModelCurrentTrade.buyCoin(coinName, coinAmount, total,currentPrice)
                    viewModelCurrentTrade.state.observe(viewLifecycleOwner, Observer {
                        if (it) {
                            getDetailsOfCoinFromDatabase()
                            toastMessages("İşlem başarılı")

                        } else {
                            toastMessages("İşlem başarısız...")
                        }
                    })

                }


            }
            tradeEnum.Sell -> {
                Log.i("den1", "2")
                val Amount = dataBindingCurrentTrade.coinAmount.text.toString()
                val Price = dataBindingCurrentTrade.coinPrice.text.toString()
                if (Amount != "" && Price != "") {

                    val coinAmount = Amount.toDouble()
                    val coinPrice = currentPrice
                    val total = coinAmount * coinPrice

                    Log.i("den1", "3")
                    viewModelCurrentTrade.sellCoin(coinName, coinAmount, total,currentPrice)
                    viewModelCurrentTrade.state.observe(viewLifecycleOwner, Observer {
                        Log.i("den1", "4")
                        if (it) {
                            getDetailsOfCoinFromDatabase(coinName)
                            toastMessages("İşlem başarılı..")

                        } else {
                            toastMessages("İşlem başarısız..")
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
        dataBindingCurrentTrade.doTrade.setText("Buy")
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


        dataBindingCurrentTrade.doTrade.setText("Sell")

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
            val amount = dataBindingCurrentTrade.coinAmount.text.toString().toDouble()
            val price = dataBindingCurrentTrade.coinPrice.text.toString().toDouble()



            if (amount > 0.0 && price > 0.0) {
                val total = (amount * price).toString()
                dataBindingCurrentTrade.Total.setText(total)

            }
        } else {
            dataBindingCurrentTrade.Total.setText("")
        }
    }


}