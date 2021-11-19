package com.example.vinilos


import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import android.view.View
import android.view.ViewGroup

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*

import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

import org.hamcrest.Matchers.allOf

@LargeTest
@RunWith(AndroidJUnit4::class)
class CollectorListTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun collectorList() {
        val bottomNavigationItemView = onView(
            allOf(
                withId(R.id.navigation_coleccionistas), withContentDescription("Coleccionistas"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.navigationView),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        bottomNavigationItemView.perform(click())

        // Debe contener una lista de coleccionistas con dos elementos
        val collectorRv = onView(
            allOf(
                withId(R.id.collectorsRv),
                isDisplayed()
            )
        )
        collectorRv.check(matches(hasChildCount(2)))

        // Debe mostrar nombre de coleccionista Manolo Bellon
        val textView = onView(
            allOf(
                withId(R.id.collectorName),
                withText("Manolo Bellon"),
                withContentDescription("100"),
                withParent(withParent(IsInstanceOf.instanceOf(android.view.ViewGroup::class.java))),
                isDisplayed()
            )
        )
        textView.check(matches(withText("Manolo Bellon")))

        // Debe mostrar correo del coleccionista Manolo Bellon
        val textView2 = onView(
            allOf(
                withId(R.id.collectorEmail),
                withText("manollo@caracol.com.co"),
                withContentDescription("100"),
                withParent(withParent(IsInstanceOf.instanceOf(android.view.ViewGroup::class.java))),
                isDisplayed()
            )
        )
        textView2.check(matches(withText("manollo@caracol.com.co")))

        // Debe mostrar el nombre del coleccionista Jaime Monsalve
        val textView3 = onView(
            allOf(
                withId(R.id.collectorName),
                withText("Jaime Monsalve"),
                withContentDescription("101"),
                withParent(withParent(IsInstanceOf.instanceOf(android.view.ViewGroup::class.java))),
                isDisplayed()
            )
        )
        textView3.check(matches(withText("Jaime Monsalve")))

        // Debe mostrar el correo del coleccionista Jaime Monsalve
        val textView4 = onView(
            allOf(
                withId(R.id.collectorEmail),
                withText("jmonsalve@rtvc.com.co"),
                withContentDescription("101"),
                withParent(withParent(IsInstanceOf.instanceOf(android.view.ViewGroup::class.java))),
                isDisplayed()
            )
        )
        textView4.check(matches(withText("jmonsalve@rtvc.com.co")))
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
