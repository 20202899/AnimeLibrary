package com.carlos.silva.animelibrary.presentation.season

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.carlos.silva.animelibrary.R
import com.carlos.silva.animelibrary.framework.AnimeLibraryViewModelFactory
import kotlinx.android.synthetic.main.season_fragment.*

class SeasonFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    private lateinit var seasonViewModel: SeasonViewModel
    private val adapter = SeasonAdapter().apply {
        onClickListener = {
            seasonViewModel.animeLiveData.value = it
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.season_fragment,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(recyclerview) {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(
                requireContext(),
                2,
                GridLayoutManager.VERTICAL,
                false
            )
            adapter = this@SeasonFragment.adapter
        }

        with(refresh) {
            setOnRefreshListener(this@SeasonFragment)
            isRefreshing = true
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        seasonViewModel = ViewModelProviders.of(this, AnimeLibraryViewModelFactory).get(
            SeasonViewModel::class.java
        )

        seasonViewModel.seasonLiveData.observe(viewLifecycleOwner, Observer {
            adapter.addAnimes(it.anime)
            refresh.isRefreshing = false
            recyclerviewAnim()
        })

        seasonViewModel.animeLiveData.observe(viewLifecycleOwner, Observer {

        })

        seasonViewModel.getSeason()
    }

    private fun recyclerviewAnim() {
        val objectAnimator = ObjectAnimator.ofFloat(
            recyclerview,
            "y",
            -2000f,
            0f
        ).apply {
            duration = 600
            interpolator = AccelerateDecelerateInterpolator()
        }

        objectAnimator.start()

    }

    override fun onRefresh() {
        seasonViewModel.getSeason()
    }
}