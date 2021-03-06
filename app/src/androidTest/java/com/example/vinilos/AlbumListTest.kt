package com.example.vinilos

import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import com.example.vinilos.util.EspressoIdlingResource

import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

import org.junit.After
import org.junit.Before
import androidx.test.espresso.matcher.ViewMatchers.withTagValue
import org.hamcrest.Matchers.*


@LargeTest
@RunWith(AndroidJUnit4::class)
class AlbumListTest {

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
    fun albumListTest() {

        val textView = onView(
            allOf(
                withText("Lista de álbumes"),
                withParent(
                    allOf(
                        withId(R.id.action_bar),
                        withParent(withId(R.id.action_bar_container))
                    )
                ),
                isDisplayed()
            )
        )
        textView.check(matches(withText("Lista de álbumes")))

        val viewGroup = onView(
            allOf(
                withParent(
                    allOf(
                        withId(R.id.nav_host_fragment),
                        withParent(withId(R.id.nav_host_fragment))
                    )
                ),
                isDisplayed()
            )
        )
        viewGroup.check(matches(isDisplayed()))

        val textView2 = onView(
            allOf(
                withId(R.id.albumName), withText("Buscando América"),
                withParent(withParent(IsInstanceOf.instanceOf(android.view.ViewGroup::class.java))),
                isDisplayed()
            )
        )
        textView2.check(matches(withText("Buscando América")))


        val textView3 = onView(
            allOf(
                withId(R.id.albumPerformerNames),
                withText("Rubén Blades Bellido de Luna"),
                withParent(withParent(IsInstanceOf.instanceOf(android.view.ViewGroup::class.java))),
                withTagValue(allOf(instanceOf(Int::class.java), equalTo(100 as Int?))),
                isDisplayed()
            )
        )
        textView3.check(matches(withText("Rubén Blades Bellido de Luna")))

        val textView4 = onView(
            allOf(
                withId(R.id.albumName), withText("Poeta del pueblo"),
                withParent(withParent(IsInstanceOf.instanceOf(android.view.ViewGroup::class.java))),
                isDisplayed()
            )
        )
        textView4.check(matches(withText("Poeta del pueblo")))

        val textView5 = onView(
            allOf(
                withId(R.id.albumPerformerNames), withText("Rubén Blades Bellido de Luna"),
                withParent(withParent(IsInstanceOf.instanceOf(android.view.ViewGroup::class.java))),
                withTagValue(allOf(instanceOf(Int::class.java), equalTo(101 as Int?))),
                isDisplayed()
            )
        )
        textView5.check(matches(withText("Rubén Blades Bellido de Luna")))

        val textView6 = onView(
            allOf(
                withId(R.id.albumName), withText("A Night at the Opera"),
                withParent(withParent(IsInstanceOf.instanceOf(android.view.ViewGroup::class.java))),
                isDisplayed()
            )
        )
        textView6.check(matches(withText("A Night at the Opera")))

        val textView7 = onView(
            allOf(
                withId(R.id.albumPerformerNames),
                withParent(withParent(IsInstanceOf.instanceOf(android.view.ViewGroup::class.java))),
                withTagValue(allOf(instanceOf(Int::class.java), equalTo(102 as Int?))),
                isDisplayed()
            )
        )
        textView7.check(matches(isDisplayed()))

        val textView8 = onView(
            allOf(
                withId(R.id.albumName), withText("A Day at the Races"),
                withParent(withParent(IsInstanceOf.instanceOf(android.view.ViewGroup::class.java))),
                isDisplayed()
            )
        )
        textView8.check(matches(withText("A Day at the Races")))

        val textView9 = onView(
            allOf(
                withId(R.id.albumPerformerNames),
                withParent(withParent(IsInstanceOf.instanceOf(android.view.ViewGroup::class.java))),
                withTagValue(allOf(instanceOf(Int::class.java), equalTo(103 as Int?))),
                isDisplayed()
            )
        )
        textView9.check(matches(isDisplayed()))

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

        val imageView2 = onView(
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
        imageView2.check(matches(isDisplayed()))
    }
}
