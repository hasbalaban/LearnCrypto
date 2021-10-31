package com.finance.trade_learn.database.dataBaseEntities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "myCoins")
data class myCoins(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "CoinName")
    var CoinName: String="",

    @ColumnInfo(name = "CoinAmount")
    var CoinAmount: Double


)