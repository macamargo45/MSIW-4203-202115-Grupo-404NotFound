package com.example.vinilos.views

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vinilos.R
import com.example.vinilos.databinding.MusicianDetailsFragmentBinding
import com.example.vinilos.viewmodels.MusicianDetailsViewModel
import com.example.vinilos.views.adapters.MusicianAlbumsAdapter
import com.squareup.picasso.Picasso
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MusicianDetailsFragment : Fragment() {
    private var _binding: MusicianDetailsFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MusicianDetailsViewModel
    private val args: MusicianDetailsFragmentArgs by navArgs()
    private var viewModelAdapter: MusicianAlbumsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if(_binding == null)
            _binding = MusicianDetailsFragmentBinding.inflate(inflater, container, false)

        if(viewModelAdapter == null)
            viewModelAdapter = MusicianAlbumsAdapter()
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        try {
            super.onActivityCreated(savedInstanceState)
            val activity = requireNotNull(this.activity) {
                "You can only access the viewModel after onActivityCreated()"
            }

            binding.albumsMusicianRv.isVisible = false
            viewModelAdapter!!.musicianAlbums = args.musician.albums
            binding.albumsMusicianRv.isVisible = true

            viewModel =
                ViewModelProvider(this, MusicianDetailsViewModel.Factory(activity.application))[MusicianDetailsViewModel::class.java]
            viewModel.eventNetworkError.observe(viewLifecycleOwner, { isNetworkError ->
                if (isNetworkError) onNetworkError()
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
            val txtDescription: TextView = view.findViewById(R.id.MusicianDescriptionDetails)
            txtDescription.text = args.musician.description.toString()

            val txtName: TextView = view.findViewById(R.id.NameMusicianDetails)
            txtName.text = args.musician.name.toString()

            val birthDate = LocalDate.parse(args.musician.birthDate, DateTimeFormatter.ISO_DATE_TIME)

            val txtBirthDate: TextView = view.findViewById(R.id.BirthDate)
            txtBirthDate.text = getString(R.string.musician_birth_date, DateTimeFormatter.ofPattern("dd-MM-yyyy").format(birthDate))

            val imgCover: ImageView = view.findViewById(R.id.imageViewMusicianDetails)
            Picasso
                .get()
                .load(args.musician.image)
                .resize(100, 100)
                .into(imgCover)

            binding.albumsMusicianRv.layoutManager = LinearLayoutManager(context)
            binding.albumsMusicianRv.adapter = viewModelAdapter
        } catch (e: Exception) {
            Log.println(Log.ERROR, "Error", e.message.toString())
            val action = MusicianDetailsFragmentDirections.actionMusicianDetailsFragmentToErrorMessageFragment()
            view.findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        viewModelAdapter = null
    }

    private fun onNetworkError() {
        if (!viewModel.isNetworkErrorShown.value!!) {
            val action = MusicianDetailsFragmentDirections.actionMusicianDetailsFragmentToErrorMessageFragment()
            view?.findNavController()?.navigate(action)
            Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
        }
    }


}