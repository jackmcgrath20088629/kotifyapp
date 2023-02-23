package ie.setu.kotifyapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ie.setu.kotifyapp.databinding.CardPlaylistBinding
import ie.setu.kotifyapp.models.PlaylistModel

class PlaylistAdapter constructor(private var playlists: List<PlaylistModel>) :
    RecyclerView.Adapter<PlaylistAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardPlaylistBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val playlist = playlists[holder.adapterPosition]
        holder.bind(playlist)
    }

    override fun getItemCount(): Int = playlists.size

    class MainHolder(private val binding : CardPlaylistBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(playlist: PlaylistModel) {
            binding.playlistTitle.text = playlist.title
            binding.song.text = playlist.song
        }
    }
}
