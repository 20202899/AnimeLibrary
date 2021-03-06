package com.carlos.silva.animelibrary.presentation.season

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.OvershootInterpolator
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
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

    private var isCollapsed = false

    enum class MotionStatus {
        MOTION_DEFAULT,
        MOTION_COLLAPSE,
        MOTION_FULL
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

        fabAnim()

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
            adapter.refresh(it.anime)
            refresh.isRefreshing = false
            recyclerviewAnim()
        })

        seasonViewModel.motionStatus.observe(viewLifecycleOwner, Observer {

        })

        fab.setOnClickListener {
            motion.transitionToEnd()
        }

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

    private fun fabAnim() {
        val scaleX = PropertyValuesHolder.ofFloat("scaleX", 0f, 1f)
        val scaleY = PropertyValuesHolder.ofFloat("scaleY", 0f, 1f)
        val objectAnimator = ObjectAnimator.ofPropertyValuesHolder(fab, scaleX, scaleY)
        objectAnimator.interpolator = OvershootInterpolator()
        objectAnimator.duration = 300
        objectAnimator.start()
    }

    override fun onRefresh() {
        seasonViewModel.getSeason()
    }
}