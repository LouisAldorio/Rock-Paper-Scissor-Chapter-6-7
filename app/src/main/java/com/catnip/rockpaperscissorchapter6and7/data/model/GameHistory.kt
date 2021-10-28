package com.catnip.rockpaperscissorchapter6and7.data.model

import android.os.Parcelable
import androidx.room.*
import kotlinx.parcelize.Parcelize

@Entity(tableName = "GameHistory")
@Parcelize
data class GameHistory(
    @PrimaryKey(autoGenerate = true) var id: Long?,
    @ColumnInfo(name = "player1_id") var player1Id: Long?,
    @ColumnInfo(name = "player1_hero") var player1Hero: Int?,
    @ColumnInfo(name = "player2_id") var player2Id: Long?,
    @ColumnInfo(name = "player2_hero") var player2Hero: Int?,
    @ColumnInfo(name = "date") var date: String?,
) : Parcelable
