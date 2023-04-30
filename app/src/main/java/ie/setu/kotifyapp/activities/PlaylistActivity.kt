package ie.setu.kotifyapp.activities

import android.content.Intent
import android.net.Uri
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
import ie.setu.kotifyapp.models.Location


class PlaylistActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlaylistBinding
    var playlist = PlaylistModel()
    lateinit var app : MainApp
    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>
    private lateinit var mapIntentLauncher : ActivityResultLauncher<Intent>
    //var location = Location(52.245696, -7.139102, 15f)
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
            binding.favArtist.setText(playlist.favArtist)
            binding.btnAdd.setText(R.string.save_playlist)
            Picasso.get()
                .load(playlist.image)
                .into(binding.playlistImage)
            if (playlist.image != Uri.EMPTY) {
                binding.chooseImage.setText(R.string.change_playlist_image)
            }
        }

        binding.btnAdd.setOnClickListener() {
            playlist.title = binding.playlistTitle.text.toString()
            playlist.song = binding.song.text.toString()
            playlist.favArtist = binding.favArtist.text.toString()
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
        binding.playlistLocation.setOnClickListener {
            val location = Location(52.245696, -7.139102, 15f)
            if (playlist.zoom != 0f) {
                location.lat =  playlist.lat
                location.lng = playlist.lng
                location.zoom = playlist.zoom
            }
            val launcherIntent = Intent(this, MapActivity::class.java)
                .putExtra("location", location)
            mapIntentLauncher.launch(launcherIntent)
        }



        registerImagePickerCallback()
        registerMapCallback()
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

    private fun registerMapCallback() {
        mapIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when (result.resultCode) {
                    RESULT_OK -> {
                        if (result.data != null) {
                            i("Got Location ${result.data.toString()}")
                            val location = result.data!!.extras?.getParcelable<Location>("location")!!
                            i("Location == $location")
                            playlist.lat = location.lat
                            playlist.lng = location.lng
                            playlist.zoom = location.zoom
                        } // end of if
                    }
                    RESULT_CANCELED -> { } else -> { }
                }
            }
    }
    private fun numberPicker() {
        val numberPicker = binding.numbPick
        numberPicker.minValue = 0
        numberPicker.maxValue = 100
        numberPicker.wrapSelectorWheel = true
    }

    // Reference: https://tutorialwing.com/android-spinner-using-kotlin-with-example/
    private fun playlistGenrePicker() {
        val genreNames = arrayOf("Rock", "Rap", "Jazz", "EDM", "Pop", "Country", "Other")
        val spinner = binding.genre
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
