package com.finance.trade_learn.view


import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.finance.trade_learn.R
import com.finance.trade_learn.databinding.ActivityMainBinding
import com.finance.trade_learn.utils.testWorkManager
import com.finance.trade_learn.viewModel.viewModelUtils


var isCameFromActivity = false

class MainActivity : AppCompatActivity(){
    private lateinit var controller: NavController
    private lateinit var dataBindingMain: ActivityMainBinding
    private lateinit var viewModelUtils: viewModelUtils

    // val disposable = CompositeDisposable()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBindingMain = DataBindingUtil.setContentView(this, R.layout.activity_main)


        bottomNavigationItemClickListener()

        isOneEntering()


    }


    // to navigate acording click in fragment
    private fun bottomNavigationItemClickListener() {

        controller = findNavController(R.id.fragmentContainerView)
        dataBindingMain.options.setupWithNavController(controller)


        if (isCameFromActivity) {
            val directions = homeDirections.actionHomeToTradePage()
            controller.navigate(directions)
        }


    }

    //check is first entering or no ? // if it's first time add 1000 dollars
    private fun isOneEntering() {
        viewModelUtils = viewModelUtils()
        val state = viewModelUtils.isOneEntering(this)
        if (state) {
            // these functions just for test
            testWorkManager()
            Log.i("first", "this is first Entering")
        } else
            Log.i("firstNot", "this is not first Entering")

    }


    override fun onRestart() {

        Log.i("destttt", controller.currentDestination?.label.toString())
        if (isCameFromActivity) {
            isCameFromActivity = false


            when (controller.currentDestination?.label.toString()) {
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


}