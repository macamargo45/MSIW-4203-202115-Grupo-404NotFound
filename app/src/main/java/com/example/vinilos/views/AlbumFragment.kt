package com.example.vinilos.views

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vinilos.databinding.AlbumFragmentBinding
import com.example.vinilos.views.adapters.AlbumsAdapter
import com.example.vinilos.viewmodels.AlbumViewModel

class AlbumFragment : Fragment() {
    private var _binding: AlbumFragmentBinding? = null
    private val binding get() = _binding!!
    //private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: AlbumViewModel
    private var viewModelAdapter: AlbumsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

            _binding = AlbumFragmentBinding.inflate(inflater, container, false)
            viewModelAdapter = AlbumsAdapter()
            return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        try {
            //recyclerView = binding.albumsRv
            binding.albumsRv.layoutManager = LinearLayoutManager(context)
            binding.albumsRv.adapter = viewModelAdapter
        } catch (e: Exception) {
            Log.println(Log.ERROR, "Error", e.message.toString())
            val action = AlbumFragmentDirections.actionAlbumFragment2ToErrorMessageFragment()
            view.findNavController().navigate(action)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        try {


            super.onActivityCreated(savedInstanceState)
            val activity = requireNotNull(this.activity) {
                "You can only access the viewModel after onActivityCreated()"
            }
            binding.progressBar.isVisible = true
            binding.albumsRv.isVisible = false

            viewModel = ViewModelProvider(this, AlbumViewModel.Factory(activity.application))[AlbumViewModel::class.java]
            viewModel.albums.observe(viewLifecycleOwner, {
                it.apply {
                    viewModelAdapter!!.albums = this

                    binding.albumsRv.isVisible = true
                    binding.progressBar.isVisible = false
                }
            })
            viewModel.eventNetworkError.observe(
                viewLifecycleOwner,
                { isNetworkError ->
                    if (isNetworkError) onNetworkError()
                })
        } catch (e: Exception) {
            Log.println(Log.ERROR, "Error", e.message.toString())
            val action = AlbumFragmentDirections.actionAlbumFragment2ToErrorMessageFragment()
            view?.findNavController()?.navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        viewModelAdapter = null
    }

    private fun onNetworkError() {
        if (!viewModel.isNetworkErrorShown.value!!) {
            val action = AlbumFragmentDirections.actionAlbumFragment2ToErrorMessageFragment()
            view?.findNavController()?.navigate(action)
            Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()

        }
    }

}