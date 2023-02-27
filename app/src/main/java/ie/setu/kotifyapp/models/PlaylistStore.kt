package ie.setu.kotifyapp.models

import ie.setu.kotifyapp.models.PlaylistModel

interface PlaylistStore {
    fun findAll(): List<PlaylistModel>
    fun create(playlist: PlaylistModel)
    fun update(placemark: PlaylistModel)
}
