package petros.efthymiou.groovy.playlist

import petros.efthymiou.groovy.details.PlaylistDetails
import retrofit2.http.GET
import retrofit2.http.Path

interface PlaylistAPI {

    @GET("playlists")
    suspend fun fetchAllPlaylists(): List<PlaylistRaw>

}
