package com.example.vinilos.repositories

import android.app.Application
import android.util.Log
import com.example.vinilos.models.Collector
import com.example.vinilos.network.CacheManager
import com.example.vinilos.network.NetworkServiceAdapter
import java.util.*

class CollectorsRepository(val application: Application) {
    suspend fun refreshData(): List<Collector>{
        val potentialResp = CacheManager.getInstance(application.applicationContext).getCollectors("allCollectors")
        val compareDate = CacheManager.getInstance(application.applicationContext).getDate()
        val dateInSecs: Long = compareDate.timeInMillis
        val dateExpiration = Date(dateInSecs + 10 * 60 * 1000)
        val now: Calendar = Calendar.getInstance()
        //Determinar la fuente de datos que se va a utilizar. Si es necesario consultar la red, ejecutar el siguiente código
        return if(potentialResp.isEmpty() || now.time >=dateExpiration){
            val collectors =  NetworkServiceAdapter.getInstance(application).getCollectors()
            CacheManager.getInstance(application.applicationContext).addCollectors("allCollectors",collectors,now)
            collectors
        } else{
            Log.d("Cache decision", "return COLLECTORS ${potentialResp.size} elements from cache")
            potentialResp
        }
    }
}