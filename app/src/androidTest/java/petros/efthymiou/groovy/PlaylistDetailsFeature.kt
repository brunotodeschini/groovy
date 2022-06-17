package petros.efthymiou.groovy

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertNotDisplayed
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertNotExist
import org.hamcrest.CoreMatchers.allOf
import org.junit.Test

class PlaylistDetailsFeature: BaseUITest() {

    @Test
    fun displaysPlaylistNameAndDetails() {
        navigateToPlaylistDetails(0)
        Thread.sleep(4000)
        assertDisplayed(R.id.playlist_details_root)

        assertDisplayed("Hard Rock Cafe")
        assertDisplayed("Rock your senses with this timeless signature vibe list. \n\n • Poison \n • You shook me all night \n • Zombie \n • Rock'n Me \n • Thunderstruck \n • I Hate Myself for Loving you \n • Crazy \n • Knockin' on Heavens Door")
    }

    @Test
    fun displaysLoaderWhileFetchingPlaylistDetails() {
        navigateToPlaylistDetails(0)
        assertDisplayed(R.id.loader_details)
    }

    @Test
    fun hideLoader() {
        navigateToPlaylistDetails(0)
        Thread.sleep(4000)
        assertNotDisplayed(R.id.loader_details)
    }

    @Test
    fun displaysErrorMessageWhenNetworkFails() {
        navigateToPlaylistDetails(1)
        assertDisplayed(R.string.generic_error_message)
    }

    @Test
    fun hideErrorMessage() {
        navigateToPlaylistDetails(1)
        Thread.sleep(7000)
        assertNotExist(R.string.generic_error_message)
    }

    private fun navigateToPlaylistDetails(rowNumber: Int) {
        Thread.sleep(4000)

        onView(
            allOf(
                withId(R.id.playlist_image),
                isDescendantOfA(nthChildOf(withId(R.id.playlist_list),
                rowNumber))
            )
        ).perform(ViewActions.click())
    }
}