package com.hakansarac.workoutapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SqliteOpenHelper(context: Context, factory : SQLiteDatabase.CursorFactory?): SQLiteOpenHelper(context, DATABASE_NAME,factory, DATABASE_VERSION) {
    companion object{
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "Workout.db"
        private val TABLE_HISTORY = "history"
        private val COLUMN_ID = "_id"
        private val COLUMN_COMPLETED_DATE = "completed_date"
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        val CREATE_EXERCISE_TABLE = ("CREATE TABLE " + TABLE_HISTORY + "(" + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_COMPLETED_DATE + " TEXT)")
        p0?.execSQL(CREATE_EXERCISE_TABLE)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0?.execSQL("DROP TABLE IF EXISTS " + TABLE_HISTORY)
        onCreate(p0)
    }

    /**
     * Add a date to database when user finish an exercise
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