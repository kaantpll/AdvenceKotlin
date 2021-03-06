package com.example.testhazirlik.view

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.testhazirlik.R
import com.example.testhazirlik.adapter.ArtAdapter
import com.example.testhazirlik.databinding.FragmetsArtBinding
import com.example.testhazirlik.viewmodel.ArtViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class FragmentArt @Inject constructor(
        val artAdapter : ArtAdapter
): Fragment(R.layout.fragmets_art) {

    private var fragmentsArtBinding : FragmetsArtBinding? = null
    lateinit var viewModel : ArtViewModel
    private val swipeCallback = object  : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT){
        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
          val layoutPositiono = viewHolder.layoutPosition
            val selectedArt = artAdapter.arts[layoutPositiono]
            viewModel.deleteArt(selectedArt)
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var binding = FragmetsArtBinding.bind(view)
        fragmentsArtBinding = binding

        viewModel = ViewModelProvider(requireActivity()).get(ArtViewModel::class.java)

        observe()

        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentArt_to_fragmentArtDetails)
        }

        binding.artRecyclerview.adapter = artAdapter
        binding.artRecyclerview.layoutManager = LinearLayoutManager(requireContext())
        ItemTouchHelper(swipeCallback).attachToRecyclerView(binding.artRecyclerview)


    }

    private fun observe() {
        viewModel.artList.observe(viewLifecycleOwner, Observer {
            artAdapter.arts = it
        })
    }
    override fun onDestroyView() {
       fragmentsArtBinding= null
        super.onDestroyView()
    }

}