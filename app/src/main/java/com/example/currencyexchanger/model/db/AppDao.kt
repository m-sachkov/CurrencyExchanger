package com.example.currencyexchanger.model.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.currencyexchanger.model.pojo.ValuteInfo

@Dao
interface AppDao {
    @Query("SELECT * FROM ValuteInfo")
    fun getValuteInfo(): ValuteInfo?

    @Insert
    fun insert(valuteInfo: ValuteInfo)

    @Update
    fun update(valuteInfo: ValuteInfo)

    @Query("DELETE FROM ValuteInfo")
    fun delete()
}