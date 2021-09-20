package com.afiniti.kiosk.scalabletask.ui.home

import android.os.Build
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.afiniti.kiosk.scalabletask.R
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(maxSdk = Build.VERSION_CODES.P, minSdk = Build.VERSION_CODES.P)
class HomeFragmentTest {

    private lateinit var scenario: FragmentScenario<HomeFragment>

    @Before
    fun setUp(){
        scenario = launchFragmentInContainer( themeResId = R.style.Theme_ScalableTask, initialState = Lifecycle.State.RESUMED)
    }

    @Test
    fun `when repos are requested,check if its visibility`() {
        // trigger update event
        scenario.onFragment {

            // then assert validation errors visible
            Espresso.onView(withId(R.id.lblPopular)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
            Espresso.onView(withId(R.id.recyclerPopularRepos)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        }
    }
}