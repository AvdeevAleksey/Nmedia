package ru.netology.nmedia.model.dao

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import ru.netology.nmedia.model.dto.Post
import java.util.*

class PostDaoImpl(
    private val db: SQLiteDatabase
) : PostDao {

    companion object {
        internal const val DATABASE_VERSION = 1
        internal const val DATABASE_NAME = "MyPostsSQLiteDatabase.db"
        val DDL = """
        CREATE TABLE ${PostColumns.TABLE} (
            ${PostColumns.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT,
            ${PostColumns.COLUMN_AUTHOR} TEXT NOT NULL,
            ${PostColumns.COLUMN_PUBLISHED} TEXT NOT NULL,
            ${PostColumns.COLUMN_CONTENT} TEXT NOT NULL,
            ${PostColumns.COLUMN_VIDEO_IN_POST} TEXT NOT NULL,
            ${PostColumns.COLUMN_LIKE_COUNT} INTEGER NOT NULL DEFAULT 0,
            ${PostColumns.COLUMN_SHARE_COUNT} INTEGER NOT NULL DEFAULT 0,
            ${PostColumns.COLUMN_VIEWING_COUNT} INTEGER NOT NULL DEFAULT 0,
            ${PostColumns.COLUMN_LIKED_BY_ME} BOOLEAN NOT NULL DEFAULT 0 
        );
        """.trimIndent()
    }

    object PostColumns {
        const val TABLE = "posts"
        const val COLUMN_ID = "id"
        const val COLUMN_AUTHOR = "author"
        const val COLUMN_PUBLISHED = "published"
        const val COLUMN_CONTENT = "content"
        const val COLUMN_VIDEO_IN_POST = "videoInPost"
        const val COLUMN_LIKE_COUNT = "likesCount"
        const val COLUMN_SHARE_COUNT = "shareCount"
        const val COLUMN_VIEWING_COUNT = "viewingCount"
        const val COLUMN_LIKED_BY_ME = "likedByMe"

        val ALL_COLUMNS = arrayOf(
            COLUMN_ID,
            COLUMN_AUTHOR,
            COLUMN_PUBLISHED,
            COLUMN_CONTENT,
            COLUMN_VIDEO_IN_POST,
            COLUMN_LIKE_COUNT,
            COLUMN_SHARE_COUNT,
            COLUMN_VIEWING_COUNT,
            COLUMN_LIKED_BY_ME
        )
    }

    override fun getAll(): List<Post> {
        var posts = mutableListOf<Post>()
        db.query(
            PostColumns.TABLE,
            PostColumns.ALL_COLUMNS,
            null,
            null,
            null,
            null,
            "${PostColumns.COLUMN_ID} DESC"
        ).use {
            while(it.moveToNext()) {
                posts.add(map(it))
            }
        }
        return posts
    }

    override fun likeById(id: Int) {
        db.execSQL(
               """ 
                   UPDATE posts SET
                        likesCount = likesCount + CASE WHEN likedByMe THEN -1 ELSE 1 END,
                        likedByMe = CASE WHEN likedByMe THEN 0 ELSE 1 END
                   WHERE id=?;
                """.trimIndent(), arrayOf(id)
        )
    }

    override fun shareById(id: Int) {
        db.execSQL(
            """UPDATE posts SET shareCount = shareCount + 1
                WHERE id=?;
            """.trimIndent(), arrayOf(id)
        )
    }

    override fun viewingById(id: Int) {
        db.execSQL(
            """UPDATE posts SET viewingCount = viewingCount + 1
                WHERE id=?;
            """.trimIndent(), arrayOf(id)
        )
    }

    override fun savePost(post: Post) : Post {
        val values = ContentValues().apply {
            if (post.id != 0) {
                put(PostColumns.COLUMN_ID, post.id)
            }
            put(PostColumns.COLUMN_AUTHOR,"Me")
            put(PostColumns.COLUMN_CONTENT, post.content)
            put(PostColumns.COLUMN_PUBLISHED, Calendar.getInstance().time.toString())
            TODO("Delete this hardcode later")
            put(PostColumns.COLUMN_VIDEO_IN_POST, "https://youtu.be/WhWc3b3KhnY")
        }
        val id = db.replace(PostColumns.TABLE,null,values)
        db.query(
            PostColumns.TABLE,
            PostColumns.ALL_COLUMNS,
            "${PostColumns.COLUMN_ID} = ?",
            arrayOf(id.toString()),
            null,
            null,
            null
        ).use {
            it.moveToNext()
            return map(it)
        }
    }

    override fun removeById(id: Int) {
        db.delete(
            PostColumns.TABLE,
            "${PostColumns.COLUMN_ID} = ?",
            arrayOf(id.toString())
        )
    }

    private fun map(cursor: Cursor) : Post {
        with(cursor) {
            return Post(
                id = getInt(getColumnIndexOrThrow(PostColumns.COLUMN_ID)),
                author = getString(getColumnIndexOrThrow(PostColumns.COLUMN_AUTHOR)),
                published = getString(getColumnIndexOrThrow(PostColumns.COLUMN_PUBLISHED)),
                content = getString(getColumnIndexOrThrow(PostColumns.COLUMN_CONTENT)),
                videoInPost = getString(getColumnIndexOrThrow(PostColumns.COLUMN_VIDEO_IN_POST)),
                likesCount = getInt(getColumnIndexOrThrow(PostColumns.COLUMN_LIKE_COUNT)),
                shareCount = getInt(getColumnIndexOrThrow(PostColumns.COLUMN_SHARE_COUNT)),
                viewingCount = getInt(getColumnIndexOrThrow(PostColumns.COLUMN_VIEWING_COUNT)),
                likedByMe = getInt(getColumnIndexOrThrow(PostColumns.COLUMN_LIKED_BY_ME)) != 0
            )
        }
    }
}