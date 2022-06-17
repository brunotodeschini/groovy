package petros.efthymiou.groovy.playlist

import junit.framework.TestCase.assertEquals
import org.junit.Test
import petros.efthymiou.groovy.R
import petros.efthymiou.groovy.utils.BasicUnitTest

class PlaylistMapperShould: BasicUnitTest() {

    private val playlistRaw = PlaylistRaw("01", "Name", "Category")
    private val playlistRawRock = PlaylistRaw("01", "Name", "rock")

    private val mapper: PlaylistMapper = PlaylistMapper()

    private val playlists = mapper.invoke(listOf(playlistRaw))
    private val playlist = playlists[0]

    private val playlistsRock = mapper.invoke(listOf(playlistRawRock))
    private val playlistRock = playlistsRock[0]

    @Test
    fun keepSameId() {
        assertEquals(playlistRaw.id, playlist.id)
    }

    @Test
    fun keepSameName() {
        assertEquals(playlistRaw.name, playlist.name)
    }

    @Test
    fun keepSameCategory() {
        assertEquals(playlistRaw.category, playlist.category)
    }

    @Test
    fun mapDefaultImageWhenNotRock() {
        assertEquals(R.mipmap.playlist, playlist.image)
    }

    @Test
    fun mapRockImageWhenRockCategory() {
        assertEquals(R.mipmap.rock, playlistRock.image)
    }
}