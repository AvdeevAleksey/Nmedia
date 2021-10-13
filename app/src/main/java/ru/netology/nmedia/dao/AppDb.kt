package ru.netology.nmedia.dao

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import ru.netology.nmedia.dao.PostDaoImpl.Companion
import ru.netology.nmedia.dao.PostDaoImpl.Companion.DATABASE_NAME
import ru.netology.nmedia.dao.PostDaoImpl.Companion.DATABASE_VERSION
import ru.netology.nmedia.db.DbHelper
import java.sql.Array

class AppDb private constructor(db: SQLiteDatabase) {
    val postDao: PostDao = PostDaoImpl(db)

    companion object {
        @Volatile
        private var instance: AppDb? =null

        fun getInstance(context: Context): AppDb {
            return instance ?: synchronized(this) {
                instance ?: AppDb(
                    buildDatabase()
                )
            }
        }

        private fun buildDatabase(context: Context, DDLs: Array<String>) = DbHelper(
            context,
            DATABASE_NAME,
            DATABASE_VERSION,
            DDLs
        ).writableDatabase
    }
}