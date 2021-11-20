package com.example.vinilos.network

import android.content.Context
import com.example.vinilos.models.Album
import com.example.vinilos.models.Collector
import com.example.vinilos.models.Musician
import java.util.*

class CacheManager(context: Context) {

    companion object{
        var instance: CacheManager? = null
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
        var dateNow: Calendar = Calendar.getInstance()
        return if (dateCache.containsKey("now"))  dateCache["now"] !! else dateNow
    }

    //CAHCE PARA LOS ALBUMS
    private var albums: HashMap<String, List<Album>> = hashMapOf()

    fun addAlbums(key:String, album: List<Album>,dateNow:Calendar){
        albums[key] = album
        dateCache.put("now",dateNow)

    }

    fun getAlbums(key:String) : List<Album>{
        return if (albums.containsKey(key)) albums[key]!! else listOf<Album>()
    }

    //CAHCE PARA LOS MUSICOS
    private var musicians: HashMap<String, List<Musician>> = HashMap()
    fun addMusicians(key:String, musician: List<Musician>,dateNow:Calendar){

        musicians[key] = musician
        dateCache.put("now",dateNow)

    }

    fun getMusicians(key:String) : List<Musician>{
        return if (musicians.containsKey(key)) musicians[key]!! else listOf<Musician>()
    }

    //CAHCE PARA LOS coleccionistas
    private var collectors: HashMap<String, List<Collector>> = HashMap()
    fun addCollectors(key:String, collector: List<Collector>,dateNow:Calendar){

        collectors.put(key, collector)
        dateCache.put("now",dateNow)

    }

    fun getCollectors(key:String) : List<Collector>{
        return if (collectors.containsKey(key)) collectors[key]!! else listOf<Collector>()
    }

    //GUARDAR EL DETALLE DE UN MUSICO
    private var musician: HashMap<Int, Musician> = HashMap()
    fun addMusician(key:Int, mus: Musician,dateNow:Calendar){
        musician.put(key,mus)
        dateCache.put("now",dateNow)
    }
    fun getMusician(key:Int) : Musician?{
        return if (musician.containsKey(key)) musician[key]!! else null
    }

}