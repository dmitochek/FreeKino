package com.example.freekino.data.repository

import com.apollographql.apollo3.ApolloClient
import com.example.freekino.domain.models.VideoBasicInfo
import com.example.freekino.domain.repository.RefreshFilmsRepository
import com.source.RefreshQuery
import kotlinx.coroutines.runBlocking

class RefreshFilmsRepositoryImpl: RefreshFilmsRepository {
    val apolloClient = ApolloClient.Builder()
        .serverUrl("http://10.0.2.2:4000/")
        .build()

    suspend fun getData(category: Int): RefreshQuery.Data? {
        val response = apolloClient.query(RefreshQuery(category)).execute()
        return response.data

    }

    override fun getFilms(category: Int): Array<VideoBasicInfo?> = runBlocking{
        val response = getData(category)
            ?.refresh
            ?.map{ it?.toSimpleFilm() }
            ?: emptyList()

        return@runBlocking response.toTypedArray()
    }
}