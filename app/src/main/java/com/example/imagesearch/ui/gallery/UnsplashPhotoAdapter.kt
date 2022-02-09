package com.example.imagesearch.ui.gallery

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.imagesearch.R
import com.example.imagesearch.data.photo
import com.example.imagesearch.databinding.ItemPhotoBinding

class UnsplashPhotoAdapter(private val listener:OnItemClickListener) : PagingDataAdapter<photo, UnsplashPhotoAdapter.PhotoViewHolder>(
    PHOTO_COMPARATOR
) {
    class PhotoViewHolder(val binding: ItemPhotoBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PhotoViewHolder(
            ItemPhotoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val currItem = getItem(position)
        if (currItem != null) {
            holder.binding.apply {
                textViewUserName.text = currItem.user.username
                Glide.with(holder.itemView).load(currItem.urls.regular).centerCrop()
                    .error(R.drawable.error_image_foreground)
                    .transition(DrawableTransitionOptions.withCrossFade()).into(photoView)
                root.setOnClickListener {
                    listener.onItemClicked(currItem)
                }
            }
        }


    }

    interface OnItemClickListener{
        fun onItemClicked(photo:photo)
    }


    companion object {
        private val PHOTO_COMPARATOR = object : DiffUtil.ItemCallback<photo>() {
            override fun areItemsTheSame(oldItem: photo, newItem: photo) = oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: photo, newItem: photo) = oldItem == newItem

        }
    }
}