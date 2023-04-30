package ie.setu.kotifyapp.models

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize

data class PlaylistModel(var id: Long = 0,
                         var title: String = "",
                         var song: String = "",
                         var favArtist: String = "",
                         var image: Uri = Uri.EMPTY,
                         var genre: String = "",
                         var lat: Double = 0.0,
                         var lng: Double = 0.0,
                         var zoom: Float = 0f

                        // var numbPick: Int
                                            ) : Parcelable
@Parcelize
data class Location(var lat: Double = 0.0,
                    var lng: Double = 0.0,
                    var zoom: Float = 0f) : Parcelable


