package com.finance.trade_learn.utils

import android.util.Log
import java.util.*
import kotlin.collections.ArrayList

class newApiKey {
    val API_KEYS = ArrayList<String>()

    fun create(): String {

        val list_of_key = keys()
        val random = (0 until list_of_key.size).random()

        Log.i("random",list_of_key[random])
        return list_of_key[random]

    }

    fun keys(): ArrayList<String> {
        API_KEYS.add("93559813d49113a1760f99eea8b59722edbca326")
        API_KEYS.add("7ace70140d04dbd00b567ca1f52452aa202abbc9")
        API_KEYS.add("df3fcce35c336bc991696621cfae6b4f")
        API_KEYS.add("c9490a962892d1784041d91dfb754ec0f99d153e")
        API_KEYS.add("454aadf31dea285189e4ca4e06a7d0d3d2f5264d")
        API_KEYS.add("502a832ef6f3d0b36068f3ae0175959ba899e677")
        API_KEYS.add("7aaa0ea3f9084f643abf71f0080d01ac2dca8f19")
        API_KEYS.add("8a0f15cae34634cdc03898b4c585a156ca974df0")
        API_KEYS.add("70c78f9c149e61d79c81db0ea7748a262c0cbc63")
        API_KEYS.add("2c19b8fce8aa7eb5dd33bd08063fc33c01cace1d")
        return API_KEYS
    }
}