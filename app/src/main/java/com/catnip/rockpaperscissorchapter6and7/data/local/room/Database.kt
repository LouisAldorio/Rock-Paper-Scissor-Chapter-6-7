package com.catnip.rockpaperscissorchapter6and7.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.catnip.rockpaperscissorchapter6and7.data.local.room.dao.GameHistoryDao
import com.catnip.rockpaperscissorchapter6and7.data.local.room.dao.PlayersDao
import com.catnip.rockpaperscissorchapter6and7.data.model.GameHistory
import com.catnip.rockpaperscissorchapter6and7.data.model.Player

@Database(entities = [Player::class, GameHistory::class], version = 1)
abstract class PlayersDatabase : RoomDatabase() {
    abstract fun playersDao(): PlayersDao
    abstract fun gameHistoryDao(): GameHistoryDao

    companion object {
        private const val DB_NAME = "rock_paper_scissors_db"

        @Volatile
        private var INSTANCE: PlayersDatabase? = null
        fun getInstance(context: Context): PlayersDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PlayersDatabase::class.java,
                    DB_NAME
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}