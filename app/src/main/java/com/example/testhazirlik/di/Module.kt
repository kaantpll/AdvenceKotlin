package com.example.testhazirlik.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.testhazirlik.R
import com.example.testhazirlik.database.ArtDao
import com.example.testhazirlik.database.ArtDatabase
import com.example.testhazirlik.repo.ArtRepository
import com.example.testhazirlik.repo.ArtRepositoryInterface
import com.example.testhazirlik.service.RetrofitApi
import com.example.testhazirlik.util.Util.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
object Module {

    @Singleton
    @Provides
    fun injectRoom(@ApplicationContext context: Context) = Room.databaseBuilder(
            context,
            ArtDatabase::class.java,
            "artdatabase"
    ).build()

    @Singleton
    @Provides
    fun injectDao(database : ArtDatabase) = database.artDao()

    @Singleton
    @Provides
    fun injectRetrofit() : RetrofitApi {
        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RetrofitApi::class.java)
    }

    @Singleton
    @Provides
    fun injectNormalRepo(dao : ArtDao,api : RetrofitApi) = ArtRepository(dao,api) as ArtRepositoryInterface

    @Singleton
    @Provides
    fun injectGlide(@ApplicationContext context : Context) = Glide.with(context).setDefaultRequestOptions(RequestOptions().placeholder(R.drawable.ic_launcher_background).error(R.drawable.ic_launcher_background))

}