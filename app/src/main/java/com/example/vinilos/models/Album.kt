package com.example.vinilos.models


import android.os.Parcel
import android.os.Parcelable

data class Album(
    val albumId:Int,
    val name: String?,
    val cover: String?,
    val releaseDate: String?,
    val description: String?,
    val genre: String?,
    val recordLabel: String?

): Parcelable {

    companion object {
        @JvmField val CREATOR: Parcelable.Creator<Album> = object : Parcelable.Creator<Album>{
            override fun newArray(size: Int): Array<Album?> = arrayOfNulls<Album>(size)
            override fun createFromParcel(source: Parcel): Album = Album(source)
        }
    }

    constructor(source: Parcel):this(
        source.readInt(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString()
    )

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.let {
            dest.writeInt(albumId)
            dest.writeString(name)
            dest.writeString(cover)
            dest.writeString(releaseDate)
            dest.writeString(description)
            dest.writeString(genre)
            dest.writeString(recordLabel)
        }
    }

    override fun describeContents(): Int = 0

}
