package com.example.vinilos.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Collector (
    val id:Int,
    val name: String?,
    val telephone: String?,
    val email: String?,
    val favoritePerformers: MutableList<Performer?>,
    val collectorAlbums: MutableList<CollectorAlbum?>
): Parcelable