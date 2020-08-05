package com.carlos.silva.core.data

import com.carlos.silva.core.domain.Season

interface AnimeSeasonDataSource {
    suspend fun getAnimeSeason(): Season?
}