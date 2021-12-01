package com.example.vinilos.models

import android.os.Parcelable
import android.util.Patterns
import kotlinx.parcelize.Parcelize


@Parcelize
data class Album(
    val albumId: Int?,
    var name: String?,
    var cover: String?,
    var releaseDate: String?,
    var description: String?,
    var genre: String?,
    var recordLabel: String?,
    val performers: MutableList<Performer?>?
) : Parcelable {
    constructor(
    ) : this(null, null, null, null, null, null, null, null)

    val performerNames get() = this.performers?.map { it?.name }?.joinToString()

    fun isNameValueProvided(): Boolean {
        return !(name?.isEmpty() ?: true)
    }
    fun isGenreValueProvided(): Boolean {
        return !(genre?.isEmpty() ?: true) && genre != "Seleccionar"
    }
    fun isDescriptionValueProvided(): Boolean {
        return !(description?.isEmpty() ?: true)
    }
    fun isRecordLabelValueProvided(): Boolean {
        return !(recordLabel?.isEmpty() ?: true)  && recordLabel != "Seleccionar"
    }

    fun isCoverURLValid(): Boolean {
        return cover?.matches(Patterns.WEB_URL.toRegex()) ?: false
    }

    fun isDescriptionLengthGreaterThan5(): Boolean {
        return if (description != null) {
            description!!.length > 5
        } else {
            false
        }
    }
}
