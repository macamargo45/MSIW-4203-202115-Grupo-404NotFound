package com.example.vinilos.views

import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.example.vinilos.R
import com.example.vinilos.databinding.CreateAlbumFragmentBinding
import com.example.vinilos.models.Album
import com.example.vinilos.viewmodels.CreateAlbumViewModel
import java.util.Date

class CreateAlbumFragment : Fragment() {
    private var _binding: CreateAlbumFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: CreateAlbumViewModel
    private var newAlbum: Album? = null
    //private var viewModelAdapter: CreateAlbumAdapter? = null

    companion object {
        fun newInstance() = CreateAlbumFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (_binding == null)
            _binding = CreateAlbumFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

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
                binding.recordLabelSpinner.adapter = adapter
            }

            ArrayAdapter.createFromResource(
                it,
                R.array.genre_list,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                // Apply the adapter to the spinner
                binding.genreSpinner.adapter = adapter
            }
        }


        binding.addAlbumButton.setOnClickListener {
            if (newAlbum == null)
                newAlbum = Album()
            newAlbum!!.name = binding.nameTextbox.text.toString()
            newAlbum!!.cover = binding.coverTextobox.text.toString()
            newAlbum!!.description = binding.descriptionTextbox.text.toString()
            newAlbum!!.genre = binding.genreSpinner.selectedItem.toString()
            newAlbum!!.recordLabel = binding.recordLabelSpinner.selectedItem.toString()
            newAlbum!!.releaseDate  = "01/01/2020"
            viewModel.album.postValue(newAlbum)
        }
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(this).get(CreateAlbumViewModel::class.java)

        viewModel.album.observe(viewLifecycleOwner, {
            var validData: Boolean = true
            if (
                !it.isNameValueProvided()) {
                binding.nameTextbox.error = "Ingrese el nombre del album"
                binding.nameTextbox.requestFocus()
                validData = false
            }
            if (!it.isCoverURLValid()) {
                binding.coverTextobox.error = "Ingrese un URL válido para el cover del album"
                binding.coverTextobox.requestFocus()
                validData = false
            }
            if (!it.isGenreValueProvided()) {
                binding.nameTextbox.error = "Seleccione el género del album"
                binding.nameTextbox.requestFocus()
                validData = false
            }
            if (!it.isRecordLabelValueProvided()) {
                binding.nameTextbox.error = "Seleccione el sello discográfico del album"
                binding.nameTextbox.requestFocus()
                validData = false
            }
            if (!it.isDescriptionLengthGreaterThan5()) {
                binding.descriptionTextbox.error =
                    "Ingrese una descripción de al menos 5 caracteres"
                binding.descriptionTextbox.requestFocus()
                validData = false
            }
            if (validData) {
                viewModel.addAlbum(it)
            }

        })

        viewModel.eventNetworkError.observe(
            viewLifecycleOwner,
            { isNetworkError ->
                if (isNetworkError) onNetworkError()
            })

    }

    private fun onNetworkError() {
        if (!viewModel.isNetworkErrorShown.value!!) {
            val action = CreateAlbumFragmentDirections.actionCreateAlbumFragmentToErrorMessageFragment()
            view?.findNavController()?.navigate(action)
            Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()

        }
    }

}