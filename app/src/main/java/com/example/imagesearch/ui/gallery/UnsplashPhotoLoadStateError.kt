package com.example.imagesearch.ui.gallery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.imagesearch.databinding.UnsplashPhotoLoadStateFooterHeaderBinding

class UnsplashPhotoLoadStateAdapter(private val retry:()->Unit) :LoadStateAdapter<UnsplashPhotoLoadStateAdapter.LoadStateViewHolder>(){
    inner class LoadStateViewHolder(private val binding: UnsplashPhotoLoadStateFooterHeaderBinding):RecyclerView.ViewHolder(binding.root) {
       init{
           binding.retry.setOnClickListener {
               retry.invoke()
           }
       }
        fun bind(loadState: LoadState) {
            binding.apply {
                progressBar.isVisible=loadState is LoadState.Loading
                retry.isVisible=loadState is LoadState.Error
                errorText.isVisible=loadState is LoadState.Error
            }

        }

    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
       holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        return LoadStateViewHolder(UnsplashPhotoLoadStateFooterHeaderBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }
}