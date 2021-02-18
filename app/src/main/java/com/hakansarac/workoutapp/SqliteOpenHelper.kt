package com.hakansarac.workoutapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


/**
 * Create a helper object to create, open, and/or manage a database.
 * This method always returns very quickly.  The database is not actually
 * created or opened until one of getWritableDatabase or
 * getReadableDatabase is called.
 *
 * @param context to use for locating paths to the the database
 * @param name of the database file, or null for an in-memory database
 * @param factory to use for creating cursor objects, or null for the default
 * @param version number of the database (starting at 1); if the database is older,
 *     #onUpgrade will be used to upgrade the database; if the database is
 *     newer, #onDowngrade will be used to downgrade the database
 */
class SqliteOpenHelper(context: Context, factory : SQLiteDatabase.CursorFactory?): SQLiteOpenHelper(context, DATABASE_NAME,factory, DATABASE_VERSION) {
    companion object{
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "Workout.db"
        private val TABLE_HISTORY = "history"
        private val COLUMN_ID = "_id"
        private val COLUMN_COMPLETED_DATE = "completed_date"
    }

    /**
     * This override function is used to execute a when the class is called once where the database tables are created.
     */
    override fun onCreate(p0: SQLiteDatabase?) {
        val CREATE_EXERCISE_TABLE = ("CREATE TABLE " + TABLE_HISTORY + "(" + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_COMPLETED_DATE + " TEXT)")
        p0?.execSQL(CREATE_EXERCISE_TABLE)
    }

    /**
     * This function is called when the database version is changed.
     */
    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0?.execSQL("DROP TABLE IF EXISTS " + TABLE_HISTORY)       // It drops the existing history table
        onCreate(p0)    // Calls the onCreate function so all the updated table will be created.
    }

    /**
     * Add a date to database when user finished an exercise
     */
    fun addDate(date:String){
        val values = ContentValues()
        values.put(COLUMN_COMPLETED_DATE,date)
        val db = this.writableDatabase
        db.insert(TABLE_HISTORY,null,values)
        db.close()
    }

    /**
     * get all completed exercise dates from database
     */
    fun getAllCompletedDates() : ArrayList<String>{
        val listDates : ArrayList<String> = ArrayList<String>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_HISTORY",null)
        while(cursor.moveToNext()){
            val date = cursor.getString(cursor.getColumnIndex(COLUMN_COMPLETED_DATE))
            listDates.add(date)
        }
        cursor.close()
        //db.close()
        return listDates
    }
}