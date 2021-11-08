package com.example.vinilos


import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest2 {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun mainActivityTest2() {
        val recyclerView = onView(
            allOf(
                withId(R.id.albumsRv),
                childAtPosition(
                    withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                    1
                )
            )
        )
        recyclerView.perform(actionOnItemAtPosition<ViewHolder>(0, click()))

        val textView = onView(
            allOf(
                withText("Detalle Album"),
                withParent(
                    allOf(
                        withId(R.id.action_bar),
                        withParent(withId(R.id.action_bar_container))
                    )
                ),
                isDisplayed()
            )
        )
        textView.check(matches(withText("Detalle Album")))

        val imageView = onView(
            allOf(
                withId(R.id.imageViewAlbumDetails), withContentDescription("Portada del album"),
                withParent(withParent(IsInstanceOf.instanceOf(androidx.cardview.widget.CardView::class.java))),
                isDisplayed()
            )
        )
        imageView.check(matches(isDisplayed()))

        val textView2 = onView(
            allOf(
                withId(R.id.NameAlbumDetails), withText("Buscando América"),
                withParent(withParent(IsInstanceOf.instanceOf(android.view.ViewGroup::class.java))),
                isDisplayed()
            )
        )
        textView2.check(matches(withText("Buscando América")))

        val textView3 = onView(
            allOf(
                withId(R.id.PerformerNamesAlbumDetails), withText("Rubén Blades Bellido de Luna"),
                withParent(withParent(IsInstanceOf.instanceOf(android.view.ViewGroup::class.java))),
                isDisplayed()
            )
        )
        textView3.check(matches(withText("Rubén Blades Bellido de Luna")))

        val textView4 = onView(
            allOf(
                withId(R.id.RecordLabelLabelAlbumDetails), withText("Compañia discográfica"),
                withParent(withParent(IsInstanceOf.instanceOf(android.widget.TableLayout::class.java))),
                isDisplayed()
            )
        )
        textView4.check(matches(withText("Compañia discográfica")))

        val textView5 = onView(
            allOf(
                withId(R.id.RecordLabelAlbumDetails), withText("Elektra"),
                withParent(withParent(IsInstanceOf.instanceOf(android.widget.TableLayout::class.java))),
                isDisplayed()
            )
        )
        textView5.check(matches(withText("Elektra")))

        val textView6 = onView(
            allOf(
                withId(R.id.GenreLabelAlbumDetails), withText("Género"),
                withParent(withParent(IsInstanceOf.instanceOf(android.widget.TableLayout::class.java))),
                isDisplayed()
            )
        )
        textView6.check(matches(withText("Género")))

        val textView7 = onView(
            allOf(
                withId(R.id.GenreAlbumDetails), withText("Salsa"),
                withParent(withParent(IsInstanceOf.instanceOf(android.widget.TableLayout::class.java))),
                isDisplayed()
            )
        )
        textView7.check(matches(withText("Salsa")))

        val textView8 = onView(
            allOf(
                withId(R.id.ReleaseDateLabelAlbumDetails), withText("Fecha Lanzamiento"),
                withParent(withParent(IsInstanceOf.instanceOf(android.widget.TableLayout::class.java))),
                isDisplayed()
            )
        )
        textView8.check(matches(withText("Fecha Lanzamiento")))

        val textView9 = onView(
            allOf(
                withId(R.id.ReleaseDateAlbumDetails), withText("01-08-1984"),
                withParent(withParent(IsInstanceOf.instanceOf(android.widget.TableLayout::class.java))),
                isDisplayed()
            )
        )
        textView9.check(matches(withText("01-08-1984")))

        val textView10 = onView(
            allOf(
                withId(R.id.ReleaseDateAlbumDetails), withText("01-08-1984"),
                withParent(withParent(IsInstanceOf.instanceOf(android.widget.TableLayout::class.java))),
                isDisplayed()
            )
        )
        textView10.check(matches(withText("01-08-1984")))

        val textView11 = onView(
            allOf(
                withId(R.id.ReleaseDateAlbumDetails), withText("01-08-1984"),
                withParent(withParent(IsInstanceOf.instanceOf(android.widget.TableLayout::class.java))),
                isDisplayed()
            )
        )
        textView11.check(matches(withText("01-08-1984")))

        val appCompatImageButton = onView(
            allOf(
                withContentDescription("Navigate up"),
                childAtPosition(
                    allOf(
                        withId(R.id.action_bar),
                        childAtPosition(
                            withId(R.id.action_bar_container),
                            0
                        )
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        appCompatImageButton.perform(click())

        val textView12 = onView(
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
        textView12.check(matches(withText("Albumes")))
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
