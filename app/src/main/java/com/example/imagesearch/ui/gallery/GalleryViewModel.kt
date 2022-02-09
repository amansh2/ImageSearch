package com.example.imagesearch.ui.gallery

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.imagesearch.api.UnsplashApi
import com.example.imagesearch.api.UnsplashApiService
import com.example.imagesearch.data.UnsplashRepository

class GalleryViewModel(private var repository:UnsplashRepository):ViewModel() {

    val currQuery=MutableLiveData(DEFAULT_QUERY)

    val photos=currQuery.switchMap {
        repository.getSearchResults(it).cachedIn(viewModelScope)
    }
    fun searchPhotos(query:String){
        currQuery.value=query
    }

    companion object{
        private const val DEFAULT_QUERY="cats"
    }
}