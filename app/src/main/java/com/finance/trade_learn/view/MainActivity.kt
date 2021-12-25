package com.finance.trade_learn.view


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.finance.trade_learn.R
import com.finance.trade_learn.databinding.ActivityMainBinding
import com.finance.trade_learn.utils.testWorkManager
import com.finance.trade_learn.viewModel.ViewModelMarket
import com.finance.trade_learn.viewModel.viewModelUtils
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*


var isCameFromActivity = false

class MainActivity : AppCompatActivity() {
    private lateinit var controller: NavController
    private lateinit var dataBindingMain: ActivityMainBinding
    private lateinit var viewModelUtils: viewModelUtils
    private lateinit var viewModelMarket: ViewModelMarket


    private lateinit var firestore: FirebaseFirestore

    // val disposable = CompositeDisposable()
    override fun onCreate(savedInstanceState: Bundle?) {
        providers()
        super.onCreate(savedInstanceState)
        dataBindingMain = DataBindingUtil.setContentView(this, R.layout.activity_main)


        bottomNavigationItemClickListener()

        isOneEntering()


    }


   private fun providers() {
        viewModelMarket = ViewModelProvider(this).get(ViewModelMarket::class.java)

    }


    // to navigate according click in fragment
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

        Log.i("destiniation", controller.currentDestination?.label.toString())
        if (isCameFromActivity) {
            isCameFromActivity = false


            when (controller.currentDestination?.label.toString()) {
                "marketPage" -> {
                    val directions = MarketPageDirections.actionMarketPageToTradePage()
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


    fun firebaseSave(){

        firestore = Firebase.firestore

        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        val currentDate = sdf.format(Date())

        val openAppDetails = hashMapOf(
            "open" to "1",
            "time" to currentDate,
            "country" to Locale.getDefault().country
        )

        firestore.collection("StartApp").add(openAppDetails).addOnSuccessListener {
            Toast.makeText(this, "opened page", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {

            Toast.makeText(this, "opened page failed", Toast.LENGTH_SHORT).show()
        }
    }


}