package com.exam.fintonic.helper

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import com.exam.fintonic.service.model.*

class FeedReaderDbHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "fintonic.db"
        const val DATABASE_VERSION = 1
        const val TABLE_BEERS = "BeersTable"
        const val KEY_ID = "id"
        const val KEY_NAME = "name"
        const val KEY_TAG_LINE = "tagLine"
        const val KEY_DESCRIPTION = "description"
        const val KEY_FIRST_BREWED = "firstBrewed"
        const val KEY_FOOD_PAIRING = "foodPairing"
        const val KEY_IMAGE_URL = "imageUrl"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_BEERS_TABLE=("CREATE TABLE " + TABLE_BEERS + " ("+ KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_NAME + " TEXT," + KEY_TAG_LINE + " TEXT," + KEY_DESCRIPTION + " TEXT,"
                + KEY_FIRST_BREWED + " TEXT," + KEY_FOOD_PAIRING + " TEXT," + KEY_IMAGE_URL + " TEXT" + ")")
        db?.execSQL(CREATE_BEERS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_BEERS")
        onCreate(db)
    }

    fun addBeer(beer: Beer, foodPairing: String): Long{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, beer.id)
        contentValues.put(KEY_NAME, beer.name)
        contentValues.put(KEY_TAG_LINE, beer.tagline)
        contentValues.put(KEY_DESCRIPTION, beer.description)
        contentValues.put(KEY_FIRST_BREWED, beer.firstBrewed)
        contentValues.put(KEY_FOOD_PAIRING, foodPairing)
        contentValues.put(KEY_IMAGE_URL, beer.imageUrl)
        //insert rows
        val success = db.insert(TABLE_BEERS, null, contentValues)
        db.close()
        return success
    }

    fun deleteBeer(): Int{
        val db = this.writableDatabase
        val success = db.delete(TABLE_BEERS, null, null)
        db.close()
        return success
    }

    fun updateBeer(beer: Beer, foodPairing: String): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, beer.id)
        contentValues.put(KEY_NAME, beer.name)
        contentValues.put(KEY_TAG_LINE, beer.tagline)
        contentValues.put(KEY_DESCRIPTION, beer.description)
        contentValues.put(KEY_FIRST_BREWED, beer.firstBrewed)
        contentValues.put(KEY_FOOD_PAIRING, foodPairing)
        contentValues.put(KEY_IMAGE_URL, beer.imageUrl)
        //update rows
        val success = db.update(TABLE_BEERS, contentValues, "id="+beer.id, null)
        db.close()
        return success
    }

    fun getBeers(): MutableList<Beer>{
        val beersList: ArrayList<Beer> = ArrayList()
        val selectQuery = "SELECT * FROM $TABLE_BEERS"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }
        //var idBeer = Int
        //var beerName = String
        //var beerTagLine = String
        //var beerDescription = String
        //var beerFirstBrewed = String
        //var beerFoodPairing = String
        //var beerImageUrl = String
        if (cursor.moveToFirst()){
            do {
                val idBeer = cursor.getInt(cursor.getColumnIndex("id"))
                val beerName = cursor.getString(cursor.getColumnIndex("name"))
                val beerTagLine = cursor.getString(cursor.getColumnIndex("tagLine"))
                val beerDescription = cursor.getString(cursor.getColumnIndex("description"))
                val beerFirstBrewed = cursor.getString(cursor.getColumnIndex("firstBrewed"))
                val beerFoodPairing = cursor.getString(cursor.getColumnIndex("foodPairing"))
                val beerImageUrl = cursor.getString(cursor.getColumnIndex("imageUrl"))
                val beer = Beer(id = idBeer, name = beerName, tagline = beerTagLine, description = beerDescription,
                firstBrewed = beerFirstBrewed, imageUrl = beerImageUrl, foodPairing = arrayListOf(), abv = 0F,
                ibu = 0F, targetFg = 0F, targetOg = 0F, ebc = 0F, srm = 0F, ph = 0F, attenuationLevel = 0F,
                volume = Volume(), boilVolume = BoilVolume(), method = Method(), ingredients = Ingredients(), 
                brewersTips = "", contributedBy = "")
                beer.foodDataBase = beerFoodPairing
                beersList.add(beer)
            } while (cursor.moveToNext())
        }
        return beersList
    }

}