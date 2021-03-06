package com.example.testhazirlik.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.testhazirlik.R
import com.example.testhazirlik.adapter.ImageAdapter
import com.example.testhazirlik.databinding.FragmentsImageBinding
import com.example.testhazirlik.util.Status
import com.example.testhazirlik.viewmodel.ArtViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class FragmentsImage @Inject constructor(
        private val imageAdapter : ImageAdapter
) : Fragment(R.layout.fragments_image) {

    lateinit var  viewModel : ArtViewModel

    private var fragmentBind : FragmentsImageBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel = ViewModelProvider(requireActivity()).get(ArtViewModel::class.java)
        val binding = FragmentsImageBinding.bind(view)
        fragmentBind = binding

        var job : Job? =null

        binding.searchEditText.addTextChangedListener {
            job?.cancel()
            job = lifecycleScope.launch{
                delay(1000)
                it?.let {
                    if(it.toString().isNotEmpty()){
                        viewModel.searchForImage(it.toString())
                    }
                }
            }
        }
        observe()

        binding.imageRecyclerview.adapter = imageAdapter
        binding.imageRecyclerview.layoutManager = GridLayoutManager(requireContext(),3)

        imageAdapter.setOnItemClickListener {
            findNavController().popBackStack()
            viewModel.setSelectedImage(it)
        }

    }

    private fun observe() {

        viewModel.imageList.observe(viewLifecycleOwner, Observer {
            when(it.status) {
                Status.SUCCESS -> {
                    val urls = it.data?.hits?.map { imageResult ->  imageResult.previewURL }
                    imageAdapter.images = urls ?: listOf()


                }

                Status.ERROR -> {
                    Toast.makeText(requireContext(),it.message ?: "Error",Toast.LENGTH_LONG).show()


                }

                Status.LOADING -> {

                }
            }

        })

    }
}