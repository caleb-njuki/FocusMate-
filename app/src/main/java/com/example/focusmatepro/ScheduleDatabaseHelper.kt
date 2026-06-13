package com.example.focusmatepro

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ScheduleDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, "ScheduleDB", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {

        db.execSQL(
            """
            CREATE TABLE schedule(
            cell TEXT PRIMARY KEY,
            value TEXT
            )
            """
        )

    }

    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int
    ) {

        db.execSQL("DROP TABLE IF EXISTS schedule")

        onCreate(db)

    }

    fun saveCell(
        cell: String,
        value: String
    ) {

        val db = writableDatabase

        val values = ContentValues()

        values.put("cell", cell)

        values.put("value", value)

        db.insertWithOnConflict(

            "schedule",

            null,

            values,

            SQLiteDatabase.CONFLICT_REPLACE

        )

        db.close()

    }

    fun getCell(cell: String): String {

        val db = readableDatabase

        val cursor = db.rawQuery(

            "SELECT value FROM schedule WHERE cell=?",

            arrayOf(cell)

        )

        var result = ""

        if (cursor.moveToFirst()) {

            result = cursor.getString(0)

        }

        cursor.close()

        db.close()

        return result

    }

}