package com.example.freekino.data.repository

import android.util.Log
import android.widget.ListAdapter
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.toJsonString
import com.example.freekino.domain.models.VideoBasicInfo
import com.example.freekino.domain.repository.FilmsRepository
import com.source.GetmediaQuery
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking

class FilmsRepositoryImpl : FilmsRepository {
    val apolloClient = ApolloClient.Builder()
        .serverUrl("http://10.0.2.2:4000/")
        .build()

    suspend fun getData(): GetmediaQuery.Data? {
        val response = apolloClient.query(GetmediaQuery()).execute()
        return response.data

    }

    override fun getFilms(): Array<VideoBasicInfo?> = runBlocking{
        val response = getData()
            ?.getmedia
            ?.map{ it?.toSimpleFilm() }
            ?: emptyList()

        return@runBlocking response.toTypedArray()
    }
}