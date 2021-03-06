package com.example.testhazirlik.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.bumptech.glide.RequestManager
import com.example.testhazirlik.adapter.ArtAdapter
import com.example.testhazirlik.adapter.ImageAdapter
import javax.inject.Inject

class FragmentFactory @Inject constructor(
        private val imageAdapter : ImageAdapter,
        private val artAdapter : ArtAdapter,
        private val glide : RequestManager
) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {

        return when(className){
            FragmentsImage::class.java.name -> FragmentsImage(imageAdapter)
            FragmentArt::class.java.name -> FragmentArt(artAdapter)
            FragmentArtDetails::class.java.name -> FragmentArtDetails(glide)
            else -> super.instantiate(classLoader, className)
        }


    }


}