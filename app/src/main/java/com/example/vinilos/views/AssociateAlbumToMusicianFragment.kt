package com.example.vinilos.views

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.vinilos.R
import com.example.vinilos.databinding.AssociateAlbumToMusicianFragmentBinding
import com.example.vinilos.models.Album
import com.example.vinilos.viewmodels.AssociateAlbumToMusicianViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class AssociateAlbumToMusicianFragment : Fragment() {
    private var _binding: AssociateAlbumToMusicianFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: AssociateAlbumToMusicianViewModel
    private val args: AssociateAlbumToMusicianFragmentArgs by navArgs()
    private var albums: List<Album> = emptyList()
    private var spinner: Spinner? = null
    private var albumAdapter: ArrayAdapter<Album>? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (_binding == null)
            _binding = AssociateAlbumToMusicianFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        try {
            super.onActivityCreated(savedInstanceState)
            val activity = requireNotNull(this.activity) {
                "You can only access the viewModel after onActivityCreated()"
            }
            viewModel = ViewModelProvider(this, AssociateAlbumToMusicianViewModel.Factory(activity.application)).get(
                AssociateAlbumToMusicianViewModel::class.java
            )
            viewModel.albums.observe(viewLifecycleOwner, Observer<List<Album>> {
                it.apply {
                    albums = this
                }
            })
        } catch (e: Exception) {
            Log.println(Log.ERROR, "Error", e.stackTraceToString())
            val action = MusicianDetailsFragmentDirections.actionMusicianDetailsFragmentToErrorMessageFragment()
            view?.findNavController()?.navigate(action)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        try {
            spinner = view.findViewById(R.id.album_list_spinner) as Spinner

            albumAdapter = ArrayAdapter<Album>(this, android.R.layout.simple_spinner_item, albums)

            val txtName: TextView = view.findViewById(R.id.nameMusicianDetailsAssociateAlbum)
            txtName.text = args.musician.name.toString()

            val birthDate = LocalDate.parse(args.musician.birthDate, DateTimeFormatter.ISO_DATE_TIME)

            val txtBirthDate: TextView = view.findViewById(R.id.birthDateAssociateAlbum)
            txtBirthDate.text =
                getString(R.string.musician_birth_date, DateTimeFormatter.ofPattern("dd-MM-yyyy").format(birthDate))

            // Create an ArrayAdapter using the string array and a default spinner layout
            this.context?.let {
                ArrayAdapter.createFromResource(
                    it,
                    R.array.genre_list,
                    android.R.layout.simple_spinner_item
                ).also { adapter ->
                    // Specify the layout to use when the list of choices appears
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    // Apply the adapter to the spinner
                    binding.albumListSpinner.adapter = adapter
                }
            }

        } catch (e: Exception) {
            Log.println(Log.ERROR, "Error", e.message.toString())
            val action =
                AssociateAlbumToMusicianFragmentDirections.actionAssociateAlbumToMusicianFragmentToErrorMessageFragment()
            view.findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onNetworkError() {
        if (!viewModel.isNetworkErrorShown.value!!) {
            val action =
                AssociateAlbumToMusicianFragmentDirections.actionAssociateAlbumToMusicianFragmentToErrorMessageFragment()
            view?.findNavController()?.navigate(action)
            Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
        }
    }
}