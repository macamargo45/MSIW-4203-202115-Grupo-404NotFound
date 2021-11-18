package com.example.vinilos.repositories

import android.app.Application
import com.example.vinilos.models.Album
import com.example.vinilos.network.NetworkServiceAdapter

class AlbumRepository(val application: Application) {
    suspend fun refreshData(): List<Album>{
        //Determinar la fuente de datos que se va a utilizar. Si es necesario consultar la red, ejecutar el siguiente código
        return NetworkServiceAdapter.getInstance(application).getAlbums()
    }
}