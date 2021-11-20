package com.example.vinilos.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Musician(
    val id: Int,
    val name: String?,
    val image: String?,
    val description: String?,
    val birthDate: String?,
    val albums: List<Album>
): Parcelable