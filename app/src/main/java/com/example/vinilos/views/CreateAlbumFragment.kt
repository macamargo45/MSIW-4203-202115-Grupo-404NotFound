package com.example.vinilos.views

import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.annotation.RequiresApi
import com.example.vinilos.R
import com.example.vinilos.viewmodels.CreateAlbumViewModel

class CreateAlbumFragment : Fragment() {

    companion object {
        fun newInstance() = CreateAlbumFragment()
    }

    private lateinit var viewModel: CreateAlbumViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.create_album_fragment, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val spinner: Spinner = view.findViewById(R.id.record_label_spinner)
        // Create an ArrayAdapter using the string array and a default spinner layout
        this.context?.let {
            ArrayAdapter.createFromResource(
                it,
                R.array.record_labels,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                // Apply the adapter to the spinner
                spinner.adapter = adapter
            }
        }
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CreateAlbumViewModel::class.java)
        // TODO: Use the ViewModel

    }

}