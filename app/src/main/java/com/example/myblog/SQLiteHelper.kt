package com.example.myblog

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast



class SQLiteHelper(context:Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    private val context: Context?
        get() {
            TODO()
        }

    companion object{

        private const val DATABASE_NAME= "myblog.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "mes_blogs"
        private const val ID = "id"
        private const val TITLE = "title"
        private const val DESCRIPTION = "description"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val query = "CREATE TABLE $TABLE_NAME ($ID INTEGER PRIMARY KEY AUTOINCREMENT, $TITLE TEXT, $DESCRIPTION TEXT);"
        db?.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addArticle(titre: String, description: String) {
        val dbb = this.writableDatabase
        val cv = ContentValues()

        cv.put(TITLE, titre)
        cv.put(DESCRIPTION, description)
            val result = dbb.insert(TABLE_NAME, null, cv)
        if (result == -1L) {
            Toast.makeText(this.context, "Erreur", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this.context, "Ajouter avec succées", Toast.LENGTH_SHORT).show()
        }
    }

    fun getAllArticles(): List<Article> {
        val db = this.writableDatabase
        val posts = mutableListOf<Article>()

        val cursor = db.query(TABLE_NAME, null, null, null, null, null, null)
        cursor.moveToFirst()
        while (!cursor.isAfterLast) {
            val p = Article()
            p.id = cursor.getLong(0).toInt()
            p.titre = cursor.getString(1)
            p.description = cursor.getString(2)
            posts.add(p)
            cursor.moveToNext()
        }
        cursor.close()
        return posts
    }

    fun getArticleById(id: Long): Article {
        val db = this.readableDatabase
        var cursor: Cursor? = null

        val objet = Article()

        try {
            val query = "SELECT * FROM $TABLE_NAME WHERE $ID = ?"
            val selectionArgs = arrayOf(id.toString())

            cursor = db.rawQuery(query, selectionArgs)

            if (cursor != null && cursor.moveToFirst()) {
                objet.id = id.toInt()
                objet.titre = cursor.getString(1)
                objet.description = cursor.getString(2)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            cursor?.close()
        }
        return objet
    }
}