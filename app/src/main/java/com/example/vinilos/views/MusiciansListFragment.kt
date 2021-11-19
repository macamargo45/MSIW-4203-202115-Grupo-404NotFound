package com.example.vinilos.views

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vinilos.databinding.MusiciansListFragmentBinding
import com.example.vinilos.viewmodels.MusiciansListViewModel
import com.example.vinilos.views.adapters.MusiciansListAdapter

class MusiciansListFragment : Fragment() {
    private var _binding: MusiciansListFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: MusiciansListViewModel
    private var viewModelAdapter: MusiciansListAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = MusiciansListFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModelAdapter = MusiciansListAdapter()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        try {
            recyclerView = binding.musicianRv
            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.adapter = viewModelAdapter
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