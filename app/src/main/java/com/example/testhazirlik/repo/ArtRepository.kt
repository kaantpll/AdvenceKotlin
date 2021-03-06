package com.example.testhazirlik.repo

import androidx.lifecycle.LiveData
import com.example.testhazirlik.database.ArtDao
import com.example.testhazirlik.model.Art
import com.example.testhazirlik.model.ImageResponse
import com.example.testhazirlik.service.RetrofitApi
import com.example.testhazirlik.util.Resource
import javax.inject.Inject

class ArtRepository @Inject constructor(
        private var artDao : ArtDao,
        private var retrofit : RetrofitApi
) : ArtRepositoryInterface{
    override suspend fun insert(art: Art) {
        artDao.insertArt(art)
    }

    override suspend fun delete(art: Art) {
        artDao.deleteArt(art)
    }

    override fun getData(): LiveData<List<Art>> {
        return artDao.observeArts()
    }

    override suspend fun selectImage(imageString: String): Resource<ImageResponse> {
        return try {

            val response = retrofit.imageSearch(imageString)
            if(response.isSuccessful){
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("Error",null)
            }else{
                Resource.error("Error",null)
            }

        }catch (e : Exception){
            Resource.error("No Data",null)
        }
    }
}