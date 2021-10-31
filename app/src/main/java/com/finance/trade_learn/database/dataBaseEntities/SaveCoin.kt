package com.finance.trade_learn.database.dataBaseEntities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.finance.trade_learn.enums.tradeEnum

@Entity(tableName = "SaveCoin")
data class SaveCoin(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "tradeId")
    val tradeId: Int = 0,
    @ColumnInfo(name = "coinName")
    val coinName: String,
    @ColumnInfo(name = "coinAmount")
    val coinAmount: String,
    @ColumnInfo(name = "coinPrice")
    val coinPrice: String,
    @ColumnInfo(name = "total")
    val total: String,
    @ColumnInfo(name = "date")
    val date: String,
    // tradeEnum: tradeEnum
    @ColumnInfo(name = "tradeOperation")
    val tradeOperation: String

)