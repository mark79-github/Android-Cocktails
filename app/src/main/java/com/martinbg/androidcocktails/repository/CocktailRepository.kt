package com.martinbg.androidcocktails.repository

import android.content.Context
import com.martinbg.androidcocktails.db.dao.CocktailDao
import com.martinbg.androidcocktails.db.entity.CocktailEntity
import com.martinbg.androidcocktails.db.entity.FavouriteEntity
import com.martinbg.androidcocktails.db.relation.CocktailAndFavourite
import com.martinbg.androidcocktails.model.CocktailDetailsModel
import com.martinbg.androidcocktails.model.CocktailModel
import com.martinbg.androidcocktails.service.CocktailService
import com.martinbg.androidcocktails.util.NetworkUtil

class CocktailRepository constructor(
    private val context: Context,
    private val cocktailService: CocktailService,
    private val cocktailDao: CocktailDao
) {
    suspend fun getCocktails(): List<CocktailAndFavourite> {
        return try {
            if (NetworkUtil.isConnected(context)) {
                val response = cocktailService.getCocktails().execute().body()
                val roomCocktails =
                    response?.drinks?.map { mapResponseToCocktailDbModel(it) }
                val roomFavourites =
                    response?.drinks?.map { mapResponseToFavouriteDbModel(it) }
                roomCocktails?.let { cocktailDao.insertCocktails(it) }
                roomFavourites?.let { cocktailDao.insertFavourites(it) }
            }
            cocktailDao.getCocktails()
        } catch (e: Exception) {
            arrayListOf()
        }
    }

    suspend fun getCocktailById(id: String): CocktailAndFavourite? {
        return try {
            if (NetworkUtil.isConnected(context)) {
                val response = cocktailService.getCocktailById(id).execute().body()
                response?.drinks?.first()?.let {
                    val roomCocktail = mapResponseDetailsToCocktailDbModel(it)
                    roomCocktail?.let { currentCocktail ->
                        cocktailDao.updateCocktail(currentCocktail)
                    }
                }
            }
            return cocktailDao.getCocktailById(id)
        } catch (e: Exception) {
            null
        }
    }

    suspend fun updateCocktail(cocktail: CocktailEntity) {
        cocktailDao.updateCocktail(cocktail)
    }

    suspend fun updateFavourite(favourite: FavouriteEntity) {
        cocktailDao.updateFavourite(favourite)
    }

    private fun mapResponseToCocktailDbModel(cocktailModel: CocktailModel): CocktailEntity {
        return CocktailEntity(
            idDrink = cocktailModel.idDrink,
            strDrink = cocktailModel.strDrink,
            strDrinkThumb = cocktailModel.strDrinkThumb,
            strGlass = null,
            strInstructions = null,
            strIngredientMeasure1 = null,
            strIngredientMeasure2 = null,
            strIngredientMeasure3 = null,
            strIngredientMeasure4 = null,
            strIngredientMeasure5 = null,
            strIngredientMeasure6 = null,
            strIngredientMeasure7 = null,
            strIngredientMeasure8 = null,
            strIngredientMeasure9 = null,
            strIngredientMeasure10 = null,
            strIngredientMeasure11 = null,
            strIngredientMeasure12 = null,
            strIngredientMeasure13 = null,
            strIngredientMeasure14 = null,
            strIngredientMeasure15 = null,
        )
    }

    private fun mapResponseDetailsToCocktailDbModel(cocktail: CocktailDetailsModel): CocktailEntity? {
        return CocktailEntity(
            idDrink = cocktail.idDrink,
            strDrink = cocktail.strDrink,
            strDrinkThumb = cocktail.strDrinkThumb,
            strGlass = cocktail.strGlass,
            strInstructions = cocktail.strInstructions,
            strIngredientMeasure1 = calcMeasureIngredientsText(
                cocktail.strMeasure1,
                cocktail.strIngredient1
            ),
            strIngredientMeasure2 = calcMeasureIngredientsText(
                cocktail.strMeasure2,
                cocktail.strIngredient2
            ),
            strIngredientMeasure3 = calcMeasureIngredientsText(
                cocktail.strMeasure3,
                cocktail.strIngredient3
            ),
            strIngredientMeasure4 = calcMeasureIngredientsText(
                cocktail.strMeasure4,
                cocktail.strIngredient4
            ),
            strIngredientMeasure5 = calcMeasureIngredientsText(
                cocktail.strMeasure5,
                cocktail.strIngredient5
            ),
            strIngredientMeasure6 = calcMeasureIngredientsText(
                cocktail.strMeasure6,
                cocktail.strIngredient6
            ),
            strIngredientMeasure7 = calcMeasureIngredientsText(
                cocktail.strMeasure7,
                cocktail.strIngredient7
            ),
            strIngredientMeasure8 = calcMeasureIngredientsText(
                cocktail.strMeasure8,
                cocktail.strIngredient8
            ),
            strIngredientMeasure9 = calcMeasureIngredientsText(
                cocktail.strMeasure9,
                cocktail.strIngredient9
            ),
            strIngredientMeasure10 = calcMeasureIngredientsText(
                cocktail.strMeasure10,
                cocktail.strIngredient10
            ),
            strIngredientMeasure11 = calcMeasureIngredientsText(
                cocktail.strMeasure11,
                cocktail.strIngredient11
            ),
            strIngredientMeasure12 = calcMeasureIngredientsText(
                cocktail.strMeasure12,
                cocktail.strIngredient12
            ),
            strIngredientMeasure13 = calcMeasureIngredientsText(
                cocktail.strMeasure13,
                cocktail.strIngredient13
            ),
            strIngredientMeasure14 = calcMeasureIngredientsText(
                cocktail.strMeasure14,
                cocktail.strIngredient14
            ),
            strIngredientMeasure15 = calcMeasureIngredientsText(
                cocktail.strMeasure15,
                cocktail.strIngredient15
            ),
        )
    }

    private fun calcMeasureIngredientsText(measure: String?, ingredient: String?): String? {
        if (measure.isNullOrBlank() && ingredient.isNullOrBlank()) {
            return null
        } else if (measure.isNullOrBlank()) {
            return ingredient
        } else if (ingredient.isNullOrBlank()) {
            return measure
        }
        return "$measure $ingredient"
    }

    private fun mapResponseToFavouriteDbModel(cocktail: CocktailModel): FavouriteEntity {
        return FavouriteEntity(
            idDrink = cocktail.idDrink
        )
    }
}