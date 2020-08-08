package com.carlos.silva.animelibrary.presentation.season

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.carlos.silva.animelibrary.framework.AnimeLibraryViewModel
import com.carlos.silva.animelibrary.framework.Interactors
import com.carlos.silva.core.domain.Anime
import com.carlos.silva.core.domain.Season
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class SeasonViewModel(
    private val application: Application,
    private val interactors: Interactors
) : AnimeLibraryViewModel(application, interactors) {

    val seasonLiveData = MutableLiveData<Season>()
    val animeLiveData = MutableLiveData<Anime>()
    val motionStatus = MutableLiveData<SeasonFragment.MotionStatus>(
        SeasonFragment.MotionStatus.MOTION_DEFAULT
    )

    fun getSeason() = viewModelScope.launch {
        seasonLiveData.value = async { interactors.getAnimeSeason() }.await()
    }

    fun setOnMotionStatus(status: SeasonFragment.MotionStatus)  {
        motionStatus.value = status
    }

    fun getCurrentStatus() = motionStatus.value
}