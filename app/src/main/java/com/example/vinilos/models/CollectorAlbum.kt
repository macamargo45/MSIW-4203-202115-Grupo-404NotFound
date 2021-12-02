package com.example.vinilos.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CollectorAlbum (
    val id: Int,
    val price: Int,
    val status: String?
): Parcelable