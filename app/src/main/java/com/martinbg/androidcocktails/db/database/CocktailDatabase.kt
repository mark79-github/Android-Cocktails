package com.martinbg.androidcocktails.db.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.martinbg.androidcocktails.db.dao.CocktailDao
import com.martinbg.androidcocktails.db.entity.CocktailEntity
import com.martinbg.androidcocktails.db.entity.FavouriteEntity

@Database(
    entities = [CocktailEntity::class, FavouriteEntity::class],
    version = 1,
    exportSchema = false
)
abstract class CocktailDatabase : RoomDatabase() {

    abstract fun cocktailDao(): CocktailDao

    companion object {
        @Volatile
        private var INSTANCE: CocktailDatabase? = null

        fun getDatabase(context: Context): CocktailDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CocktailDatabase::class.java,
                    "cocktails.db"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}