package ie.setu.kotifyapp.models

import android.content.Context
import android.net.Uri
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import ie.setu.kotifyapp.helpers.*
import timber.log.Timber
import java.lang.reflect.Type
import java.util.*

const val JSON_FILE = "playlists.json"
val gsonBuilder: Gson = GsonBuilder().setPrettyPrinting()
    .registerTypeAdapter(Uri::class.java, UriParser())
    .create()
val listType: Type = object : TypeToken<ArrayList<PlaylistModel>>() {}.type

fun generateRandomId(): Long {
    return Random().nextLong()
}

class PlaylistJSONStore(private val context: Context) : PlaylistStore {

    var playlists = mutableListOf<PlaylistModel>()

    init {
        if (exists(context, JSON_FILE)) {
            deserialize()
        }
    }

    override fun findAll(): MutableList<PlaylistModel> {
        logAll()
        return playlists
    }

    override fun create(playlist: PlaylistModel) {
        playlist.id = generateRandomId()
        playlists.add(playlist)
        serialize()
    }


    override fun update(playlist: PlaylistModel) {
        var foundPlaylist: PlaylistModel? = playlists.find { p -> p.id == playlist.id }
        if (foundPlaylist != null) {
            foundPlaylist.title = playlist.title
            foundPlaylist.song = playlist.song
            foundPlaylist.favArtist = playlist.favArtist
            foundPlaylist.image = playlist.image
            foundPlaylist.genres = playlist.genres
            foundPlaylist.lat = playlist.lat
            foundPlaylist.lng = playlist.lng
            foundPlaylist.zoom = playlist.zoom
            logAll()
        }
    }

    override fun delete(playlist: PlaylistModel) {
        playlists.remove(playlist)
        serialize()
    }



    private fun serialize() {
        val jsonString = gsonBuilder.toJson(playlists, listType)
        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        playlists = gsonBuilder.fromJson(jsonString, listType)
    }

    private fun logAll() {
        playlists.forEach { Timber.i("$it") }
    }

    override fun findById(id:Long) : PlaylistModel? {
        val foundPlaylist: PlaylistModel? = playlists.find { it.id == id }
        return foundPlaylist
    }
}

class UriParser : JsonDeserializer<Uri>,JsonSerializer<Uri> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Uri {
        return Uri.parse(json?.asString)
    }

    override fun serialize(
        src: Uri?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive(src.toString())
    }
}
