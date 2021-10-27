package com.catnip.rockpaperscissorchapter6and7.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "Players")
@Parcelize
data class Player(
    @PrimaryKey(autoGenerate = true) var id: Int?,
    @ColumnInfo(name = "name") var name: String,

) : Parcelable {

    @Ignore
    var choice : Int = -1
}
