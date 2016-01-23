package app.com.boris.android.randomgif.data

import android.content.ContentValues
import android.content.Context

/**
 * Created by bkach on 12/14/15.
 */
class GifData(context: Context) {

    val dBHelper = DBHelper(context)

    fun insert(id: String, title: String){
        if (!dBHelper.entryExists(id, title)) {
            val values = ContentValues(2);
            values.put(DBHelper.COLUMN_NAME_ENTRY_ID, id);
            values.put(DBHelper.COLUMN_NAME_TITLE, title);
            dBHelper.writableDatabase.insert(DBHelper.TABLE_NAME, null, values)
        }
    }

    fun get(): List<String> {
        val projection : Array<String> = arrayOf(DBHelper.COLUMN_NAME_TITLE)

        val c = dBHelper.readableDatabase.query(
                DBHelper.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        )

        var columnIndex = c.getColumnIndexOrThrow(DBHelper.COLUMN_NAME_TITLE);
        var results : MutableList<String> = arrayListOf()
        while (c.moveToNext()) {
            results.add(c.getString(columnIndex))
        }
        c.close()
        return results.toList();
    }
}