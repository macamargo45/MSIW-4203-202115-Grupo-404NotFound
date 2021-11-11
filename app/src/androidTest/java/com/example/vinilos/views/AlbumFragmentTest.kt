package com.example.vinilos.views

import androidx.test.espresso.Espresso.*
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.example.vinilos.MainActivity
import com.example.vinilos.R
import com.example.vinilos.util.EspressoIdlingResource
import com.example.vinilos.views.adapters.AlbumsAdapter
import com.example.vinilos.views.data.FakeAlbumData
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class AlbumFragmentTest {
    @get: Rule
    val activityRUle = ActivityScenarioRule(MainActivity::class.java)

    val LIST_ALBUM_IN_TEST = 10;
    val ALBUM_IN_TEST = FakeAlbumData.albums[LIST_ALBUM_IN_TEST]

    @Before
    fun registerIdlingResource() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @After
    fun unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }

    @Test
    fun test_isListFragmentVisible_onAppLaunch() {
        Thread.sleep(1500)
        onView(withId(R.id.albumsRv)).check(matches(isDisplayed()))
    }

    @Test
    fun test_selectListItem_isDetailFragmentVisible() {
        Thread.sleep(1500)
        onData(withId(R.id.albumsRv)).perform(
            actionOnItemAtPosition<AlbumsAdapter.AlbumViewHolder>(
                LIST_ALBUM_IN_TEST,
                click()
            )
        )
        onData(withId(R.id.albumName)).check(matches(withText(ALBUM_IN_TEST.name)))
    }

    @Test
    fun test_backNavigation_toAlbumListFragment() {
        Thread.sleep(1500)
        onData(withId(R.id.albumsRv)).perform(
            actionOnItemAtPosition<AlbumsAdapter.AlbumViewHolder>(
                LIST_ALBUM_IN_TEST,
                click()
            )
        )
        onData(withId(R.id.albumName)).check(matches(withText(ALBUM_IN_TEST.name)))
        pressBack()
        onData(withId(R.id.albumsRv)).check(matches(isDisplayed()))
        Thread.sleep(1500)
    }
}