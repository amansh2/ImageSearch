package com.example.imagesearch.ui.gallery

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.example.imagesearch.R
import com.example.imagesearch.api.UnsplashApi
import com.example.imagesearch.data.UnsplashRepository
import com.example.imagesearch.data.photo
import com.example.imagesearch.databinding.FragmentGalleryBinding


class GalleryFragment : Fragment(R.layout.fragment_gallery),UnsplashPhotoAdapter.OnItemClickListener {
    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: GalleryViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentGalleryBinding.bind(view)
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(UnsplashRepository(UnsplashApi.retrofitService))
        ).get(GalleryViewModel::class.java)

        val adapter = UnsplashPhotoAdapter(this)
//header footer optional
        binding.apply {
            recyclerView.setHasFixedSize(true)
            recyclerView.adapter = adapter.withLoadStateHeaderAndFooter(
                header = UnsplashPhotoLoadStateAdapter { adapter.retry() },
                footer = UnsplashPhotoLoadStateAdapter { adapter.retry() }
            )
            retry.setOnClickListener {
                adapter.retry()
            }
        }

        viewModel.photos.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)

        }
//optional
        adapter.addLoadStateListener {
            binding.apply {
                progressBar.isVisible = it.source.refresh is LoadState.Loading
                retry.isVisible = it.source.refresh is LoadState.Error
                recyclerView.isVisible = it.source.refresh is LoadState.NotLoading
                errorText.isVisible = it.source.refresh is LoadState.Error

                if (it.source.refresh is LoadState.NotLoading && it.append.endOfPaginationReached && adapter.itemCount < 1) {
                    recyclerView.isVisible = false
                    textViewEmpty.isVisible = true
                } else textViewEmpty.isVisible = false

            }


        }

        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.menu_gallery, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as androidx.appcompat.widget.SearchView
        searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    binding.recyclerView.scrollToPosition(0)
                    viewModel.searchPhotos(query)
                    searchView.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClicked(photo: photo) {
       val action=GalleryFragmentDirections.actionGalleryFragmentToDetailFragment(photo)
        findNavController().navigate(action)
    }
}