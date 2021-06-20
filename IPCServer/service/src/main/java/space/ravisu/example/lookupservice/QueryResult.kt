package space.ravisu.example.lookupservice

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class QueryResult(
    val result: Array<String>
) : Parcelable
