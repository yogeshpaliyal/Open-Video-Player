package com.yogeshpaliyal.openvideoplayer.data

import android.net.Uri
import android.os.Parcel
import android.os.Parcelable

data class Video(val contentId: Uri, val videoName: String, val duration : Int, val size: Int): Parcelable {
    constructor(parcel: Parcel) : this(
        Uri.parse(parcel.readString()),
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(contentId.toString())
        parcel.writeString(videoName)
        parcel.writeInt(duration)
        parcel.writeInt(size)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Video> {
        override fun createFromParcel(parcel: Parcel): Video {
            return Video(parcel)
        }

        override fun newArray(size: Int): Array<Video?> {
            return arrayOfNulls(size)
        }
    }
}
