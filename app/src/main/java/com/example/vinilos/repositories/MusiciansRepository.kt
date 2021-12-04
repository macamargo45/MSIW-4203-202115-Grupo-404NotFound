package com.example.vinilos.repositories

import android.app.Application
import android.util.Log
import com.example.vinilos.models.Musician
import com.example.vinilos.network.CacheManager
import com.example.vinilos.network.NetworkServiceAdapter
import java.util.*

class MusiciansRepository(val application: Application) {
    suspend fun refreshData(): List<Musician>{
        val potentialResp = CacheManager.getInstance(application.applicationContext).getMusicians("allMusicians")
        val compareDate = CacheManager.getInstance(application.applicationContext).getDate()
        val dateInSecs: Long = compareDate.timeInMillis
        val dateExpiration = Date(dateInSecs + 10 * 60 * 1000)
        val now: Calendar = Calendar.getInstance()
        //Determinar la fuente de datos que se va a utilizar. Si es necesario consultar la red, ejecutar el siguiente código
        return if(potentialResp.isEmpty() || now.time >=dateExpiration) {
            val musician = NetworkServiceAdapter.getInstance(application).getMusicians()
            CacheManager.getInstance(application.applicationContext).addMusicians("allMusicians",musician,now)
            musician
        } else{
            Log.d("Cache decision", "return MUSICIANS ${potentialResp.size} elements from cache")
            potentialResp
        }
    }

    suspend fun addAlbumToMusician(idAlbum: Int, idMusician: Int): Int{
        val albumId = NetworkServiceAdapter.getInstance(application).addAlbumToMusician(idAlbum, idMusician)
        // Remove cache so that next query updates the albums list from the network
        CacheManager.getInstance(application.applicationContext).removeMusician("allMusicians")
        return albumId
    }
}