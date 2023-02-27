package ie.setu.kotifyapp.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PlaylistModel(var id: Long = 0, var title: String = "", var song: String = "") : Parcelable

