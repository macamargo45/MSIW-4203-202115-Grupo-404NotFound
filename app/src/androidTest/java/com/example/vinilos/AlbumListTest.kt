package com.example.vinilos


import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.hamcrest.Matchers.allOf
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.espresso.matcher.ViewMatchers
import org.hamcrest.Matchers
import java.util.regex.Matcher


@LargeTest
@RunWith(AndroidJUnit4::class)
class AlbumListTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun albumListTest() {
        Thread.sleep(1500)
        val textView = onView(
            allOf(
                withId(R.id.albumName), withText("Buscando América"),
                withParent(withParent(IsInstanceOf.instanceOf(android.view.ViewGroup::class.java))),
                isDisplayed()
            )
        )
        textView.check(matches(withText("Buscando América")))

        val tag = "100"
        val textView2 = onView(
            allOf(
                withId(R.id.albumPerformerNames),
                withText("Rubén Blades Bellido de Luna"),
                withParent(withParent(IsInstanceOf.instanceOf(android.view.ViewGroup::class.java))),
                withParentIndex(0),
                isDisplayed()
            )
        )


        textView2.check(matches(withText("Rubén Blades Bellido de Luna")))


        val textView3 = onView(
            allOf(
                withId(R.id.albumName), withText("Poeta del pueblo"),
                withParent(withParent(IsInstanceOf.instanceOf(android.view.ViewGroup::class.java))),
                isDisplayed()
            )
        )
        textView3.check(matches(withText("Poeta del pueblo")))

        /*val textView4 = onView(
            allOf(
                withId(R.id.albumPerformerNames), withText("Rubén Blades Bellido de Luna"),
                withParent(withParent(IsInstanceOf.instanceOf(android.view.ViewGroup::class.java))),
                isDisplayed()
            )
        )
        textView4.check(matches(withText("Rubén Blades Bellido de Luna")))

         */

        val textView5 = onView(
            allOf(
                withId(R.id.albumName), withText("A Night at the Opera"),
                withParent(withParent(IsInstanceOf.instanceOf(android.view.ViewGroup::class.java))),
                isDisplayed()
            )
        )
        textView5.check(matches(withText("A Night at the Opera")))

       /* val textView6 = onView(
            allOf(
                withId(R.id.albumPerformerNames), withText("Queen"),
                withParent(withParent(IsInstanceOf.instanceOf(android.view.ViewGroup::class.java))),
                isDisplayed()
            )
        )
        textView6.check(matches(withText("Queen")))

        */

        val textView7 = onView(
            allOf(
                withId(R.id.albumName), withText("A Day at the Races"),
                withParent(withParent(IsInstanceOf.instanceOf(android.view.ViewGroup::class.java))),
                isDisplayed()
            )
        )
        textView7.check(matches(withText("A Day at the Races")))

        /*val textView8 = onView(
            allOf(
                withId(R.id.albumPerformerNames), withText("Queen"),
                withParent(withParent(IsInstanceOf.instanceOf(android.view.ViewGroup::class.java))),
                isDisplayed()
            )
        )
        textView8.check(matches(withText("Queen")))*/
    }
}
