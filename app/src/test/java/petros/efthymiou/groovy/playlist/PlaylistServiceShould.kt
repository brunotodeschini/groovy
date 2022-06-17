package petros.efthymiou.groovy.playlist

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import petros.efthymiou.groovy.utils.BasicUnitTest
import java.lang.RuntimeException

class PlaylistServiceShould: BasicUnitTest() {

    private lateinit var service: PlaylistService
    private val api: PlaylistAPI = mock()
    private val playlists: List<PlaylistRaw> = mock()


    @Test
    fun fetchPlaylistsFromAPI() = runBlockingTest {
        service = PlaylistService(api)

        service.fetchPlaylists().first()

        verify(api, times(1)).fetchAllPlaylists()

    }


    @Test
    fun convertValuesToFlowResultAndEmitThen() = runBlockingTest {
        mockSuccessfulCase()

        assertEquals(Result.success(playlists), service.fetchPlaylists().first())

    }


    @Test
    fun emitsErrorResultWhenNetworkFails() = runBlockingTest {
        mockFailureCase()

        assertEquals(
            "Something went wrong",
            service.fetchPlaylists().first().exceptionOrNull()?.message
        )
    }

    private fun mockFailureCase() = runBlockingTest {
        whenever(api.fetchAllPlaylists())
            .thenThrow(
                RuntimeException("Dam backend developers")
            )

        service = PlaylistService(api)
    }

    private fun mockSuccessfulCase() = runBlockingTest {
        whenever(api.fetchAllPlaylists()).thenReturn(playlists)

        service = PlaylistService(api)
    }

}