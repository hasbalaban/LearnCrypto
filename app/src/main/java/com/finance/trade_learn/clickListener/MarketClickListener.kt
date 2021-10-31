package com.finance.trade_learn.clickListener

import android.view.View
import androidx.navigation.Navigation
import com.finance.trade_learn.view.marketPageDirections

class MarketClickListener( ):ListenerInterface {
    override fun ClickListener(view: View) {
        val directions = marketPageDirections.actionMarketPageToTradePage()
        Navigation.findNavController(view).navigate(directions)
    }
}