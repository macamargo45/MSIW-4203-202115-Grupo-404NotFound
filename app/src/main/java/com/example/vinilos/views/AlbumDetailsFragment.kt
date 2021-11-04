package com.example.vinilos.views

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.vinilos.R
import com.example.vinilos.viewmodels.AlbumDetailsViewModel

class AlbumDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = AlbumDetailsFragment()
    }

    private lateinit var viewModel: AlbumDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.album_details_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AlbumDetailsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}