package com.example.vinilos


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.example.vinilos.util.EspressoIdlingResource
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class MusicianListTest {

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
    fun musicianListTest() {
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

        // Debe contener una lista de musicos con un elemento
        val musicianRv = onView(
            allOf(
                withId(R.id.musicianRv),
                isDisplayed()
            )
        )
        musicianRv.check(matches(hasChildCount(1)))

        // Debe contener un musico de nombre Rubén Blades Bellido de Luna
        val textView = onView(
            allOf(
                withId(R.id.musicianName),
                withText("Rubén Blades Bellido de Luna"),
                withTagValue(allOf(
                    Matchers.instanceOf(Int::class.java),
                    Matchers.equalTo(100 as Int?)
                )),
                withParent(withParent(IsInstanceOf.instanceOf(android.view.ViewGroup::class.java))),
                isDisplayed()
            )
        )
        textView.check(matches(withText("Rubén Blades Bellido de Luna")))

        // Debe contener la imagen del musico Rubén Blades Bellido de Luna
        val imageView = onView(
            allOf(
                withId(R.id.imageView),
                withParent(IsInstanceOf.instanceOf(androidx.cardview.widget.CardView::class.java)),
                isDisplayed()
            )
        )
        imageView.check(matches(isDisplayed()))
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
