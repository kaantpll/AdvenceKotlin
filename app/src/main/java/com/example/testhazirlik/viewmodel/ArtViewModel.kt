package com.example.testhazirlik.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testhazirlik.model.Art
import com.example.testhazirlik.model.ImageResponse
import com.example.testhazirlik.repo.ArtRepositoryInterface
import com.example.testhazirlik.util.Resource
import kotlinx.coroutines.launch

class ArtViewModel @ViewModelInject constructor(
        private val repositoryInterface: ArtRepositoryInterface
) : ViewModel() {

    val artList = repositoryInterface.getData()

    private val images = MutableLiveData<Resource<ImageResponse>>()
    val imageList : LiveData<Resource<ImageResponse>>
     get() = images

    private val selectedImage = MutableLiveData<String>()
    val selectedImageUrl : LiveData<String>
    get() = selectedImage

    private var insertArtMsg = MutableLiveData<Resource<Art>>()
    val insertArtMessage : LiveData<Resource<Art>>
        get() = insertArtMsg

    fun resetInsertArtMsg() {
        insertArtMsg = MutableLiveData<Resource<Art>>()
    }

    fun setSelectedImage(url : String) {
        selectedImage.postValue(url)
    }

    fun deleteArt(art: Art) = viewModelScope.launch {
        repositoryInterface.delete(art)
    }

    fun insertArt(art: Art) = viewModelScope.launch {
        repositoryInterface.insert(art)
    }

    fun makeArt(name : String, artistName : String, year : String) {
        if (name.isEmpty() || artistName.isEmpty() || year.isEmpty() ) {
            insertArtMsg.postValue(Resource.error("Enter name, artist, year", null))
            return
        }
        val yearInt = try {
            year.toInt()
        } catch (e: Exception) {
            insertArtMsg.postValue(Resource.error("Year should be number",null))
            return
        }

        val art = Art(name, artistName, yearInt,selectedImage.value?: "")
        insertArt(art)
        setSelectedImage("")
        insertArtMsg.postValue(Resource.success(art))
    }
    fun searchForImage (searchString : String) {

        if(searchString.isEmpty()) {
            return
        }
        images.value = Resource.loading(null)
        viewModelScope.launch {
            val response = repositoryInterface.selectImage(searchString)
            images.value = response
        }
    }


}