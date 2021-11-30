package com.example.vinilos.views

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.vinilos.R
import com.example.vinilos.databinding.CreateAlbumFragmentBinding
import com.example.vinilos.models.Album
import com.example.vinilos.viewmodels.CreateAlbumViewModel
import java.time.LocalDate
import java.util.*


class CreateAlbumFragment : Fragment() {
    private var _binding: CreateAlbumFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: CreateAlbumViewModel
    private var newAlbum: Album? = null
    //private var viewModelAdapter: CreateAlbumAdapter? = null

    companion object;


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (_binding == null)
            _binding = CreateAlbumFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.releaseDatepicker.maxDate = Date().time
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
            newAlbum!!.releaseDate  = getDateReleaseDate()
            viewModel.album.postValue(newAlbum)
        }
    }

    private fun getDateReleaseDate(): String {
        val date = LocalDate.of(binding.releaseDatepicker.year,binding.releaseDatepicker.month+1, binding.releaseDatepicker.dayOfMonth)
        Log.d("date: ", date.toString())
        return date.toString()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(this)[CreateAlbumViewModel::class.java]

        viewModel.album.observe(viewLifecycleOwner, {
            var validData = true
            if (
                !it.isNameValueProvided()) {
                binding.nameTextbox.error = getString(R.string.MensajeErrorNombreAlbum)
                binding.nameTextbox.requestFocus()
                validData = false
            }
            if (!it.isCoverURLValid()) {
                binding.coverTextobox.error = getString(R.string.ErrorMessageAlbumCover)
                binding.coverTextobox.requestFocus()
                validData = false
            }
            if (!it.isGenreValueProvided()) {

                val errorText: TextView = binding.genreSpinner.selectedView as TextView
                errorText.error = getString(R.string.ErrorMessageAlbumGenre)
                errorText.setTextColor(Color.RED) //just to highlight that this is an error

                validData = false
            }
            if (!it.isRecordLabelValueProvided()) {

                val errorText: TextView = binding.recordLabelSpinner.selectedView as TextView
                errorText.error = getString(R.string.ErrorMessageAlbumLabel)
                errorText.setTextColor(Color.RED)
                validData = false
            }
            if (!it.isDescriptionValueProvided() && !it.isDescriptionLengthGreaterThan5()) {
                binding.descriptionTextbox.error =
                    getString(R.string.ErrorMessageAlbumDescription)
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

        viewModel.albumId.observe(
            viewLifecycleOwner,
            {
                val action = CreateAlbumFragmentDirections.actionCreateAlbumFragmentToAlbumFragment2()
                view?.findNavController()?.navigate(action)
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