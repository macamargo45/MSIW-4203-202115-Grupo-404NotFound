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
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*

import com.example.vinilos.R

import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.*
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class CreateAlbumTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun createAlbumTest() {
        val frameLayout = onView(
            allOf(
                withId(R.id.navigation_albums), withContentDescription("Albumes"),
                withParent(withParent(withId(R.id.navigationView))),
                isDisplayed()
            )
        )
        frameLayout.check(matches(isDisplayed()))

        val textView = onView(
            allOf(
                withId(R.id.add_album), withContentDescription("Agregar Album"),
                withParent(withParent(withId(R.id.action_bar))),
                isDisplayed()
            )
        )
        textView.check(matches(isDisplayed()))

        val actionMenuItemView = onView(
            allOf(
                withId(R.id.add_album), withContentDescription("Agregar Album"),
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
        actionMenuItemView.perform(click())

        val textView2 = onView(
            allOf(
                withText("Agregar Album"),
                withParent(
                    allOf(
                        withId(R.id.action_bar),
                        withParent(withId(R.id.action_bar_container))
                    )
                ),
                isDisplayed()
            )
        )
        textView2.check(matches(withText("Agregar Album")))

        val textView3 = onView(
            allOf(
                withId(R.id.nameLabel), withText("Nombre"),
                withParent(withParent(withId(R.id.nav_host_fragment))),
                isDisplayed()
            )
        )
        textView3.check(matches(withText("Nombre")))

        val appCompatEditText = onView(
            allOf(
                withId(R.id.nameTextbox),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nav_host_fragment),
                        0
                    ),
                    1
                )
            )
        )
        appCompatEditText.perform(scrollTo(), replaceText("album de prueba"), closeSoftKeyboard())

        val appCompatEditText2 = onView(
            allOf(
                withId(R.id.nameTextbox), withText("album de prueba"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nav_host_fragment),
                        0
                    ),
                    1
                )
            )
        )
        appCompatEditText2.perform(pressImeActionButton())



        val textView4 = onView(
            allOf(
                withText("Carátula"),
                withParent(withParent(withId(R.id.nav_host_fragment))),
                isDisplayed()
            )
        )
        textView4.check(matches(withText("Carátula")))

        val appCompatEditText3 = onView(
            allOf(
                withId(R.id.coverTextobox),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nav_host_fragment),
                        0
                    ),
                    3
                )
            )
        )
        appCompatEditText3.perform(scrollTo(), click())

        val appCompatEditText4 = onView(
            allOf(
                withId(R.id.coverTextobox),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nav_host_fragment),
                        0
                    ),
                    3
                )
            )
        )
        appCompatEditText4.perform(scrollTo(), longClick())

        val appCompatEditText5 = onView(
            allOf(
                withId(R.id.coverTextobox),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nav_host_fragment),
                        0
                    ),
                    3
                )
            )
        )
        appCompatEditText5.perform(
            scrollTo(),
            replaceText("https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.todorock.com%2Fcuriosidades%2Firon-maiden-27-anos-de-fear-of-the-dark-y-estas-son-sus-curiosidades%2F&psig=AOvVaw34ft6iN3n4T5e1_cQ3UmS3&ust=1638587908702000&source=images&cd=vfe&ved=0CAsQjRxqFwoTCKClpOTVxvQCFQAAAAAdAAAAABAD"),
            closeSoftKeyboard()
        )


        val textView5 = onView(
            allOf(
                withText("Fecha Lanzamiento"),
                withParent(withParent(withId(R.id.nav_host_fragment))),
                isDisplayed()
            )
        )
        textView5.check(matches(withText("Fecha Lanzamiento")))

        val textView6 = onView(
            allOf(
                withText("Sello Discográfico"),
                withParent(withParent(withId(R.id.nav_host_fragment))),
                isDisplayed()
            )
        )
        textView6.check(matches(withText("Sello Discográfico")))




        val textView7 = onView(
            allOf(
                withId(R.id.labelGenero),
                withParent(withParent(withId(R.id.nav_host_fragment))),
                isDisplayed()
            )
        )
        textView7.check(matches(not(withText(""))))

        val appCompatSpinner2 = onView(
            allOf(
                withId(R.id.genre_spinner),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nav_host_fragment),
                        0
                    ),
                    9
                )
            )
        )
        appCompatSpinner2.perform(scrollTo(), click())


        val textView8 = onView(
            allOf(
                withId(R.id.labelDescripcion), withText("Descripción"),
                withParent(withParent(withId(R.id.nav_host_fragment))),
                isDisplayed()
            )
        )
        textView8.check(matches(withText("Descripción")))


        val appCompatEditText7 = onView(
            allOf(
                withId(R.id.descriptionTextbox),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nav_host_fragment),
                        0
                    ),
                    11
                )
            )
        )
        appCompatEditText7.perform(scrollTo(), replaceText("hola mundo"), closeSoftKeyboard())

        val button = onView(
            allOf(
                withId(R.id.add_album_button), withText("AGREGAR"),
                withParent(withParent(withId(R.id.nav_host_fragment))),
                isDisplayed()
            )
        )
        button.check(matches(isDisplayed()))

        val materialButton = onView(
            allOf(
                withId(R.id.add_album_button), withText("Agregar"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nav_host_fragment),
                        0
                    ),
                    12
                )
            )
        )
        materialButton.perform(click())

        val textView9 = onView(
            allOf(
                withId(R.id.albumName), withText("album de prueba"), withContentDescription("15"),
                withParent(withParent(IsInstanceOf.instanceOf(androidx.cardview.widget.CardView::class.java))),
                isDisplayed()
            )
        )
        textView9.check(matches(withText("album de prueba")))
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
