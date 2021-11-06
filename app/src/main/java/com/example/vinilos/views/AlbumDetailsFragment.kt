package com.example.vinilos.views

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.vinilos.R
import com.example.vinilos.databinding.AlbumDetailsFragmentBinding
import com.example.vinilos.viewmodels.AlbumDetailsViewModel
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat

class AlbumDetailsFragment : Fragment() {
    private var _binding: AlbumDetailsFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: AlbumDetailsViewModel
    private val args: AlbumDetailsFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = AlbumDetailsFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        viewModel = ViewModelProvider(this, AlbumDetailsViewModel.Factory(activity.application)).get(AlbumDetailsViewModel::class.java)
        viewModel.album.observe(viewLifecycleOwner, {
            it.apply {
                //Log.d("act", "ACA CREO QUE DEBERIA CARGAR LOS ALBUMES")
                //viewModelAdapter!!.albums = args
            }
        })
        viewModel.eventNetworkError.observe(viewLifecycleOwner, { isNetworkError ->
            if (isNetworkError) onNetworkError()
        })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        val txtDescription: TextView = view.findViewById(R.id.DescripcionAlbumDetails)
        txtDescription.text = args.myArg.description.toString()

        val txtName: TextView = view.findViewById(R.id.NameAlbumDetails)
        txtName.text = args.myArg.name.toString()

        val txtPerformers: TextView = view.findViewById(R.id.PerformerNamesAlbumDetails)
        txtPerformers.text = args.myArg.performerNames

        val txtGenre: TextView = view.findViewById(R.id.GenreAlbumDetails)
        txtGenre.text = args.myArg.genre.toString()

        val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        val formatter = SimpleDateFormat("dd-MM-yyyy")
        val date = formatter.format(parser.parse(args.myArg.releaseDate.toString().substring(0,19)))

        val txtReleaseDate: TextView = view.findViewById(R.id.ReleaseDateAlbumDetails)
        txtReleaseDate.text = date.format(formatter)

        val txtRecordLabel: TextView = view.findViewById(R.id.RecordLabelAlbumDetails)
        txtRecordLabel.text = args.myArg.recordLabel.toString()

        val imgCover: ImageView = view.findViewById(R.id.imageViewAlbumDetails)
        Picasso
            .get()
            .load(args.myArg.cover)
            .into(imgCover)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onNetworkError() {
        if(!viewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
        }
    }



}