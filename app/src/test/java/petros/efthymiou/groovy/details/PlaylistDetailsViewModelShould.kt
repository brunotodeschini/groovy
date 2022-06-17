package petros.efthymiou.groovy.details

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import petros.efthymiou.groovy.utils.BasicUnitTest
import petros.efthymiou.groovy.utils.captureValues
import petros.efthymiou.groovy.utils.getValueForTest
import java.lang.RuntimeException

class PlaylistDetailsViewModelShould: BasicUnitTest() {

    lateinit var viewModel: PlaylistDetailsViewModel
    private val service: PlaylistDetailService = mock()
    private val playlistDetail: PlaylistDetails = mock()
    private val expected = Result.success(playlistDetail)
    private val id = "1"
    private val exception = RuntimeException("Something went wrong")


    @Test
    fun getPlaylistDetailsFromService(): Unit = runBlocking {
        mockSuccessfulCase()

        viewModel.playlistDetails.getValueForTest()

        verify(service, times(1)).fetchPlaylistDetails(id)

    }

    @Test
    fun emitsPlaylistDetailFromService(): Unit = runBlocking {
        mockSuccessfulCase()

        assertEquals(expected, viewModel.playlistDetails.getValueForTest())
    }

    @Test
    fun emitsErrorWhenReceiveErrorFromService() = runBlockingTest {
        mockFailureCase()

        assertEquals(
            exception,
            viewModel.playlistDetails.getValueForTest()?.exceptionOrNull())
    }

    @Test
    fun showLoaderWhileLoadingDetails() = runBlockingTest {
        mockSuccessfulCase()

        viewModel.loaderDetails.captureValues{
            viewModel.playlistDetails.getValueForTest()

            assertEquals(true, values[0])
        }
    }

    @Test
    fun hideLoaderAfterPlaylistDetailsLoad() = runBlockingTest {
        mockSuccessfulCase()

        viewModel.loaderDetails.captureValues {
            viewModel.playlistDetails.getValueForTest()

            assertEquals(false, values.last())
        }
    }

    private suspend fun mockFailureCase() {
        whenever(service.fetchPlaylistDetails(id)).thenReturn(
            flow {
                emit(Result.failure<PlaylistDetails>(exception))
            }
        )

        viewModel = PlaylistDetailsViewModel(service)

        viewModel.getPlaylistDetails(id)
    }

    private suspend fun mockSuccessfulCase() {
        whenever(service.fetchPlaylistDetails(id)).thenReturn(
            flow {
                emit(expected)
            }
        )

        viewModel = PlaylistDetailsViewModel(service)

        viewModel.getPlaylistDetails(id)
    }

}