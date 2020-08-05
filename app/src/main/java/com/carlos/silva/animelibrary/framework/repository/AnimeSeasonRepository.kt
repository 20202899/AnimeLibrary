package com.carlos.silva.animelibrary.framework.repository

import PageQuery
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.coroutines.toDeferred
import com.carlos.silva.animelibrary.framework.ApolloConfiguration
import com.carlos.silva.core.data.AnimeSeasonDataSource
import com.carlos.silva.core.domain.Anime
import com.carlos.silva.core.domain.Season
import type.MediaSeason

class AnimeSeasonRepository : AnimeSeasonDataSource {
    private val apolloClient = ApolloConfiguration.getInstance()
    override suspend fun getAnimeSeason(): Season? {
        val respose = apolloClient.query(
            PageQuery(
                page = Input.optional(1),
                perPage = Input.optional(18),
                seasonYear = Input.optional(2020),
                season = Input.optional(MediaSeason.SUMMER)
            )
        ).toDeferred().await()
        return Season(
            mutableListOf<Anime>().apply {
                respose.data?.page?.media?.map { map ->
                    Anime(
                        map?.title?.romaji,
                        map?.coverImage?.extraLarge,
                        map?.coverImage?.color
                    )
                }?.let {
                    addAll(it)
                }
            }
        )
    }
}