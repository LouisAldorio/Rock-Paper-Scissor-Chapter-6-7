package com.catnip.rockpaperscissorchapter6and7.enumeration

enum class GameResult(val stringValue: String) {
    WIN("WIN"),
    LOSE("LOSE"),
    DRAW("DRAW");

    companion object{
        fun get(value: GameResult) = values().first(){it == value}
    }
}