package com.catnip.rockpaperscissorchapter6and7.data.model

import androidx.room.Embedded
import androidx.room.Relation

data class PlayerWithGameHistory(
    @Embedded val gameHistory: GameHistory,
    @Relation(
        parentColumn = "player1_id",
        entityColumn = "id"
    )
    val player1: Player,
    @Relation(
        parentColumn = "player2_id",
        entityColumn = "id"
    )
    val player2: Player
)
