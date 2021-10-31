package com.finance.trade_learn.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat

class IntentNavigate {
    fun navigate(context: Context, classs: Class<*>) {

        val intent = Intent(context, classs)
        val bundle = Bundle()
        ContextCompat.startActivity(context, intent, bundle)


    }

}