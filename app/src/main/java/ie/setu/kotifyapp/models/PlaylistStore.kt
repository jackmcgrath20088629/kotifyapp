package ie.setu.kotifyapp.models



interface PlaylistStore {
    fun findAll(): List<PlaylistModel>
    fun create(playlist: PlaylistModel)
    fun update(playlist: PlaylistModel)



}
