package com.carlos.silva.animelibrary.framework

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalStateException

object AnimeLibraryViewModelFactory : ViewModelProvider.Factory {

    private lateinit var application: Application
    private lateinit var interactors: Interactors

    fun inject(application: Application, interactors: Interactors) {
        this.application = application
        this.interactors = interactors
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (AnimeLibraryViewModel::class.java.isAssignableFrom(modelClass)) {
            return modelClass.getConstructor(Application::class.java, Interactors::class.java)
                .newInstance(application, interactors)
        }

        throw IllegalStateException("Error Inject")
    }
}