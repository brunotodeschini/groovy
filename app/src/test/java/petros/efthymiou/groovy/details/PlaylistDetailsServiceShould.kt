package petros.efthymiou.groovy.details

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

class PlaylistDetailsServiceShould: BasicUnitTest() {

    lateinit var service: PlaylistDetailService
    private val playlistDetailAPI: PlaylistDetailAPI = mock()
    private val playlistDetails: PlaylistDetails = mock()
    private val id = "1"

    @Test
    fun fetchPlaylistDetailFromAPI() = runBlockingTest {
        service = PlaylistDetailService(playlistDetailAPI)

        service.fetchPlaylistDetails(id).first()

        verify(playlistDetailAPI, times(1)).fetchPlaylistDetail(id)

    }

    @Test
    fun convertValuesToFlowResultAndEmitThem() = runBlockingTest {
        mockSuccessfulCase()

        assertEquals(
            Result.success(playlistDetails),
            service.fetchPlaylistDetails(id).first()
        )
    }

    @Test
    fun emitErrorResultWhenNetworkFails() = runBlockingTest {
        mockFailureCase()

        assertEquals(
            "Something went wrong",
            service.fetchPlaylistDetails(id).first().exceptionOrNull()?.message
        )
    }

    private suspend fun mockFailureCase() {
        whenever(playlistDetailAPI.fetchPlaylistDetail(id))
            .thenThrow(
                RuntimeException("Something went wrong")
            )

        service = PlaylistDetailService(playlistDetailAPI)
    }

    private suspend fun mockSuccessfulCase() {
        whenever(playlistDetailAPI.fetchPlaylistDetail(id))
            .thenReturn(
                playlistDetails
            )

        service = PlaylistDetailService(playlistDetailAPI)
    }

}