package petros.efthymiou.groovy.playlist

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


class PlaylistViewModelShould : BasicUnitTest() {

    private val repository: PlaylistRepository = mock()
    private val playlists = mock<List<Playlist>>()
    private val expected = Result.success(playlists)
    private val exception = RuntimeException("Something went wrong")

    @Test
    fun getPlaylistFromRepository() = runBlocking {
        val viewModel = mockSuccessfulCase()
        viewModel.playlists.getValueForTest()

        verify(repository, times(1)).getPlaylists()
    }

    @Test
    fun emitsPlaylistFromRepository() = runBlockingTest {
        val viewModel = mockSuccessfulCase()

        assertEquals(expected, viewModel.playlists.getValueForTest())
    }

    @Test
    fun emitsErrorWhenReceiveErrorFromRepository() = runBlockingTest {
        val viewModel = mockFailureCase()

        assertEquals(exception, viewModel.playlists.getValueForTest()?.exceptionOrNull())

    }

    @Test
    fun showSpinnerWhileLoading() = runBlockingTest {
        val viewModel = mockSuccessfulCase()

        viewModel.loader.captureValues {
            viewModel.playlists.getValueForTest()

            assertEquals(true, values[0])
        }

    }

    @Test
    fun closeLoaderAfterPlaylistsLoad() = runBlockingTest {
        val viewModel = mockSuccessfulCase()

        viewModel.loader.captureValues {
            viewModel.playlists.getValueForTest()

            assertEquals(false, values.last())
        }
    }

    @Test
    fun closeLoaderAfterError() = runBlockingTest {
        val viewModel = mockFailureCase()

        viewModel.loader.captureValues {
            viewModel.playlists.getValueForTest()

            assertEquals(false, values.last())
        }
    }

    private suspend fun mockFailureCase(): PlaylistViewModel {
        whenever(repository.getPlaylists()).thenReturn(
            flow {
                emit(Result.failure<List<Playlist>>(exception))
            }
        )

        return PlaylistViewModel(repository)
    }

    private suspend fun mockSuccessfulCase(): PlaylistViewModel {
        whenever(repository.getPlaylists()).thenReturn(
            flow {
                emit(expected)
            }
        )
        return PlaylistViewModel(repository)
    }
}