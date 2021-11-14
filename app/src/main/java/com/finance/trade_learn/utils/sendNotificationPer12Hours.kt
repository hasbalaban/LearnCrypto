package com.finance.trade_learn.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationManagerCompat
import androidx.work.*
import com.finance.trade_learn.R
import com.finance.trade_learn.ctryptoApi.cryptoService
import com.finance.trade_learn.models.on_crypto_trade.BaseModelOneCryptoModel
import com.finance.trade_learn.view.MainActivity
import com.finance.trade_learn.viewModel.viewModelCurrentTrade
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class SendNotificationPer12Hours(
    val context: Context,
    workerParams: WorkerParameters
) : Worker(context, workerParams) {
    override fun doWork(): Result {
        val number = operation()
        Log.i("workerManager", number.toString())
        getData()
        return Result.success()
    }

    fun operation(): Int {
        val sharedManager = sharedPreferencesManager(context)
        var number = sharedManager.getSharedPreferencesInt("number", 0)
        number++
        sharedManager.addSharedPreferencesInt("number", number)
        return number

    }


    fun createNotification(coinName: String, price: String) {
        val Channel_Id = "1"
        val Channel_Name = "Coin Price Notifications"

        Log.i("version", Build.VERSION.SDK_INT.toString())
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Log.i("version is equals or bigger", Build.VERSION.SDK_INT.toString())

            val priority = NotificationManager.IMPORTANCE_HIGH
            val NotifManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            val channel = NotificationChannel(Channel_Id, Channel_Name, priority)

            NotifManager.createNotificationChannel(channel)
            val notificationSettings = Notification.Builder(context, Channel_Id)

            val intent = Intent(context,MainActivity::class.java)
            val pendingIntent= PendingIntent.getActivity(context,0,intent,0)

            notificationSettings.setSubText("Learn Trade")
                .setContentTitle("Coin Name: $coinName")
                .setContentText("Price: $price")
                .setColor(555555)
                .setSmallIcon(R.drawable.coin)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)

            val notification = NotificationManagerCompat.from(context)
            notification.notify(1, notificationSettings.build())
        } else {

            Log.i("version is lower", Build.VERSION.SDK_INT.toString())


        }
    }

    fun getData() {

        val coinName = sharedPreferencesManager(context)
            .getSharedPreferencesString("coinName")
        val viewModel = viewModelCurrentTrade(context)
        viewModel.getSelectedCoinDetails(coinName)

        CoroutineScope(Dispatchers.IO).launch {

            cryptoService().selectedCoinToTrade(coinName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object :
                    DisposableSingleObserver<List<BaseModelOneCryptoModel>>() {

                    override fun onSuccess(t: List<BaseModelOneCryptoModel>) {

                        createNotification(t[0].symbol, t[0].price)

                    }

                    override fun onError(e: Throwable) {
                        Log.i("hatahata", e.message.toString())
                    }


                })
        }


        //   viewModel.selectedCoinToTradeDetails.observe(

    }
}


// this will change because we use this fun for tests...
fun testWorkManager() {
    val constraint = Constraints.Builder()
        .setRequiresBatteryNotLow(true)
        .build()

    val myWorkRequest: WorkRequest =
        PeriodicWorkRequestBuilder<SendNotificationPer12Hours>(12, TimeUnit.HOURS)
            .setConstraints(constraint)
            .build()
    WorkManager.getInstance().enqueue(myWorkRequest)

/*
    val myWorkRequest1: WorkRequest = OneTimeWorkRequestBuilder<SendNotificationPer12Hours>()
        .setConstraints(constraint)
        .build()
    //  WorkManager.getInstance().enqueue(myWorkRequest1)

 */
}