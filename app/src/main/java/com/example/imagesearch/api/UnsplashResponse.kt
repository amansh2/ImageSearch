package com.example.imagesearch.api

import androidx.lifecycle.MutableLiveData
import com.example.imagesearch.data.photo

data class UnsplashResponse(
    val results:List<photo>
)