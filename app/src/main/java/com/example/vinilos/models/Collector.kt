package com.example.vinilos.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import org.json.JSONArray

@Parcelize
data class Collector (
    val id:Int,
    val name: String?,
    val telephone: String?,
    val email: String?,
): Parcelable {
    val performers = mutableListOf<Performer?>()
    val albums = mutableListOf<CollectorAlbum?>()
    val comments = mutableListOf<Comment>()

    fun setPerformersFromJSON(jsonPerformers: JSONArray) {
        if (jsonPerformers.length() > 0) {
            for (index in 0 until jsonPerformers.length()) {
                val jsonPerformer = jsonPerformers.getJSONObject(index)

                performers.add(
                    index, Performer(
                        id = jsonPerformer.getInt("id"),
                        name = jsonPerformer.getString("name"),
                        image = jsonPerformer.getString("image"),
                        description = jsonPerformer.getString("description")
                    )
                )
            }
        }
    }

    fun setAlbumsFromJSON(jsonAlbums: JSONArray) {
        if (jsonAlbums.length() > 0) {
            for (index in 0 until jsonAlbums.length()) {
                val jsonCollectorAlbum = jsonAlbums.getJSONObject(index)

                albums.add(
                    index, CollectorAlbum(
                        id = jsonCollectorAlbum.getInt("id"),
                        status = jsonCollectorAlbum.getString("status"),
                        price = jsonCollectorAlbum.getInt("price")
                    )
                )
            }
        }
    }

    fun setCommentsFromJSON(jsonComments: JSONArray) {
        if (jsonComments.length() > 0) {
            for (index in 0 until jsonComments.length()) {
                val comment = jsonComments.getJSONObject(index)
                comments.add(
                    index, Comment(
                        id = comment.getInt("id"),
                        rating = comment.getInt("rating"),
                        description = comment.getString("description")
                    )
                )
            }
        }
    }
}