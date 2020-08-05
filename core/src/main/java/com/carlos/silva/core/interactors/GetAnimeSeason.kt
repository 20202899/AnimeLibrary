package com.carlos.silva.core.interactors

import com.carlos.silva.core.data.AnimeSeasonDataRepository

class GetAnimeSeason(private val animeSeasonDataRepository: AnimeSeasonDataRepository) {
    suspend operator fun invoke() = animeSeasonDataRepository.getAnimeSeason()
}