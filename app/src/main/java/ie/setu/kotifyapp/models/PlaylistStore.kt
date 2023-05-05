package ie.setu.kotifyapp.models



interface PlaylistStore {
    fun findAll(): List<PlaylistModel>
    fun create(playlist: PlaylistModel)
    fun update(playlist: PlaylistModel)
    fun delete(playlist: PlaylistModel)
    fun findById(id:Long) : PlaylistModel?


}
