package com.astery.wildhackvolunteers.localStorage.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.astery.wildhack.model.Answer
import com.astery.wildhackvolunteers.model.AnswerAndTag
import com.astery.wildhackvolunteers.model.AnswerTag
import com.astery.wildhackvolunteers.localStorage.database.dao.ArticleDao

@Database(
    entities = [Answer::class, AnswerAndTag::class, AnswerTag::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "database"
                )
                    .build()
                INSTANCE = instance

                instance
            }
        }
    }
}