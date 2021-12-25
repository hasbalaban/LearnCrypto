package com.finance.trade_learn.clickListener

import android.view.View
import androidx.navigation.Navigation
import com.finance.trade_learn.view.MarketPageDirections

class MarketClickListener( ):ListenerInterface {
    override fun ClickListener(view: View) {
        val directions = MarketPageDirections.actionMarketPageToTradePage()
        Navigation.findNavController(view).navigate(directions)
    }
}