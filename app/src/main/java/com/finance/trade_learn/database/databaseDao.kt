package com.finance.trade_learn.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.finance.trade_learn.database.dataBaseEntities.myCoins
import com.finance.trade_learn.database.dataBaseEntities.SaveCoin

@Dao
interface databaseDao {

    // ------ operations of sell and buy
    @Insert
    suspend fun addCoin(myCoins: myCoins)


    @Query("select * from myCoins")
    suspend fun getAllCoins(): List<myCoins>


    @Query("select * from myCoins where CoinName=:coinName ")
    suspend fun getOneCoin(coinName: String): myCoins

    // this fun will return that it  constraint
    @Query("select * from myCoins where CoinName LIKE '%' || :firstName || '%'")
    suspend fun getConstraintCoin(firstName: String): List<myCoins>


    @Query("select * from myCoins where CoinName=:coinName ")
    suspend fun getOnCoinForTrade(coinName: String): myCoins?


    @Update
    suspend fun updateCoin(myCoins: myCoins)


    // ------ operations of save of trade
    @Insert
    suspend fun addTrade(trade: SaveCoin)

    @Query("select * from SaveCoin")
    suspend fun getAllTrades(): List<SaveCoin>


}