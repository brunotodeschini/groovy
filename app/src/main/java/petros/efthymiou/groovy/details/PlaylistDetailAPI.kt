package petros.efthymiou.groovy.details

import retrofit2.http.GET
import retrofit2.http.Path

interface PlaylistDetailAPI {

    @GET("playlist-details/{id}")
    suspend fun fetchPlaylistDetail(@Path("id") id: String): PlaylistDetails
}
