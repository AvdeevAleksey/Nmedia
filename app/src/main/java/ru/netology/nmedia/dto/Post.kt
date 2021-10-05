package ru.netology.nmedia.dto

import android.os.Parcel
import android.os.Parcelable

data class Post(
        val id: Int,
        val author: String,
        val content: String,
        val published: String,
        val likesCount: Int,
        val shareCount: Int,
        val viewingCount: Int,
        val likedByMe: Boolean = false
): Parcelable {
        constructor(parcel: Parcel) : this(
                parcel.readInt(),
                requireNotNull(parcel.readString()),
                requireNotNull(parcel.readString()),
                requireNotNull(parcel.readString()),
                parcel.readInt(),
                parcel.readInt(),
                parcel.readInt(),
                parcel.readByte() != 0.toByte()
        ) {
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
                parcel.writeInt(id)
                parcel.writeString(author)
                parcel.writeString(content)
                parcel.writeString(published)
                parcel.writeInt(likesCount)
                parcel.writeInt(shareCount)
                parcel.writeInt(viewingCount)
                parcel.writeByte(if (likedByMe) 1 else 0)
        }

        override fun describeContents(): Int {
                return 0
        }

        companion object CREATOR : Parcelable.Creator<Post> {
                override fun createFromParcel(parcel: Parcel): Post {
                        return Post(parcel)
                }

                override fun newArray(size: Int): Array<Post?> {
                        return arrayOfNulls(size)
                }
        }
}
