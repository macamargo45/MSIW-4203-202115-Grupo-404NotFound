package com.example.vinilos.views

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.vinilos.R
import com.example.vinilos.databinding.AddAlbumToMusicianFragmentBinding
import com.example.vinilos.models.Album
import com.example.vinilos.viewmodels.AddAlbumToMusicianViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class AddAlbumToMusicianFragment : Fragment() {
    private var _binding: AddAlbumToMusicianFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: AddAlbumToMusicianViewModel
    private val args: AddAlbumToMusicianFragmentArgs by navArgs()
    private var albums: List<Album> = emptyList()
    private var spinner: Spinner? = null
    private var albumAdapter: ArrayAdapter<Album>? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (_binding == null)
            _binding = AddAlbumToMusicianFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        try {
            super.onActivityCreated(savedInstanceState)
            val activity = requireNotNull(this.activity) {
                "You can only access the viewModel after onActivityCreated()"
            }
            viewModel = ViewModelProvider(this, AddAlbumToMusicianViewModel.Factory(activity.application))[AddAlbumToMusicianViewModel::class.java]
            viewModel.albums.observe(viewLifecycleOwner, { it ->
                it.apply {
                    albums = this
                }
                albumAdapter = ArrayAdapter<Album>(activity, android.R.layout.simple_spinner_item, albums)
                spinner!!.adapter = this.activity?.let {
                    ArrayAdapter(
                        it,
                        R.layout.support_simple_spinner_dropdown_item,
                        albums
                    )
                } as SpinnerAdapter
            })

            viewModel.eventNetworkError.observe(
                viewLifecycleOwner,
                { isNetworkError ->
                    if (isNetworkError) onNetworkError()
                })

            viewModel.albumId.observe(
                viewLifecycleOwner,
                {
                    Toast.makeText(activity, getString(R.string.AdditionSucessMessage), Toast.LENGTH_LONG).show()
                    val action = AddAlbumToMusicianFragmentDirections.actionAddAlbumToMusicianFragmentToMusiciansListFragment()
                    view?.findNavController()?.navigate(action)
                })
        } catch (e: Exception) {
            Log.println(Log.ERROR, "Error", e.stackTraceToString())
            val action = AddAlbumToMusicianFragmentDirections.actionAddAlbumToMusicianFragmentToErrorMessageFragment()
            view?.findNavController()?.navigate(action)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        try {
            spinner = view.findViewById(R.id.album_list_spinner) as Spinner

            val txtName: TextView = view.findViewById(R.id.nameMusicianDetailsAssociateAlbum)
            txtName.text = args.musician.name.toString()

            val birthDate = LocalDate.parse(args.musician.birthDate, DateTimeFormatter.ISO_DATE_TIME)

            val txtBirthDate: TextView = view.findViewById(R.id.birthDateAssociateAlbum)
            txtBirthDate.text =
                getString(R.string.musician_birth_date, DateTimeFormatter.ofPattern("dd-MM-yyyy").format(birthDate))

            binding.addAlbumToMusicianButton.setOnClickListener {
                viewModel.addAlbumToMusician(
                    albums[binding.albumListSpinner.selectedItemId.toInt()].albumId!!,
                    args.musician.id
                )
            }
        } catch (e: Exception) {
            Log.println(Log.ERROR, "Error", e.message.toString())
            val action =
                AddAlbumToMusicianFragmentDirections.actionAddAlbumToMusicianFragmentToErrorMessageFragment()
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
                AddAlbumToMusicianFragmentDirections.actionAddAlbumToMusicianFragmentToErrorMessageFragment()
            view?.findNavController()?.navigate(action)
            Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
        }
    }
}