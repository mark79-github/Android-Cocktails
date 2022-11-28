package com.martinbg.androidcocktails.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourite")
data class FavouriteEntity(
    @ColumnInfo(name = "is_favourite")
    var isFavourite: Boolean = false,
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id_drink")
    val idDrink: String
)