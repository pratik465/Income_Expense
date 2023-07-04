package com.pachchham.income_expense

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.khatabook2.Model.TransModel

class Database(
    context: Context?
) : SQLiteOpenHelper(context, "Pratik.db", null, 1) {


    var TABLE_NAME = "Trans"
    var ID = "id"
    var AMOUNT = "amount"
    var CATEGORY = "category"
    var NOTE = "note"
    var IS_EXPENSE = "isexpense"

    override fun onCreate(p0: SQLiteDatabase?) {


        val que = "Create Table $TABLE_NAME($ID INTEGER PRIMARY KEY AUTOINCREMENT, " +

                "$AMOUNT INTEGER, $CATEGORY TEXT, $NOTE TEXT, $IS_EXPENSE INTEGER)"

        p0?.execSQL(que)

    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }

    fun addTrans(transModel: TransModel) {
        val db = writableDatabase
        val values = ContentValues().apply {
            transModel.apply {

                put(AMOUNT, amount)
                put(CATEGORY, category)
                put(NOTE, note)
                put(IS_EXPENSE, isexpense)
            }
        }

        db.insert(TABLE_NAME, null, values)

    }

    @SuppressLint("Recycle")
    fun getTransaction(): ArrayList<TransModel> {
        val translist = ArrayList<TransModel>()
        val db = readableDatabase
        val que = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(que, null)
        cursor.moveToFirst()

        for (i in 0..cursor.count - 1) {

            var id = cursor.getInt(0)
            var amount = cursor.getInt(1)
            var category = cursor.getString(2)
            var note = cursor.getString(3)
            var isexpense = cursor.getInt(4)

            var model = TransModel(id, amount, category, note, isexpense)

//            val amount = cursor.getInt(0)
//            val category = cursor.getString(1)
//            val note = cursor.getString(2)
//
//            val model = TransModel(amount, category, note)

            translist.add(model)
            cursor.moveToNext()
        }
        return translist
    }

    fun updateTrans(transModel: TransModel) {

        val db = writableDatabase
        val values = ContentValues().apply {
            transModel.apply {
                put(AMOUNT, amount)
                put(CATEGORY, category)
                put(NOTE, note)
                put(IS_EXPENSE, isexpense)
            }
        }

        db.update(TABLE_NAME, values, "id=${transModel.id}", null)
    }

    fun deleteTrans(id: Int) {
        val db = writableDatabase
        db.delete(TABLE_NAME, "id=$id", null)
    }

}