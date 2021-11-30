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
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.vinilos.R
import com.example.vinilos.databinding.CollectorDetailsFragmentBinding
import com.example.vinilos.viewmodels.CollectorDetailsViewModel

class CollectorDetailsFragment: Fragment() {
    private var _binding: CollectorDetailsFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: CollectorDetailsViewModel
    private val args: CollectorDetailsFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CollectorDetailsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        try {
            super.onActivityCreated(savedInstanceState)
            val activity = requireNotNull(this.activity) {
                "You can only access the viewModel after onActivityCreated()"
            }
            viewModel =
                ViewModelProvider(this, CollectorDetailsViewModel.Factory(activity.application))[CollectorDetailsViewModel::class.java]
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