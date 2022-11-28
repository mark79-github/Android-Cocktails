package com.martinbg.androidcocktails.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cocktail")
data class CocktailEntity(
    @PrimaryKey
    @ColumnInfo(name = "id_drink")
    var idDrink: String,
    @ColumnInfo(name = "str_drink")
    var strDrink: String,
    @ColumnInfo(name = "str_drink_thumb")
    var strDrinkThumb: String,
    @ColumnInfo(name = "str_glass")
    var strGlass: String?,
    @ColumnInfo(name = "str_instructions")
    var strInstructions: String?,
    @ColumnInfo(name = "str_ingredient_measure_1")
    var strIngredientMeasure1: String?,
    @ColumnInfo(name = "str_ingredient_measure_2")
    var strIngredientMeasure2: String?,
    @ColumnInfo(name = "str_ingredient_measure_3")
    var strIngredientMeasure3: String?,
    @ColumnInfo(name = "str_ingredient_measure_4")
    var strIngredientMeasure4: String?,
    @ColumnInfo(name = "str_ingredient_measure_5")
    var strIngredientMeasure5: String?,
    @ColumnInfo(name = "str_ingredient_measure_6")
    var strIngredientMeasure6: String?,
    @ColumnInfo(name = "str_ingredient_measure_7")
    var strIngredientMeasure7: String?,
    @ColumnInfo(name = "str_ingredient_measure_8")
    var strIngredientMeasure8: String?,
    @ColumnInfo(name = "str_ingredient_measure_9")
    var strIngredientMeasure9: String?,
    @ColumnInfo(name = "str_ingredient_measure_10")
    var strIngredientMeasure10: String?,
    @ColumnInfo(name = "str_ingredient_measure_11")
    var strIngredientMeasure11: String?,
    @ColumnInfo(name = "str_ingredient_measure_12")
    var strIngredientMeasure12: String?,
    @ColumnInfo(name = "str_ingredient_measure_13")
    var strIngredientMeasure13: String?,
    @ColumnInfo(name = "str_ingredient_measure_14")
    var strIngredientMeasure14: String?,
    @ColumnInfo(name = "str_ingredient_measure_15")
    var strIngredientMeasure15: String?,
)