package ie.setu.kotifyapp.main

import android.app.Application
import ie.setu.kotifyapp.models.PlaylistModel
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

    val playlists = ArrayList<PlaylistModel>()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("Playlist started")

    }
}
