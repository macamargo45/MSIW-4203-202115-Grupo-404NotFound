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
import com.example.vinilos.databinding.MusiciansListFragmentBinding
import com.example.vinilos.viewmodels.MusiciansListViewModel
import com.example.vinilos.views.adapters.MusiciansListAdapter

class MusiciansListFragment : Fragment() {
    private var _binding: MusiciansListFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MusiciansListViewModel
    private var viewModelAdapter: MusiciansListAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        if(_binding == null)
            _binding = MusiciansListFragmentBinding.inflate(inflater, container, false)

        if(viewModelAdapter == null)
            viewModelAdapter = MusiciansListAdapter()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        try {
            binding.musicianRv.layoutManager = LinearLayoutManager(context)
            binding.musicianRv.adapter = viewModelAdapter
        } catch (e: Exception) {
            Log.println(Log.ERROR, "Error", e.message.toString())
            val action = CollectorsListFragmentDirections.actionCollectorsListFragmentToErrorMessageFragment()
            view.findNavController().navigate(action)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        try {


            super.onActivityCreated(savedInstanceState)
            val activity = requireNotNull(this.activity) {
                "You can only access the viewModel after onActivityCreated()"
            }
            binding.progressBarMusicians.isVisible = true
            binding.musicianRv.isVisible = false

            viewModel = ViewModelProvider(this, MusiciansListViewModel.Factory(activity.application))[MusiciansListViewModel::class.java]
            viewModel.musicians.observe(viewLifecycleOwner, {
                it.apply {
                    viewModelAdapter!!.musicians = this

                    binding.musicianRv.isVisible = true
                    binding.progressBarMusicians.isVisible = false
                }
            })
            viewModel.eventNetworkError.observe(
                viewLifecycleOwner,
                { isNetworkError ->
                    if (isNetworkError) onNetworkError()
                })
        } catch (e: Exception) {
            Log.println(Log.ERROR, "Error", e.message.toString())
            val action = CollectorsListFragmentDirections.actionCollectorsListFragmentToErrorMessageFragment()
            view?.findNavController()?.navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onNetworkError() {
        if (!viewModel.isNetworkErrorShown.value!!) {
            val action = CollectorsListFragmentDirections.actionCollectorsListFragmentToErrorMessageFragment()
            view?.findNavController()?.navigate(action)
            Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()

        }
    }

}