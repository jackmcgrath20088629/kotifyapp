package ie.setu.kotifyapp.adapters
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ie.setu.kotifyapp.databinding.CardPlaylistBinding
import ie.setu.kotifyapp.models.PlaylistModel
interface PlaylistListener {
    fun onPlaylistClick(playlist: PlaylistModel)
}
class PlaylistAdapter constructor(private var playlists: List<PlaylistModel>,
                                  private val listener: PlaylistListener) :
    RecyclerView.Adapter<PlaylistAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardPlaylistBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val playlist = playlists[holder.adapterPosition]
        holder.bind(playlist, listener)
    }

    override fun getItemCount(): Int = playlists.size

    class MainHolder(private val binding : CardPlaylistBinding) :
        RecyclerView.ViewHolder(binding.root) {





        fun bind(playlist: PlaylistModel, listener: PlaylistListener) {
            binding.playlistTitle.text = playlist.title
            binding.song.text = playlist.song
            binding.favArtist.text = playlist.favArtist
            Picasso.get().load(playlist.image).resize(200,200).into(binding.playlistImage)
            //binding.numbPick.int = playlist.numbPick.toString()
            binding.root.setOnClickListener { listener.onPlaylistClick(playlist) }
        }
    }
}
