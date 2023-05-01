package ie.setu.kotifyapp.main

import android.app.Application
import ie.setu.kotifyapp.models.PlaylistJSONStore
import ie.setu.kotifyapp.models.PlaylistMemStore
import ie.setu.kotifyapp.models.PlaylistModel
import ie.setu.kotifyapp.models.PlaylistStore
import timber.log.Timber
import timber.log.Timber.i
class MainApp : Application() {

    lateinit var playlists: PlaylistStore

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        // playlists = PlaylistMemStore()
        playlists = PlaylistJSONStore(applicationContext)
        i("Playlist started")
    }
}