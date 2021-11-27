package com.example.vinilos.network

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.JsonRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.vinilos.models.*
import com.example.vinilos.util.EspressoIdlingResource
import org.json.JSONArray
import org.json.JSONObject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine
import org.json.JSONException
import com.android.volley.AuthFailureError
import com.android.volley.VolleyError








class NetworkServiceAdapter constructor(context: Context) {

    companion object {
        const val BASE_URL = "https://vinilo-grupo-15.herokuapp.com/"
        private var instance: NetworkServiceAdapter? = null
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

    suspend fun getAlbums() = suspendCoroutine<List<Album>> { cont ->
        EspressoIdlingResource.increment()
        requestQueue.add(
            getRequest("albums",
                { response ->
                    val resp = JSONArray(response)
                    val list = mutableListOf<Album>()

                    for (i in 0 until resp.length()) {
                        val item = resp.getJSONObject(i)

                        val performers = mutableListOf<Performer?>()
                        val jsonPerformers = item.getJSONArray("performers")

                        for (performerIndex in 0 until jsonPerformers.length()) {
                            val jsonPerformer = jsonPerformers.getJSONObject(performerIndex)

                            performers.add(
                                performerIndex, Performer(
                                    id = jsonPerformer.getInt("id"),
                                    name = jsonPerformer.getString("name"),
                                    image = jsonPerformer.getString("image"),
                                    description = jsonPerformer.getString("description")
                                )
                            )
                        }

                        list.add(
                            i, Album(
                                albumId = item.getInt("id"),
                                name = item.getString("name"),
                                cover = item.getString("cover"),
                                recordLabel = item.getString("recordLabel"),
                                releaseDate = item.getString("releaseDate"),
                                genre = item.getString("genre"),
                                description = item.getString("description"),
                                performers = performers
                            )
                        )
                    }
                    EspressoIdlingResource.decrement()
                    cont.resume(list)
                },
                {
                    cont.resumeWithException(it) //se relanza la excepción
                })
        )
    }

    suspend fun createAlbum(newAlbum: Album) = suspendCoroutine<Int> { cont ->
        EspressoIdlingResource.increment()

        val postData = JSONObject()
        try {
            postData.put("name", newAlbum.name)
            postData.put("cover", newAlbum.cover)
            postData.put("releaseDate", newAlbum.releaseDate)
            postData.put("description", newAlbum.description)
            postData.put("genre", newAlbum.genre)
            postData.put("recordLabel", newAlbum.recordLabel)
        } catch (e: JSONException) {
            cont.resumeWithException(e)
        }
        requestQueue.add(
            postRequest("albums", postData,
                { response ->
                    val albumId = response.getInt("id")
                    EspressoIdlingResource.decrement()
                    cont.resume(albumId)
                },
                {
                    Log.d("Error in post call", it.stackTrace.toString())

                        // As of f605da3 the following should work
                    Log.d("Error in post call",it.networkResponse.toString())

                    cont.resumeWithException(it) //se relanza la excepción
                })
        )
    }


    private fun getRequest(
        path: String,
        responseListener: Response.Listener<String>,
        errorListener: Response.ErrorListener
    ): StringRequest {
        return StringRequest(Request.Method.GET, BASE_URL + path, responseListener, errorListener)
    }

    private fun postRequest(
        path: String,
        parameters: JSONObject,
        responseListener: Response.Listener<JSONObject>,
        errorListener: Response.ErrorListener
    ): JsonObjectRequest {

        var request = object : JsonObjectRequest(
                Request.Method.POST,
                BASE_URL + path,
                parameters,
                responseListener,
                errorListener
            ){
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String>? {
                val headers: MutableMap<String, String> = HashMap()
                headers["Authorization"] = "TOKEN" //put your token here
                return headers
            }
        }

        return request


    }

    suspend fun getCollectors() = suspendCoroutine<List<Collector>> { cont ->
        EspressoIdlingResource.increment()
        requestQueue.add(
            getRequest("collectors",
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

                            favoritePerformers.add(
                                performerIndex, Performer(
                                    id = jsonPerformer.getInt("id"),
                                    name = jsonPerformer.getString("name"),
                                    image = jsonPerformer.getString("image"),
                                    description = jsonPerformer.getString("description")
                                )
                            )
                        }

                        for (albumIndex in 0 until jsonCollectorsAlbums.length()) {
                            val jsonCollectorAlbum = jsonCollectorsAlbums.getJSONObject(albumIndex)

                            collectorsAlbums.add(
                                albumIndex, CollectorAlbum(
                                    id = jsonCollectorAlbum.getInt("id"),
                                    status = jsonCollectorAlbum.getString("status"),
                                    price = jsonCollectorAlbum.getInt("price")
                                )
                            )
                        }

                        list.add(
                            i, Collector(
                                id = item.getInt("id"),
                                name = item.getString("name"),
                                telephone = item.getString("telephone"),
                                email = item.getString("email"),
                                favoritePerformers = favoritePerformers,
                                collectorAlbums = collectorsAlbums
                            )
                        )
                    }
                    cont.resume(list)
                    EspressoIdlingResource.decrement()
                },
                {
                    cont.resumeWithException(it) //se relanza la excepción
                })
        )
    }

    suspend fun getMusicians() = suspendCoroutine<List<Musician>> { cont ->
        EspressoIdlingResource.increment()
        requestQueue.add(getRequest("musicians", { response ->

            val resp = JSONArray(response)
            val list = mutableListOf<Musician>()
            for (i in 0 until resp.length()) {
                val item = resp.getJSONObject(i)
                val albums = item.getJSONArray("albums")
                val albumsList = mutableListOf<Album>()
                val performers = mutableListOf<Performer?>()

                for (albumIndex in 0 until albums.length()) {
                    val jsonAlbum = albums.getJSONObject(albumIndex)

                    albumsList.add(
                        i, Album(
                            albumId = jsonAlbum.getInt("id"),
                            name = jsonAlbum.getString("name"),
                            cover = jsonAlbum.getString("cover"),
                            recordLabel = jsonAlbum.getString("recordLabel"),
                            releaseDate = jsonAlbum.getString("releaseDate"),
                            genre = jsonAlbum.getString("genre"),
                            description = jsonAlbum.getString("description"),
                            performers = performers
                        )
                    )
                }

                list.add(
                    i, Musician(
                        id = item.getInt("id"),
                        name = item.getString("name"),
                        image = item.getString("image"),
                        description = item.getString("description"),
                        birthDate = item.getString("birthDate"),
                        albums = albumsList.toList()
                    )
                )
                cont.resume(list)
                EspressoIdlingResource.decrement()
            }
        }, {
            cont.resumeWithException(it) //se relanza la excepción
        }))
    }

}