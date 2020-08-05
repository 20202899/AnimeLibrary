package com.carlos.silva.animelibrary.framework

import android.app.Application
import com.carlos.silva.animelibrary.framework.repository.AnimeSeasonRepository
import com.carlos.silva.animelibrary.presentation.MainActivity
import com.carlos.silva.core.data.AnimeSeasonDataRepository
import com.carlos.silva.core.interactors.GetAnimeSeason

class AnimeLibraryApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        val animeSeasonDataRepository = AnimeSeasonDataRepository(
            AnimeSeasonRepository()
        )
        val interactors = Interactors(
            getAnimeSeason = GetAnimeSeason(animeSeasonDataRepository)
        )

        AnimeLibraryViewModelFactory.inject(this, interactors)
    }
}