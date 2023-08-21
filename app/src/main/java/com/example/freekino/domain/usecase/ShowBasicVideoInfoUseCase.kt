package com.example.freekino.domain.usecase

import com.example.freekino.domain.models.VideoBasicInfo
import com.example.freekino.domain.repository.FilmsRepository

class ShowBasicVideoInfoUseCase(private val filmsRepository: FilmsRepository) {
     fun execute(category: Int): Array<VideoBasicInfo?> {
         return filmsRepository.getFilms(category)
    }
}
