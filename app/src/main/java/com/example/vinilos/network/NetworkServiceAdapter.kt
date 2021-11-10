package com.example.vinilos.network

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.vinilos.models.*
import com.example.vinilos.util.EspressoIdlingResource
import org.json.JSONArray

class NetworkServiceAdapter constructor(context: Context) {

    companion object{
        const val BASE_URL= "https://vinilo-grupo-15.herokuapp.com/"
        var instance: NetworkServiceAdapter? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: NetworkServiceAdapter(context).also {
                    instance = it
                }
            }
    }

    private val requestQueue: RequestQueue by lazy {
        // applicationContext keeps you from leaking the Activity or BroadcastReceiver if someone passes one in.
        Volley.newRequestQueue(context.applicationContext)
    }

    fun getAlbums(onComplete:(resp:List<Album>)->Unit, onError: (error: VolleyError)->Unit){
        EspressoIdlingResource.increment()
        requestQueue.add(getRequest("albums",
            { response ->
                val resp = JSONArray(response)
                val list = mutableListOf<Album>()

                for (i in 0 until resp.length()) {
                    val item = resp.getJSONObject(i)

                    val performers = mutableListOf<Performer?>()
                    val jsonPerformers = item.getJSONArray("performers")

                    for (performerIndex in 0 until jsonPerformers.length()) {
                        val jsonPerformer = jsonPerformers.getJSONObject(performerIndex)

                        performers.add(performerIndex, Performer(
                            id = jsonPerformer.getInt("id"),
                            name = jsonPerformer.getString("name"),
                            image = jsonPerformer.getString("image"),
                            description = jsonPerformer.getString("description")
                        ))
                    }

                    list.add(i, Album(
                        albumId = item.getInt("id"),
                        name = item.getString("name"),
                        cover = item.getString("cover"),
                        recordLabel = item.getString("recordLabel"),
                        releaseDate = item.getString("releaseDate"),
                        genre = item.getString("genre"),
                        description = item.getString("description"),
                        performers = performers
                    ))
                }
                onComplete(list)
                EspressoIdlingResource.decrement()
            },
            {
                onError(it)
            }))
    }
    private fun getRequest(path:String, responseListener: Response.Listener<String>, errorListener: Response.ErrorListener): StringRequest {
        return StringRequest(Request.Method.GET, BASE_URL+path, responseListener,errorListener)
    }

    fun getCollectors(onComplete:(resp:List<Collector>)->Unit, onError: (error: VolleyError)->Unit){
        EspressoIdlingResource.increment()
        requestQueue.add(getRequest("collectors",
            { response ->
                val resp = JSONArray(response)
                val list = mutableListOf<Collector>()

                for (i in 0 until resp.length()) {
                    val item = resp.getJSONObject(i)

                    val favoritePerformers = mutableListOf<Performer?>()
                    val jsonPerformers = item.getJSONArray("favoritePerformers")

                    val collectorsAlbums = mutableListOf<CollectorAlbum?>()
                    val jsonCollectorsAlbums = item.getJSONArray("collectorAlbums")

                    for (performerIndex in 0 until jsonPerformers.length()) {
                        val jsonPerformer = jsonPerformers.getJSONObject(performerIndex)

                        favoritePerformers.add(performerIndex, Performer(
                            id = jsonPerformer.getInt("id"),
                            name = jsonPerformer.getString("name"),
                            image = jsonPerformer.getString("image"),
                            description = jsonPerformer.getString("description")
                        ))
                    }

                    for (albumIndex in 0 until jsonCollectorsAlbums.length()) {
                        val jsonCollectorAlbum = jsonCollectorsAlbums.getJSONObject(albumIndex)

                        collectorsAlbums.add(albumIndex, CollectorAlbum(
                            id = jsonCollectorAlbum.getInt("id"),
                            status = jsonCollectorAlbum.getString("status"),
                            price = jsonCollectorAlbum.getInt("price")
                        ))
                    }

                    list.add(i, Collector(
                        id = item.getInt("id"),
                        name = item.getString("name"),
                        telephone = item.getString("telephone"),
                        email = item.getString("email"),
                        favoritePerformers = favoritePerformers,
                        collectorAlbums = collectorsAlbums
                    )
                    )
                }
                onComplete(list)
                EspressoIdlingResource.decrement()
            },
            {
                onError(it)
            }))
    }

    //FUCNION PARA TRAER LOS MUSICOS DEL ENDPOINT

    fun getMusicians(onComplete:(resp:List<Musician>)->Unit, onError: (error: VolleyError)->Unit){
        EspressoIdlingResource.increment()
        requestQueue.add(getRequest("musicians",{ response ->

            val resp = JSONArray(response)
            val list = mutableListOf<Musician>()
            for (i in 0 until resp.length()) {
                val item = resp.getJSONObject(i)
                val albums = item.getJSONArray("albums")
                val albumsList = mutableListOf<Album?>()
                val performers = mutableListOf<Performer?>()

                for (albumIndex in 0 until albums.length()) {
                    val jsonAlbum = albums.getJSONObject(albumIndex)

                    albumsList.add(i, Album(
                        albumId = jsonAlbum.getInt("id"),
                        name = jsonAlbum.getString("name"),
                        cover = jsonAlbum.getString("cover"),
                        recordLabel = jsonAlbum.getString("recordLabel"),
                        releaseDate = jsonAlbum.getString("releaseDate"),
                        genre = jsonAlbum.getString("genre"),
                        description = jsonAlbum.getString("description"),
                        performers = performers
                    ))
                }

                list.add(i, Musician(
                    id = item.getInt("id"),
                    name = item.getString("name"),
                    image= item.getString("image"),
                    description= item.getString("description"),
                    birthDate= item.getString("birthDate"),
                    albums = albumsList
                )
                )
                onComplete(list)
                EspressoIdlingResource.decrement()
            }
        },{
            onError(it)
        }))
    }
}