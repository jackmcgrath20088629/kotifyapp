package ie.setu.kotifyapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.NumberPicker
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar
import ie.setu.kotifyapp.R
import ie.setu.kotifyapp.databinding.ActivityPlaylistBinding
import ie.setu.kotifyapp.main.MainApp
import ie.setu.kotifyapp.models.PlaylistModel
import timber.log.Timber
import timber.log.Timber.i
import com.squareup.picasso.Picasso
import ie.setu.kotifyapp.helpers.showImagePicker


class PlaylistActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlaylistBinding
    var playlist = PlaylistModel()
   lateinit var app : MainApp
    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>
    val IMAGE_REQUEST = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var edit = false
        binding = ActivityPlaylistBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.addToolbar.title = title
        setSupportActionBar(binding.addToolbar)
        app = application as MainApp

        i("Playlist Activity started...")

        if (intent.hasExtra("playlist_edit")) {
            edit = true
            playlist = intent.extras?.getParcelable("playlist_edit")!!
            binding.playlistTitle.setText(playlist.title)
            binding.song.setText(playlist.song)
            binding.btnAdd.setText(R.string.save_playlist)
            Picasso.get()
                .load(playlist.image)
                .into(binding.playlistImage)
        }

        binding.btnAdd.setOnClickListener() {
            playlist.title = binding.playlistTitle.text.toString()
            playlist.song = binding.song.text.toString()
            if (playlist.title.isEmpty()) {
                Snackbar.make(it,R.string.enter_playlist_title, Snackbar.LENGTH_LONG)
                    .show()
            } else {
                if (edit) {
                    app.playlists.update(playlist.copy())
                } else {
                    app.playlists.create(playlist.copy())
                }
            }
            i("add Button Pressed: $playlist")
            setResult(RESULT_OK)
            finish()
        }
        binding.chooseImage.setOnClickListener {
            showImagePicker(imageIntentLauncher)
        }

        registerImagePickerCallback()
        numberPicker()
        playlistGenrePicker()

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
    private fun registerImagePickerCallback() {
        imageIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when(result.resultCode){
                    RESULT_OK -> {
                        if (result.data != null) {
                            i("Got Result ${result.data!!.data}")
                            playlist.image = result.data!!.data!!
                            Picasso.get()
                                .load(playlist.image)
                                .into(binding.playlistImage)
                        }
                    }
                    RESULT_CANCELED -> { } else -> { }
                }
            }
    }
    private fun numberPicker() {
        val numberPicker = binding.songnumberPicker
        numberPicker.minValue = 0
        numberPicker.maxValue = 100
        numberPicker.wrapSelectorWheel = true
    }

    // Reference: https://tutorialwing.com/android-spinner-using-kotlin-with-example/
    private fun playlistGenrePicker() {
        val genreNames = arrayOf("Rock", "Rap", "Jazz", "EDM", "Pop", "Country", "Other")
        val spinner = binding.genrePicker
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, genreNames)
        spinner.adapter = arrayAdapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Code to perform some action when nothing is selected
            }
        }
    }


}
