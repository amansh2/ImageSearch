package com.example.imagesearch.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class photo(
    val id: String,
    val description:String?,
    val urls: photoUrls,
    val links:downloadlinks,
    val user: PhotoUser
    ) : Parcelable {
    @Parcelize
    data class photoUrls(
        val raw:String,
        val full:String,
        val small:String,
        val regular:String,
        val thumb:String
    ) : Parcelable

    @Parcelize
    data class PhotoUser(
        val name:String,
        val username:String
    ) : Parcelable {
        val attributionUrl get() = "https://unsplash.com/$username?utm_source=ImageSearch&utm_medium=referral"
    }
@Parcelize
    data class downloadlinks(
    val download:String
    ) : Parcelable

}