package com.catnip.rockpaperscissorchapter6and7.utils

import com.catnip.rockpaperscissorchapter6and7.enumeration.GameResult

object GameUtil {
    fun getGameResult(p1: Int, p2: Int): GameResult {
        return when {
            (p1 + 1) % 3 == p2 -> GameResult.LOSE
            p1 == p2 -> GameResult.DRAW
            else -> GameResult.WIN
        }
    }
}