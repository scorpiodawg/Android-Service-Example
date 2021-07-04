package space.ravisu.example.common

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class QueryResult(
    val result: Array<String>
) : Parcelable
