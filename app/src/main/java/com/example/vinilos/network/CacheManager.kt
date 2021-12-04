package com.example.vinilos.network

import android.content.Context
import com.example.vinilos.models.Album
import com.example.vinilos.models.Collector
import com.example.vinilos.models.Musician
import java.util.*

class CacheManager(context: Context) {

    companion object{
        private var instance: CacheManager? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: CacheManager(context).also {
                    instance = it
                }
            }
    }

    //CACHE PARA MANEJAR LA FECHA
    private var dateCache: HashMap<String, Calendar> = hashMapOf()
    fun getDate(): Calendar{
        val dateNow: Calendar = Calendar.getInstance()
        return if (dateCache.containsKey("now"))  dateCache["now"] !! else dateNow
    }

    //CAHCE PARA LOS ALBUMS
    private var albums: HashMap<String, List<Album>> = hashMapOf()

    fun addAlbums(key:String, album: List<Album>,dateNow:Calendar){
        albums[key] = album
        dateCache["now"] = dateNow

    }

    fun getAlbums(key:String) : List<Album>{
        return if (albums.containsKey(key)) albums[key]!! else listOf()
    }

    fun removeAllAlbums(key:String) {
        if (albums.containsKey(key))
            albums.remove(key)
    }

    //CAHCE PARA LOS MUSICOS
    private var musicians: HashMap<String, List<Musician>> = HashMap()
    fun addMusicians(key:String, musician: List<Musician>,dateNow:Calendar){

        musicians[key] = musician
        dateCache["now"] = dateNow

    }

    fun getMusicians(key:String) : List<Musician>{
        return if (musicians.containsKey(key)) musicians[key]!! else listOf()
    }

    fun removeMusician(key:String) {
        if (musicians.containsKey(key))
            musicians.remove(key)
    }

    //CAHCE PARA LOS coleccionistas
    private var collectors: HashMap<String, List<Collector>> = HashMap()
    fun addCollectors(key:String, collector: List<Collector>,dateNow:Calendar){

        collectors[key] = collector
        dateCache["now"] = dateNow

    }

    fun getCollectors(key:String) : List<Collector>{
        return if (collectors.containsKey(key)) collectors[key]!! else listOf()
    }

}