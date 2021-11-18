package com.example.vinilos.repositories

import android.app.Application
import com.example.vinilos.models.Musician
import com.example.vinilos.network.NetworkServiceAdapter

class MusiciansRepository(val application: Application) {
    suspend fun refreshData(): List<Musician>{
        //Determinar la fuente de datos que se va a utilizar. Si es necesario consultar la red, ejecutar el siguiente código
        return NetworkServiceAdapter.getInstance(application).getMusicians()
    }
}