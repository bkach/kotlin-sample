package app.com.boris.android.randomgif.data

import android.content.Context
import android.database.DatabaseUtils
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 * Created by bkach on 12/14/15.
 */

class DBHelper : SQLiteOpenHelper {

    companion object {
        val DATABASE_NAME = "HISTORY_DATABSE"
        val DATABASE_VERSION = 1

        val TABLE_NAME = "HISTORY_TABLE"
        val COLUMN_NAME_ENTRY_ID = "GIF_ID"
        val COLUMN_NAME_TITLE = "TITLE"
    }

    constructor(context: Context) : super(context, DATABASE_NAME, null, DATABASE_VERSION) {}

    override fun onCreate(db: SQLiteDatabase?) {
        val SQL_CREATE_TABLE =
                """
                CREATE TABLE $TABLE_NAME (
                $COLUMN_NAME_ENTRY_ID TEXT UNIQUE NOT NULL,
                $COLUMN_NAME_TITLE TEXT NOT NULL);
                """

        db?.execSQL(SQL_CREATE_TABLE);
    }


    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS " + DBHelper.TABLE_NAME);
        onCreate(db);
    }

    fun entryExists(id: String, title: String): Boolean {
        val numMatchingEntries =
                DatabaseUtils.queryNumEntries(this.readableDatabase, TABLE_NAME,
                        "$COLUMN_NAME_ENTRY_ID = \"$id\" AND $COLUMN_NAME_TITLE = \"$title\"")
        return (numMatchingEntries > 0);
    }
}
