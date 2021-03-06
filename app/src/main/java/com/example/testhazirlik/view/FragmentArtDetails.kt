package com.example.testhazirlik.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.RequestManager
import com.example.testhazirlik.R
import com.example.testhazirlik.databinding.FragmentsDetailsBinding
import com.example.testhazirlik.util.Status
import com.example.testhazirlik.viewmodel.ArtViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FragmentArtDetails @Inject constructor(
        val glide : RequestManager
) : Fragment(R.layout.fragments_details) {

   private var fragmentBinding : FragmentsDetailsBinding? = null
    lateinit var viewModel : ArtViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bind = FragmentsDetailsBinding.bind(view)
        fragmentBinding = bind

        viewModel = ViewModelProvider(requireActivity()).get(ArtViewModel::class.java)


        observe()

        bind.imageView.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentArtDetails_to_fragmentsImage)
        }

        val callback = object :  OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }

        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)

        bind.saveButton.setOnClickListener{
            viewModel.makeArt(bind.authorEditText.text.toString(),bind.artNamEditText.text.toString(),bind.artYearsEditText.text.toString())
        }

    }

    private fun observe() {
        viewModel.selectedImageUrl.observe(viewLifecycleOwner, Observer { url ->
            fragmentBinding?.let {
                glide.load(url).into(it.imageView)
            }
        })

        viewModel.insertArtMessage.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    Toast.makeText(requireActivity(),"Success",Toast.LENGTH_LONG).show()
                    findNavController().navigateUp()
                    viewModel.resetInsertArtMsg()
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