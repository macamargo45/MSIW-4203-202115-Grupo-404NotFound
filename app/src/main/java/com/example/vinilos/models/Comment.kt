package com.example.vinilos.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Comment (
    val id: Int,
    val rating: Int,
    val description: String
): Parcelable