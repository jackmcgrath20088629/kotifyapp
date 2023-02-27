package ie.setu.kotifyapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.snackbar.Snackbar
import ie.setu.kotifyapp.R
import ie.setu.kotifyapp.databinding.ActivityPlaylistBinding
import ie.setu.kotifyapp.main.MainApp
import ie.setu.kotifyapp.models.PlaylistModel
import timber.log.Timber
import timber.log.Timber.i

class PlaylistActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlaylistBinding
    var playlist = PlaylistModel()
   lateinit var app : MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPlaylistBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.addToolbar.title = title
        setSupportActionBar(binding.addToolbar)
        app = application as MainApp

        if (intent.hasExtra("playlist_edit")) {
            playlist = intent.extras?.getParcelable("playlist_edit")!!
            binding.playlistTitle.setText(playlist.title)
            binding.song.setText(playlist.song)
        }

        binding.btnAdd.setOnClickListener() {
            playlist.title = binding.playlistTitle.text.toString()
            playlist.song = binding.song.text.toString()
            if (playlist.title.isNotEmpty()) {
                app.playlists.create(playlist.copy())
                setResult(RESULT_OK)
                finish()
            }


            else {
                Snackbar
                    .make(it, "A playlist title must be entered", Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_playlist, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_cancel -> { finish() }
        }
        return super.onOptionsItemSelected(item)
    }
}
