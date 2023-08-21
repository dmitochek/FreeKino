package com.example.freekino.domain.usecase

import com.example.freekino.domain.models.VideoBasicInfo
import com.example.freekino.domain.repository.RefreshFilmsRepository

class RefreshCatalogUseCase(private val refreshFilmsRepository: RefreshFilmsRepository) {
    fun execute(category: Int): Array<VideoBasicInfo?> {
        return refreshFilmsRepository.getFilms(category)
    }
}