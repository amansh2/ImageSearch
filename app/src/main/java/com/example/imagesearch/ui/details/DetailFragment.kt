package com.example.imagesearch.ui.details

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.imagesearch.R
import com.example.imagesearch.databinding.FragmentDetailsBinding
import android.widget.Toast

import android.os.Environment
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

import androidx.core.content.ContextCompat.getSystemService
import com.example.imagesearch.data.photo
import java.io.File
import java.lang.Exception
import java.util.jar.Manifest


class DetailFragment : Fragment(R.layout.fragment_details) {

    private val args by navArgs<DetailFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentDetailsBinding.bind(view)
        val photo = args.photo

        binding.apply {
            Glide.with(this@DetailFragment).load(photo.urls.full).listener(object:RequestListener<Drawable>{
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    progressBar.isVisible=false
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                   progressBar.isVisible=false
                    creator.isVisible=true
                    description.isVisible=true
                    return false
                }

            })
                .error(R.drawable.error_image_foreground).into(imageView)
            description.text=photo.description
            val uri= Uri.parse(photo.user.attributionUrl)
            val intent= Intent(Intent.ACTION_VIEW,uri)

            creator.apply{
             text="Photo by ${photo.user.name} on Unsplash"
             setOnClickListener {
                 context.startActivity(intent)
             }
                paint.isUnderlineText=true
            }

            download.setOnClickListener {
                downloadImageNew(photo.id,photo.links.download)
            }
        }

    }

    private fun downloadImageNew(filename: String, downloadUrlOfImage: String) {
        try {
            val dm = activity?.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager?
            val downloadUri = Uri.parse(downloadUrlOfImage)
            val request = DownloadManager.Request(downloadUri)
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
                .setAllowedOverRoaming(true)
                .setTitle(filename)
                .setMimeType("image/jpeg") // Your file type. You can use this code to download other file types also.
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setDestinationInExternalPublicDir(
                    Environment.DIRECTORY_PICTURES,
                    File.separator.toString() + filename + ".jpg"
                )
            dm!!.enqueue(request)
            Toast.makeText(activity, "Image download started.", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(activity, "Image download failed.", Toast.LENGTH_SHORT).show()
        }
    }


}