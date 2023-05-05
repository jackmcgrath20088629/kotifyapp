package ie.setu.kotifyapp.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.squareup.picasso.Picasso
import ie.setu.kotifyapp.databinding.ActivityPlaylistMapsBinding
import ie.setu.kotifyapp.databinding.ContentPlaylistMapsBinding
import ie.setu.kotifyapp.main.MainApp

class PlaylistMapsActivity : AppCompatActivity(),GoogleMap.OnMarkerClickListener {

    private lateinit var binding: ActivityPlaylistMapsBinding
    private lateinit var contentBinding: ContentPlaylistMapsBinding
    lateinit var map: GoogleMap
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPlaylistMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        contentBinding = ContentPlaylistMapsBinding.bind(binding.root)
        contentBinding.mapView.onCreate(savedInstanceState)
        app = application as MainApp
        contentBinding.mapView.getMapAsync {
            map = it
            configureMap()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        contentBinding.mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        contentBinding.mapView.onLowMemory()
    }

    override fun onPause() {
        super.onPause()
        contentBinding.mapView.onPause()
    }

    override fun onResume() {
        super.onResume()
        contentBinding.mapView.onResume()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        contentBinding.mapView.onSaveInstanceState(outState)
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        //val playlist = marker.tag as PlaylistModel
        val tag = marker.tag as Long
        val playlist = app.playlists.findById(tag)
        contentBinding.currentTitle.text = playlist!!.title
        contentBinding.currentSong.text = playlist.song
        Picasso.get().load(playlist.image).into(contentBinding.imageView2)
        return false
    }

    private fun configureMap() {
        map.uiSettings.isZoomControlsEnabled = true
        app.playlists.findAll().forEach {
            val loc = LatLng(it.lat, it.lng)
            val options = MarkerOptions().title(it.title).position(loc)
            map.addMarker(options)?.tag = it.id
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, it.zoom))
            map.setOnMarkerClickListener(this)
        }
    }
}

