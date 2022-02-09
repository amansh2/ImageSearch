package com.example.imagesearch.data

import android.app.DownloadManager
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.imagesearch.api.UnsplashApi
import com.example.imagesearch.api.UnsplashApiService
import retrofit2.HttpException
import java.io.IOException

private const val UNSPLASH_STARTING_PAGE_INDEX=1
class UnsplashPagingSource(
    private var apiService: UnsplashApiService,
    private val query: String
):PagingSource<Int,photo>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, photo> {

        val position=params.key ?:UNSPLASH_STARTING_PAGE_INDEX
        apiService=UnsplashApi.retrofitService
       return try {
            val response = apiService.searchPhotos(query,position,params.loadSize)
            val photos = response.results
           LoadResult.Page(
               data = photos,
               prevKey = if(position== UNSPLASH_STARTING_PAGE_INDEX) null else position-1,
               nextKey = if(photos.isEmpty()) null else position+1
           )
        }catch (exception:IOException){
            LoadResult.Error(exception)
        }catch (exception:HttpException){
            LoadResult.Error(exception)
        }
    }




}