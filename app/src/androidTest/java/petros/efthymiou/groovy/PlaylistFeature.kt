package petros.efthymiou.groovy


import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import com.schibsted.spain.barista.assertion.BaristaRecyclerViewAssertions.assertRecyclerViewItemCount
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertNotDisplayed
import com.schibsted.spain.barista.internal.matcher.DrawableMatcher.Companion.withDrawable
import org.hamcrest.CoreMatchers.allOf
import org.junit.Rule
import org.junit.Test

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

class PlaylistFeature: BaseUITest() {

    @Test
    fun displayTitleScreen() {
        assertDisplayed("Playlists")
    }

    @Test
    fun displayListOfPlaylists() {

        Thread.sleep(4000)
        assertRecyclerViewItemCount(R.id.playlist_list, 10)

        onView(
            allOf(
                withId(R.id.playlist_name), isDescendantOfA(nthChildOf(withId(R.id.playlist_list), 0))
            )
        )
            .check(matches(withText("Hard Rock Cafe")))
            .check(matches(isDisplayed()))

        onView(
            allOf(
                withId(R.id.playlist_category), isDescendantOfA(nthChildOf(withId(R.id.playlist_list), 0))
            )
        )
            .check(matches(withText("rock")))
            .check(matches(isDisplayed()))

        onView(
            allOf(
                withId(R.id.playlist_image), isDescendantOfA(nthChildOf(withId(R.id.playlist_list), 1))
            )
        )
            .check(matches(withDrawable(R.mipmap.playlist)))
            .check(matches(isDisplayed()))

    }

    @Test
    fun displaysRockImageForRockListItems() {
        Thread.sleep(4000)
        onView(
            allOf(
                withId(R.id.playlist_image), isDescendantOfA(nthChildOf(withId(R.id.playlist_list), 0))
            )
        )
            .check(matches(withDrawable(R.mipmap.rock)))
            .check(matches(isDisplayed()))

        onView(
            allOf(
                withId(R.id.playlist_image), isDescendantOfA(nthChildOf(withId(R.id.playlist_list), 3))
            )
        )
            .check(matches(withDrawable(R.mipmap.rock)))
            .check(matches(isDisplayed()))
    }

    @Test
    fun displaysLoaderWhileFetchingThePlaylists() {
        assertDisplayed(R.id.loader)
    }

    @Test
    fun hideLoader() {
        Thread.sleep(4000)
        assertNotDisplayed(R.id.loader)
    }

    @Test
    fun navigateToDetailsScreen() {
        Thread.sleep(4000)
        onView(
            allOf(
                withId(R.id.playlist_image), isDescendantOfA(nthChildOf(withId(R.id.playlist_list), 0))
            )
        )
            .perform(click())

        assertDisplayed(R.id.playlist_details_root)
    }
}