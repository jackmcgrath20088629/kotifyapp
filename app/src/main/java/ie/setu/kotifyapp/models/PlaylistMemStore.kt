package ie.setu.kotifyapp.models

import timber.log.Timber.i

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}
class PlaylistMemStore : PlaylistStore {

    val playlists = ArrayList<PlaylistModel>()

    override fun findAll(): List<PlaylistModel> {
        return playlists
    }

    override fun create(playlist: PlaylistModel) {
        playlists.add(playlist)
        playlist.id = getId()
        logAll()
    }

    override fun update(playlist: PlaylistModel) {
        var foundPlaylist: PlaylistModel? = playlists.find { p -> p.id == playlist.id }
        if (foundPlaylist != null) {
            foundPlaylist.title = playlist.title
            foundPlaylist.song = playlist.song
            foundPlaylist.favArtist = playlist.favArtist
            foundPlaylist.image = playlist.image
            foundPlaylist.genre = playlist.genre
            logAll()
        }
    }

    private fun logAll() {
        playlists.forEach{ i("${it}") }
    }
}
