package com.example.freekino.data.repository

import com.apollographql.apollo3.ApolloClient
import com.example.freekino.domain.models.VideoBasicInfo
import com.example.freekino.domain.repository.FilmsRepository
import com.source.GetmediaQuery
import kotlinx.coroutines.runBlocking

class FilmsRepositoryImpl() : FilmsRepository {
    val apolloClient = ApolloClient.Builder()
        .serverUrl("http://10.0.2.2:4000/")
        .build()

    suspend fun getData(category: Int): GetmediaQuery.Data? {
        val response = apolloClient.query(GetmediaQuery(category)).execute()
        return response.data

    }

    override fun getFilms(category: Int): Array<VideoBasicInfo?> = runBlocking{
        val response = getData(category)
            ?.getmedia
            ?.map{ it?.toSimpleFilm() }
            ?: emptyList()

        return@runBlocking response.toTypedArray()
    }
}