package com.example.currencyexchanger.model

import android.content.Context
import android.os.Handler
import android.os.Looper
import androidx.room.Room
import com.example.currencyexchanger.App
import com.example.currencyexchanger.model.db.AppDB
import com.example.currencyexchanger.model.network.NetworkService
import com.example.currencyexchanger.model.pojo.ValuteInfo
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import java.util.*

class Storage private constructor(context: Context){

    private var storage: ValuteInfo? = null
    private val timer: Timer = Timer(Runnable { refreshData() }, 5 * 60000)
    private val listeners: LinkedList<AutoDataUpdateNotificationsListener> = LinkedList()
    private val localDBConnection: AppDB =
        Room.databaseBuilder(context, AppDB::class.java, "db")
            .fallbackToDestructiveMigration()
            .build()

    init {
        timer.start()
    }

    fun subscribeOnAutoUpdNotifications(listener: AutoDataUpdateNotificationsListener) {
        listeners.add(listener)
    }

    interface AutoDataUpdateNotificationsListener {
        fun onStorageAutomaticallyUpdated()
    }

    fun getData(): ValuteInfo {
        if (storage == null) {
            val deferred = GlobalScope.async {
                val localData =
                    localDBConnection
                    .appDao()
                    .getValuteInfo()
                if (localData == null) {
                    storage = loadActualValutesInfo()
                    storage?.let{ localDBConnection.appDao().insert(it) }
                }
                else {
                    storage = localData
                }
            }
            runBlocking { deferred.await() }
        }
        return storage!!
    }

    private suspend fun loadActualValutesInfo() =
        NetworkService.instance
        .getAPI()
        .getActualValuteInfo()

    private fun notifyDataupdated() {
        listeners.forEach{ it.onStorageAutomaticallyUpdated() }
    }

    fun refreshData() {
        GlobalScope.async {
            val actualData = loadActualValutesInfo()
            if (actualData != storage) {
                localDBConnection
                    .appDao()
                    .update(actualData)
                storage = actualData
            }
            notifyDataupdated()
        }
    }

    companion object {
        val instance = Storage(App.getAppContext())
    }

    class Timer(val task: Runnable, val interval: Long) {

        private val handler = Handler(Looper.getMainLooper())
        private var isStopped = true

        private val mainTask = object : Runnable{
            override fun run() {
                if (!isStopped) {
                    task.run()
                    handler.postDelayed(this, interval)
                }
            }
        }

        fun start() {
            isStopped = false
            handler.postDelayed(mainTask, 0)
        }

        fun stop() {
            isStopped = true
        }
    }
}