package com.carlos.silva.core.data

class AnimeSeasonDataRepository (private val animeSeasonDataSource: AnimeSeasonDataSource) {
    suspend fun getAnimeSeason() = animeSeasonDataSource.getAnimeSeason()
}