package com.martinbg.androidcocktails.db.dao

import androidx.room.*
import com.martinbg.androidcocktails.db.entity.CocktailEntity
import com.martinbg.androidcocktails.db.entity.FavouriteEntity
import com.martinbg.androidcocktails.db.relation.CocktailAndFavourite

@Dao
interface CocktailDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCocktails(cocktails: List<CocktailEntity>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateCocktail(cocktail: CocktailEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavourites(favourites: List<FavouriteEntity>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateFavourite(favourite: FavouriteEntity)

    @Transaction
    @Query("SELECT * FROM cocktail WHERE id_drink = :id")
    suspend fun getCocktailById(id: String): CocktailAndFavourite

    @Transaction
    @Query("SELECT * FROM cocktail")
    suspend fun getCocktails(): List<CocktailAndFavourite>
}