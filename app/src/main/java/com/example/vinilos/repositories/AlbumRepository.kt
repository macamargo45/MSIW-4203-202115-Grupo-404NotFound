package com.example.vinilos.repositories

import android.app.Application
import android.util.Log
import com.example.vinilos.models.Album
import com.example.vinilos.network.CacheManager
import com.example.vinilos.network.NetworkServiceAdapter
import java.util.*

class AlbumRepository(val application: Application) {
    suspend fun refreshData(): List<Album>{
        val potentialResp = CacheManager.getInstance(application.applicationContext).getAlbums("allAlbums")
        val compareDate = CacheManager.getInstance(application.applicationContext).getDate()
        val dateInSecs: Long = compareDate.timeInMillis
        val dateExpiration = Date(dateInSecs + 10 * 60 * 1000)
        val now: Calendar = Calendar.getInstance()


        //Determinar la fuente de datos que se va a utilizar. Si es necesario consultar la red, ejecutar el siguiente código
        return if(potentialResp.isEmpty() || now.time >=dateExpiration){
            Log.d("LLAMAR END POINT", "CALLLLLLLLLLLLLLLLLL")
            //return NetworkServiceAdapter.getInstance(application).getAlbums()
            val albums = NetworkServiceAdapter.getInstance(application).getAlbums()
            CacheManager.getInstance(application.applicationContext).addAlbums("allAlbums",albums,now)
            albums
        } else{
            Log.d("Cache decision", "return ${potentialResp.size} elements from cache")
            potentialResp
        }
    }
}