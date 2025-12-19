package com.example.myapplication

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context?) : SQLiteOpenHelper(context, DBNAME, null, 2) {

    companion object {
        const val DBNAME = "Login.db"
    }

    override fun onCreate(MyDB: SQLiteDatabase) {
        MyDB.execSQL("create Table users(username TEXT primary key, password TEXT)")
        MyDB.execSQL("create Table items(id INTEGER primary key autoincrement, name TEXT, username TEXT)")
    }

    override fun onUpgrade(MyDB: SQLiteDatabase, i: Int, i1: Int) {
        MyDB.execSQL("drop Table if exists users")
        MyDB.execSQL("drop Table if exists items")
        onCreate(MyDB)
    }

    fun insertData(username: String, password: String): Boolean {
        val MyDB = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("username", username)
        contentValues.put("password", password)
        val result = MyDB.insert("users", null, contentValues)
        return result != -1L
    }

    fun checkusername(username: String): Boolean {
        val MyDB = this.writableDatabase
        val cursor = MyDB.rawQuery("Select * from users where username = ?", arrayOf(username))
        return cursor.count > 0
    }

    fun checkusernamepassword(username: String, password: String): Boolean {
        val MyDB = this.writableDatabase
        val cursor = MyDB.rawQuery("Select * from users where username = ? and password = ?", arrayOf(username, password))
        return cursor.count > 0
    }

    fun insertItem(name: String, username: String): Boolean {
        val MyDB = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("name", name)
        contentValues.put("username", username)
        val result = MyDB.insert("items", null, contentValues)
        return result != -1L
    }

    fun getItems(username: String): Cursor? {
        val MyDB = this.readableDatabase
        return MyDB.rawQuery("SELECT id as _id, name FROM items WHERE username = ?", arrayOf(username))
    }

    fun updateItem(id: String, name: String): Boolean {
        val MyDB = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("name", name)
        val result = MyDB.update("items", contentValues, "id = ?", arrayOf(id))
        return result > 0
    }

    fun deleteItem(id: String): Boolean {
        val MyDB = this.writableDatabase
        val result = MyDB.delete("items", "id = ?", arrayOf(id))
        return result > 0
    }
}
