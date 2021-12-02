package com.example.vinilos.views

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vinilos.R
import com.example.vinilos.databinding.CollectorDetailsFragmentBinding
import com.example.vinilos.models.Musician
import com.example.vinilos.viewmodels.CollectorDetailsViewModel
import com.example.vinilos.views.adapters.CollectorAlbumsAdapter
import com.example.vinilos.views.adapters.CollectorCommentsAdapter
import com.example.vinilos.views.adapters.CollectorMusiciansAdapter

class CollectorDetailsFragment: Fragment() {
    private var _binding: CollectorDetailsFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: CollectorDetailsViewModel
    private val args: CollectorDetailsFragmentArgs by navArgs()
    private var collectorCommentsAdapter: CollectorCommentsAdapter? = null
    private var collectorMusiciansAdapter: CollectorMusiciansAdapter? = null
    private var collectorAlbumsAdapter: CollectorAlbumsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if(_binding == null)
            _binding = CollectorDetailsFragmentBinding.inflate(inflater, container, false)

        if(collectorCommentsAdapter == null)
            collectorCommentsAdapter = CollectorCommentsAdapter()

        if(collectorMusiciansAdapter == null)
            collectorMusiciansAdapter = CollectorMusiciansAdapter()

        if(collectorAlbumsAdapter == null)
            collectorAlbumsAdapter = CollectorAlbumsAdapter()

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        try {
            super.onActivityCreated(savedInstanceState)
            val activity = requireNotNull(this.activity) {
                "You can only access the viewModel after onActivityCreated()"
            }

            binding.progressBarCollectors.isVisible = true
            binding.collectorDetailsFragment.isVisible = false

            collectorCommentsAdapter!!.comments = args.collector.comments.toList()

            viewModel =
                ViewModelProvider(this, CollectorDetailsViewModel.Factory(activity.application))[CollectorDetailsViewModel::class.java]

            val mediator = MediatorLiveData<List<Musician>>()
            viewModel.musicians.observe(viewLifecycleOwner, {
                val musicianIds = args.collector.performers.map { musician -> musician?.id }

                collectorMusiciansAdapter!!.musicians = it.filter { musician -> musician.id in musicianIds }
            })

            viewModel.albums.observe(viewLifecycleOwner, {
                val albumIds = args.collector.albums.map { album -> album?.id }

                collectorAlbumsAdapter!!.albums = it.filter { album -> album.albumId in albumIds }

                binding.progressBarCollectors.isVisible = false
                binding.collectorDetailsFragment.isVisible = true
            })

            viewModel.eventNetworkError.observe(viewLifecycleOwner, { isNetworkError ->
                if (isNetworkError) onNetworkError()
            })
        } catch (e: Exception) {
            Log.println(Log.ERROR, "Error", e.message.toString())
            val action = CollectorDetailsFragmentDirections.actionCollectorDetailsFragmentToErrorMessageFragment()
            view?.findNavController()?.navigate(action)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        try {
            binding.collectorCommentsRv.layoutManager = LinearLayoutManager(context)
            binding.collectorCommentsRv.adapter = collectorCommentsAdapter

            binding.collectorPerformersRv.layoutManager = LinearLayoutManager(context)
            binding.collectorPerformersRv.adapter = collectorMusiciansAdapter

            binding.collectorAlbumsRv.layoutManager = LinearLayoutManager(context)
            binding.collectorAlbumsRv.adapter = collectorAlbumsAdapter

            val txtName: TextView = view.findViewById(R.id.CollectorDetailsName)
            txtName.text = args.collector.name

            val txtEmail: TextView = view.findViewById(R.id.CollectorDetailsEmail)
            txtEmail.text = args.collector.email
        } catch (e: Exception) {
            Log.println(Log.ERROR, "Error", e.message.toString())
            val action = CollectorDetailsFragmentDirections.actionCollectorDetailsFragmentToErrorMessageFragment()
            view.findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onNetworkError() {
        if (!viewModel.isNetworkErrorShown.value!!) {
            val action = CollectorDetailsFragmentDirections.actionCollectorDetailsFragmentToErrorMessageFragment()
            view?.findNavController()?.navigate(action)
            Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
        }
    }
}