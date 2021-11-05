package com.example.vinilos.models
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class Performer(
    val id: Int,
    val name: String,
    val image: String,
    val description: String
): Parcelable