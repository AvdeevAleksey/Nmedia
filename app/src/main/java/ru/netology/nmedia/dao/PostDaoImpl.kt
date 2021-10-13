package ru.netology.nmedia.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.db.DbHelper
import ru.netology.nmedia.dto.Post
import java.util.*

class PostDaoImpl(
    context: Context,
    private val db: SQLiteDatabase
) : PostDao {

    companion object {
        internal val DATABASE_VERSION = 1
        internal val DATABASE_NAME = "MyPostsSQliteDatabase.db"
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
        const val COLUMN_LIKE_BY_MY = "likedByMe"

        val ALL_COLUMNS = arrayOf(
            COLUMN_ID,
            COLUMN_AUTHOR,
            COLUMN_PUBLISHED,
            COLUMN_CONTENT,
            COLUMN_VIDEO_IN_POST,
            COLUMN_LIKE_COUNT,
            COLUMN_SHARE_COUNT,
            COLUMN_VIEWING_COUNT,
            COLUMN_LIKE_BY_MY
        )
    }

    private var posts = mutableListOf<Post>()

    override fun getAll(): List<Post> {
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
                        likesCount = likesCount + CASE WHEN likeByMe THEN -1 ELSE 1 END;
                        likeByMe = CASE WHEN likeByMe THEN 0 ELSE 1 END;
                WHERE id=?;
            """.trimIndent(), arrayOf(id)
        )
    }

    override fun shareById(id: Int) {
        db.execSQL(
            """
                UPDATE posts SET
                        shareById = shareById + 1;
                WHERE id=?;
            """.trimIndent(), arrayOf(id)
        )
    }

    override fun viewingById(id: Int) {
        db.execSQL(
            """
                UPDATE posts SET
                        viewingById = viewingById + 1;
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
                likedByMe = getInt(getColumnIndexOrThrow(PostColumns.COLUMN_LIKE_BY_MY)) != 0
            )
        }
    }
}