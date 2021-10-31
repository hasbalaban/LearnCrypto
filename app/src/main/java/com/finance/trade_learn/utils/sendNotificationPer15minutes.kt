package com.finance.trade_learn.utils

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.work.Worker
import androidx.work.WorkerParameters

class sendNotificationPer15minutes(
    val context: Context,
    workerParams: WorkerParameters
) : Worker(context, workerParams) {
    override fun doWork(): Result {
       val number = operation()
        Log.i("workerManager", number.toString())

        return Result.success()
    }

    fun operation() : Int{
        val sharedManager = sharedPreferencesManager(context)
        var number = sharedManager.getSharedPreferencesInt("number", 0)
        number++
        sharedManager.addSharedPreferencesInt("number", number)
        return number

    }
}