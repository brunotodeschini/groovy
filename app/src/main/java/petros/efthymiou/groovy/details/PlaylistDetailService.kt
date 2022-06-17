package petros.efthymiou.groovy.details

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import java.lang.RuntimeException
import javax.inject.Inject

class PlaylistDetailService @Inject constructor(
    private val playlistDetailAPI: PlaylistDetailAPI
) {

    suspend fun fetchPlaylistDetails(id: String): Flow<Result<PlaylistDetails>> = flow {
       emit(Result.success(playlistDetailAPI.fetchPlaylistDetail(id)))
    }.catch {
        emit(Result.failure(RuntimeException("Something went wrong")))
    }
}