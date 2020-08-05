package com.carlos.silva.animelibrary.framework

import android.app.Application
import androidx.lifecycle.ViewModel

open class AnimeLibraryViewModel (
    private val application: Application,
    private val interactors: Interactors
) : ViewModel()