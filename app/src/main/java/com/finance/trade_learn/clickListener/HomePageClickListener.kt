package com.finance.trade_learn.clickListener

import android.view.View
import androidx.navigation.Navigation
import com.finance.trade_learn.view.homeDirections

class HomePageClickListener ( ):ListenerInterface {
    override fun ClickListener(view: View) {
        val directions = homeDirections.actionHomeToTradePage()
        Navigation.findNavController(view).navigate(directions)
    }
}