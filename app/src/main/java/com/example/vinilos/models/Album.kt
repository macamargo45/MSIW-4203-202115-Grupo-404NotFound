package com.example.vinilos.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Album(
    val albumId:Int,
    val name: String?,
    val cover: String?,
    val releaseDate: String?,
    val description: String?,
    val genre: String?,
    val recordLabel: String?,
    val performers: MutableList<Performer?>
): Parcelable {
    val performerNames get() = this.performers.map { it?.name }.joinToString()
}
