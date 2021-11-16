package com.finance.trade_learn.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.finance.trade_learn.Adapters.adapter_for_search_coin
import com.finance.trade_learn.data.Cache_Data
import com.finance.trade_learn.R
import com.finance.trade_learn.databinding.ActivitySearchBinding
import kotlinx.coroutines.*
import java.util.*

class SearchActivity : AppCompatActivity(), TextWatcher {


    var job = arrayListOf<Job>()
    lateinit var adapter: adapter_for_search_coin
    lateinit var dataBindingSearch: ActivitySearchBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.thema_search)
        super.onCreate(savedInstanceState)
        dataBindingSearch = DataBindingUtil.setContentView(this, R.layout.activity_search)
       // setTitle(R.string.page_name)

        setup()
    }

    fun setup() {
        adapter = adapter_for_search_coin(arrayListOf(), this,this)
        setAdapter()
        dataBindingSearch.searchCoin.addTextChangedListener(this)
    }

    fun setAdapter() {
        dataBindingSearch.searchedCoins.layoutManager = LinearLayoutManager(this)
        dataBindingSearch.searchedCoins.adapter = adapter
    }

    fun getEqualCoins(): List<String> {
        val listCoin = Cache_Data().coinsName()
        val textOfQuery = dataBindingSearch.searchCoin.text.toString()
            .uppercase(Locale.getDefault())

        val queryList = listCoin.filter {
            it.startsWith(textOfQuery)
        }

        return queryList

    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        if(s.toString()!= s.toString().uppercase(Locale.getDefault())){

            dataBindingSearch.searchCoin.setText(dataBindingSearch.searchCoin.text.toString()
                .uppercase(Locale.getDefault()))
        }

        job.clear()
    }

    override fun afterTextChanged(s: Editable?) {
        if (dataBindingSearch.searchCoin.text.toString() != "") {

            val newJob = CoroutineScope(Job() + Dispatchers.IO).launch {
                val newList = getEqualCoins()
                withContext(Dispatchers.Main) {
                    adapter.updateAdapterSearchCoin(newList)
                }
            }
            job.add(newJob)
        }

        val edittextCursorPosition =  dataBindingSearch.searchCoin.length()
        dataBindingSearch.searchCoin.setSelection(edittextCursorPosition)
    }



}
