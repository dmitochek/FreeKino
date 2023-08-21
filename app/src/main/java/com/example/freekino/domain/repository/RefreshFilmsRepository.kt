package com.example.freekino.domain.repository

import com.example.freekino.domain.models.VideoBasicInfo

interface RefreshFilmsRepository {
    fun getFilms(category: Int): Array<VideoBasicInfo?>
}