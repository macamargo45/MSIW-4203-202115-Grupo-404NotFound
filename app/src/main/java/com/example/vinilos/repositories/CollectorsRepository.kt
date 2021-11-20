package com.example.vinilos.repositories

import android.app.Application
import android.util.Log
import com.example.vinilos.models.Collector
import com.example.vinilos.network.CacheManager
import com.example.vinilos.network.NetworkServiceAdapter
import java.util.*

class CollectorsRepository(val application: Application) {
    suspend fun refreshData(): List<Collector>{
        var potentialResp = CacheManager.getInstance(application.applicationContext).getCollectors("allCollectors")
        var compareDate = CacheManager.getInstance(application.applicationContext).getDate()
        val dateInSecs: Long = compareDate.getTimeInMillis()
        val dateExpiration = Date(dateInSecs + 10 * 60 * 1000)
        var now: Calendar = Calendar.getInstance()
        //Determinar la fuente de datos que se va a utilizar. Si es necesario consultar la red, ejecutar el siguiente código
        if(potentialResp.isEmpty() || now.getTime()>=dateExpiration){
            var collectors =  NetworkServiceAdapter.getInstance(application).getCollectors()
            CacheManager.getInstance(application.applicationContext).addCollectors("allCollectors",collectors,now)
            return collectors
        }
        else{
            Log.d("Cache decision", "return COLLECTORS ${potentialResp.size} elements from cache")
            return potentialResp
        }
    }
}