package com.example.imagesearch.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.imagesearch.api.UnsplashApi
import com.example.imagesearch.api.UnsplashApiService



class UnsplashRepository(private val apiService: UnsplashApiService) {
    fun getSearchResults(query:String)=
        Pager(
        config = PagingConfig(
            20,
            100,
            false
        ), pagingSourceFactory = {UnsplashPagingSource(apiService,query)}
        ).liveData
}