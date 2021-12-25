package com.finance.trade_learn.utils

import android.util.Log

class DifferentItems<T>(private val oldItem: List<T>, private val newItem: List<T>) {

    fun comporeItems(): List<ResultCompare<T>> {
        val newList: ArrayList<ResultCompare<T>> = ArrayList()
        newList.clear()

        val all_Items = NewAndLastItems<T>(newItem, oldItem)

        if (all_Items.newItem.size == all_Items.oldItem.size
            && all_Items.oldItem.size > 1
            && all_Items.newItem.size > 1
        ) {

            for (position in 0..newItem.size-1) {

                if (all_Items.newItem[position] == all_Items.oldItem[position]) {
                    val resultCompare = ResultCompare<T>(all_Items.newItem[position], true,position)
                    newList.add(resultCompare)

                } else {
                    val ResultCompare = ResultCompare<T>(all_Items.newItem[position], false,position)
                    newList.add(ResultCompare)
                }


            }


        } else if (newItem.isNotEmpty()) {
            for (position in 0..newItem.size-1) {
                if (position <= all_Items.oldItem.size) {


                    val resultCompare = ResultCompare<T>(all_Items.newItem[position], false,position)
                    newList.add(resultCompare)

                }


            }

        } else if (oldItem.isNotEmpty()) {
            for (position in 0..all_Items.newItem.size-1) {



                val resultCompare = ResultCompare<T>(all_Items.oldItem[position], true,position)
                newList.add(resultCompare)
            }
        }

        return newList

    }
}

data class NewAndLastItems<T>(val newItem: List<T>, val oldItem: List<T>)
data class ResultCompare<T>(val comparedList: T, val equals: Boolean, val position :Int)

