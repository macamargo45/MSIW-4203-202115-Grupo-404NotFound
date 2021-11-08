package com.example.vinilos.views

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.example.vinilos.MainActivity
import com.example.vinilos.R
import com.example.vinilos.views.adapters.AlbumsAdapter
import com.example.vinilos.views.data.FakeAlbumData
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class AlbumFragmentTest {
    @get: Rule
    val activityRUle = ActivityScenarioRule(MainActivity::class.java)

    val LIST_ALBUM_IN_TEST = 10;
    val ALBUM_IN_TEST = FakeAlbumData.albums[LIST_ALBUM_IN_TEST]

    @Test
    fun test_isListFragmentVisible_onAppLaunch() {
        onView(withId(R.id.albumsRv)).check(matches(isDisplayed()))
    }

    @Test
    fun test_selectListItem_isDetailFragmentVisible() {
        onView(withId(R.id.albumsRv)).perform(
            actionOnItemAtPosition<AlbumsAdapter.AlbumViewHolder>(
                LIST_ALBUM_IN_TEST,
                click()
            )
        )
        onView(withId(R.id.albumName)).check(matches(withText(ALBUM_IN_TEST.name)))
    }

    @Test
    fun test_backNavigation_toAlbumListFragment() {
        onView(withId(R.id.albumsRv)).perform(
            actionOnItemAtPosition<AlbumsAdapter.AlbumViewHolder>(
                LIST_ALBUM_IN_TEST,
                click()
            )
        )
        onView(withId(R.id.albumName)).check(matches(withText(ALBUM_IN_TEST.name)))
        pressBack()
        onView(withId(R.id.albumsRv)).check(matches(isDisplayed()))
    }

}