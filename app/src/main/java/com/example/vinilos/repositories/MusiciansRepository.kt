package com.example.vinilos.repositories

import android.app.Application
import android.util.Log
import com.example.vinilos.models.Musician
import com.example.vinilos.network.CacheManager
import com.example.vinilos.network.NetworkServiceAdapter
import java.util.*

class MusiciansRepository(val application: Application) {
    suspend fun refreshData(): List<Musician>{
        var potentialResp = CacheManager.getInstance(application.applicationContext).getMusicians("allMusicians")
        var compareDate = CacheManager.getInstance(application.applicationContext).getDate()
        val dateInSecs: Long = compareDate.getTimeInMillis()
        val dateExpiration = Date(dateInSecs + 10 * 60 * 1000)
        var now: Calendar = Calendar.getInstance()
        //Determinar la fuente de datos que se va a utilizar. Si es necesario consultar la red, ejecutar el siguiente código
        if(potentialResp.isEmpty() || now.getTime()>=dateExpiration) {
            var musician = NetworkServiceAdapter.getInstance(application).getMusicians()
            CacheManager.getInstance(application.applicationContext).addMusicians("allMusicians",musician,now)
            return musician
        }
        else{
            Log.d("Cache decision", "return MUSICIANS ${potentialResp.size} elements from cache")
            return potentialResp
        }
    }
}