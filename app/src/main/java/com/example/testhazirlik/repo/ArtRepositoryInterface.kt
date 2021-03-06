package com.example.testhazirlik.repo

import androidx.lifecycle.LiveData
import com.example.testhazirlik.model.Art
import com.example.testhazirlik.model.ImageResponse
import com.example.testhazirlik.util.Resource

interface ArtRepositoryInterface {

    suspend fun insert(art : Art)

    suspend fun delete(art : Art)

    fun getData() : LiveData<List<Art>>

    suspend fun selectImage(imageString : String) : Resource<ImageResponse>

}