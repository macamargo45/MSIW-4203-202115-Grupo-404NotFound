package com.example.vinilos


import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.example.vinilos.util.EspressoIdlingResource
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
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
class NavigationTest {

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
    fun navigationTest() {

        val textView = onView(
            allOf(
                withText("Albumes"),
                withParent(
                    allOf(
                        withId(R.id.action_bar),
                        withParent(withId(R.id.action_bar_container))
                    )
                ),
                isDisplayed()
            )
        )
        textView.check(matches(withText("Albumes")))

        val imageView = onView(
            allOf(
                withId(R.id.navigation_bar_item_icon_view),
                withParent(
                    allOf(
                        withId(R.id.navigation_albums), withContentDescription("Albumes"),
                        withParent(IsInstanceOf.instanceOf(android.view.ViewGroup::class.java))
                    )
                ),
                isDisplayed()
            )
        )
        imageView.check(matches(isDisplayed()))

        val textView2 = onView(
            allOf(
                withId(R.id.navigation_bar_item_large_label_view), withText("Albumes"),
                withParent(
                    allOf(
                        withId(R.id.navigation_bar_item_labels_group),
                        withParent(
                            allOf(
                                withId(R.id.navigation_albums),
                                withContentDescription("Albumes")
                            )
                        )
                    )
                ),
                isDisplayed()
            )
        )
        textView2.check(matches(withText("Albumes")))

        val textView3 = onView(
            allOf(
                withId(R.id.navigation_bar_item_small_label_view), withText("Músicos"),
                withParent(
                    allOf(
                        withId(R.id.navigation_bar_item_labels_group),
                        withParent(
                            allOf(
                                withId(R.id.navigation_musicos),
                                withContentDescription("Músicos")
                            )
                        )
                    )
                ),
                isDisplayed()
            )
        )
        textView3.check(matches(withText("Músicos")))

        val textView4 = onView(
            allOf(
                withId(R.id.navigation_bar_item_small_label_view), withText("Coleccionistas"),
                withParent(
                    allOf(
                        withId(R.id.navigation_bar_item_labels_group),
                        withParent(
                            allOf(
                                withId(R.id.navigation_coleccionistas),
                                withContentDescription("Coleccionistas")
                            )
                        )
                    )
                ),
                isDisplayed()
            )
        )
        textView4.check(matches(withText("Coleccionistas")))

        val textView5 = onView(
            allOf(
                withId(R.id.navigation_bar_item_small_label_view), withText("Premios"),
                withParent(
                    allOf(
                        withId(R.id.navigation_bar_item_labels_group),
                        withParent(
                            allOf(
                                withId(R.id.navigation_premios),
                                withContentDescription("Premios")
                            )
                        )
                    )
                ),
                isDisplayed()
            )
        )
        textView5.check(matches(withText("Premios")))

        val textView6 = onView(
            allOf(
                withText("Albumes"),
                withParent(
                    allOf(
                        withId(R.id.action_bar),
                        withParent(withId(R.id.action_bar_container))
                    )
                ),
                isDisplayed()
            )
        )
        textView6.check(matches(withText("Albumes")))

        val recyclerView = onView(
            allOf(
                withId(R.id.albumsRv),
                childAtPosition(
                    withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                    1
                )
            )
        )
        recyclerView.perform(actionOnItemAtPosition<ViewHolder>(3, click()))

        val bottomNavigationItemView = onView(
            allOf(
                withId(R.id.navigation_albums), withContentDescription("Albumes"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.navigationView),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        bottomNavigationItemView.perform(click())
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
