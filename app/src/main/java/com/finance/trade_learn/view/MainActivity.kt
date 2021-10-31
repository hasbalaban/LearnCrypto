package com.finance.trade_learn.view


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.work.*
import com.finance.trade_learn.R
import com.finance.trade_learn.databinding.ActivityMainBinding
import com.finance.trade_learn.utils.sendNotificationPer15minutes
import com.finance.trade_learn.viewModel.viewModelUtils
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.TimeUnit


var isCameFromActivity = false

class MainActivity : AppCompatActivity() {
    lateinit var controller: NavController
    lateinit var dataBindingMain: ActivityMainBinding
    lateinit var viewModelUtils: viewModelUtils
    val disposable = CompositeDisposable()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBindingMain = DataBindingUtil.setContentView(this, R.layout.activity_main)


        BottomNavigationItemClickListener()

        isOneEntering()

        testWorkManager()


    }


    fun BottomNavigationItemClickListener() {

        controller = findNavController(R.id.fragmentContainerView)
        dataBindingMain.options.setupWithNavController(controller)


        if (isCameFromActivity) {
            val directions = homeDirections.actionHomeToTradePage()
            controller.navigate(directions)
        }


    }

    //check is first entering or no ? // if it's first time add 1000 dollars
    fun isOneEntering() {
        viewModelUtils = viewModelUtils()
        viewModelUtils.isOneEntering(this)

    }


    override fun onRestart() {


        Log.i("destttt", controller.currentDestination?.label.toString())
        if (isCameFromActivity) {
            isCameFromActivity = false

            val currentDirections = controller.currentDestination?.label.toString()


            when (currentDirections) {
                "marketPage" -> {
                    val directions = marketPageDirections.actionMarketPageToTradePage()
                    controller.navigate(directions)
                }
                "home" -> {
                    val directions = homeDirections.actionHomeToTradePage()
                    controller.navigate(directions)

                }
                else -> Toast.makeText(this, "there are a error at background", Toast.LENGTH_SHORT)
                    .show()


            }
        }
        super.onRestart()
    }


    // this will change because we use this fun for tests...
    fun testWorkManager() {
        val constraint = Constraints.Builder()
            .setRequiresBatteryNotLow(true)
            .build()

        val myWorkRequest: WorkRequest =
            PeriodicWorkRequestBuilder<sendNotificationPer15minutes>(15, TimeUnit.MINUTES)
                .setConstraints(constraint)
                .build()


        val myWorkRequest1:WorkRequest= OneTimeWorkRequestBuilder<sendNotificationPer15minutes>()
            .setConstraints(constraint)
            .build()
        WorkManager.getInstance().enqueue(myWorkRequest)
        WorkManager.getInstance().enqueue(myWorkRequest1)
    }

}