package com.example.vinilos


import androidx.test.espresso.DataInteraction
import androidx.test.espresso.ViewInteraction
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent

import androidx.test.InstrumentationRegistry.getInstrumentation
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*

import com.example.vinilos.R
import com.example.vinilos.util.EspressoIdlingResource

import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.anything
import org.hamcrest.Matchers.`is`
import org.junit.After
import org.junit.Before
import java.util.*

@LargeTest
@RunWith(AndroidJUnit4::class)
class AssociateAlbumTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Before
    fun registerIdlingResource() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @After
    fun unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }

    @Test
    fun associateAlbumTest() {
        val frameLayout = onView(
            allOf(
                withId(R.id.navigation_musicos), withContentDescription("Músicos"),
                withParent(withParent(withId(R.id.navigationView))),
                isDisplayed()
            )
        )
        frameLayout.check(matches(isDisplayed()))

        val bottomNavigationItemView = onView(
            allOf(
                withId(R.id.navigation_musicos), withContentDescription("Músicos"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.navigationView),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        bottomNavigationItemView.perform(click())

        val cardView = onView(
            allOf(
                withParent(
                    allOf(
                        withId(R.id.musicianRv),
                        withParent(IsInstanceOf.instanceOf(android.view.ViewGroup::class.java))
                    )
                ),
                isDisplayed()
            )
        )
        cardView.check(matches(isDisplayed()))

        val recyclerView = onView(
            allOf(
                withId(R.id.musicianRv),
                childAtPosition(
                    withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                    1
                )
            )
        )
        recyclerView.perform(actionOnItemAtPosition<ViewHolder>(0, click()))

        var menuName = "Más opciones"
        val localeInPhone = Locale.getDefault()
        if(localeInPhone == Locale.US)
            menuName = "More options"
        else if(localeInPhone.toString() == "pt_BR")
            menuName = "Mais opções"


        val imageView = onView(
            allOf(
                withContentDescription(menuName),
                withParent(withParent(withId(R.id.action_bar))),
                isDisplayed()
            )
        )
        imageView.check(matches(isDisplayed()))

        val overflowMenuButton = onView(
            allOf(
                withContentDescription(menuName),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.action_bar),
                        1
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        overflowMenuButton.perform(click())


        val materialTextView = onView(
            allOf(
                withId(R.id.title), withText("Asociar Album"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.content),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        materialTextView.perform(click())

        val textView = onView(
            allOf(
                withText("Lista de álbumes"), withContentDescription("Lista de Albumes"),
                withParent(withParent(IsInstanceOf.instanceOf(androidx.cardview.widget.CardView::class.java))),
                isDisplayed()
            )
        )
        textView.check(matches(withText("Lista de álbumes")))

        /*val appCompatSpinner = onView(
            allOf(
                withId(R.id.album_list_spinner), withContentDescription("Seleccion de albumes"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("com.google.android.material.card.MaterialCardView")),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        appCompatSpinner.perform(click())*/



        val materialButton = onView(
            allOf(
                withId(R.id.add_album_to_musician_button),
                withContentDescription("Asociar album"),
                isDisplayed()
            )
        )
        materialButton.perform(click())
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
