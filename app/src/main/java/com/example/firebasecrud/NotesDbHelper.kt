package com.example.firebasecrud

import android.content.ContentValues
import android.database.sqlite.SQLiteOpenHelper
import android.content.Context;
import android.database.sqlite.SQLiteDatabase

class NotesDbHelper(context: Context)  : SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "notes.db"
        private const val DATABASE_VERSION = 1;
        private const val TABLE_NAME = "tbl_notes";
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val tableQuery =
            "CREATE TABLE $TABLE_NAME (`id` INTEGER PRIMARY KEY,`title` TEXT,`description` TEXT)"
        db?.execSQL(tableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val tableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(tableQuery)
        onCreate(db)
    }

    fun insertNote(note: Note) {
        val db = writableDatabase;
        val value = ContentValues().apply {
            put("title", note.title)
            put("description", note.description)
        }

        db.insert(TABLE_NAME, null, value)
        db.close()
    }

    fun getAllNotes(): List<Note> {
        val noteList = mutableListOf<Note>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"

        val data = db.rawQuery(query, null)

        while (data.moveToNext()) {
            val id = data.getInt(data.getColumnIndexOrThrow("id"))
            val title = data.getString(data.getColumnIndexOrThrow("title"))
            val description = data.getString(data.getColumnIndexOrThrow("description"))

            val note = Note(id, title, description);
            noteList.add(note)
        }
        data.close()
        db.close()

        return noteList;
    }

    fun updateNote(note: Note) {
        val db = writableDatabase;
        val value = ContentValues().apply {
            put("title", note.title)
            put("description", note.description)
        }
        val whereClause = "`id` = ?"
        val whereArgs = arrayOf(note.id.toString())
        db.update(TABLE_NAME,value,whereClause,whereArgs)
        db.close()
    }

    fun getNoteById(id:Int) : Note{
        val noteList = mutableListOf<Note>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE `id`=$id";

        val data = db.rawQuery(query, null)

        data.moveToFirst()

        val id = data.getInt(data.getColumnIndexOrThrow("id"))
        val title = data.getString(data.getColumnIndexOrThrow("title"))
        val description = data.getString(data.getColumnIndexOrThrow("description"))
        data.close()
        db.close()
        return Note(id,title,description)
    }

    fun deleteNote(id:Int){
        val db = writableDatabase
        var whereClause = "`id` = ?"
        var whereArgs = arrayOf(id.toString())
        db.delete(TABLE_NAME,whereClause,whereArgs)
        db.close()
    }
}

