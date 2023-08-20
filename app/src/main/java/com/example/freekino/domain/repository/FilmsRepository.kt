package com.example.freekino.domain.repository
import com.example.freekino.domain.models.VideoBasicInfo

interface FilmsRepository {
    fun getFilms(): Array<VideoBasicInfo?>
}