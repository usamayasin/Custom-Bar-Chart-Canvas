package com.afiniti.kiosk.scalabletask.ui.detail

import android.os.Build
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.afiniti.kiosk.scalabletask.R
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config


@RunWith(AndroidJUnit4::class)
@Config(maxSdk = Build.VERSION_CODES.P, minSdk = Build.VERSION_CODES.P)
class RepoDetailFragmentTest{

    private lateinit var scenario: FragmentScenario<RepoDetailFragment>

    @Before
    fun setUp(){
        scenario = launchFragmentInContainer( themeResId = R.style.Theme_ScalableTask, initialState = Lifecycle.State.RESUMED)
    }

    @Test
    fun `all views are visible `(){
        scenario.onFragment {
            Espresso.onView(ViewMatchers.withId(R.id.tv_commit_count)).check(
                ViewAssertions.matches(
                    ViewMatchers.isDisplayed()))
            Espresso.onView(ViewMatchers.withId(R.id.tv_month)).check(
                ViewAssertions.matches(
                    ViewMatchers.isDisplayed()))
            Espresso.onView(ViewMatchers.withId(R.id.cv_bar)).check(
                ViewAssertions.matches(
                    ViewMatchers.isDisplayed()))

        }
    }
}