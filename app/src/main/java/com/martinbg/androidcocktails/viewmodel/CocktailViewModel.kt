package com.martinbg.androidcocktails.viewmodel

import androidx.lifecycle.ViewModel
import com.martinbg.androidcocktails.db.entity.CocktailEntity
import com.martinbg.androidcocktails.db.entity.FavouriteEntity
import com.martinbg.androidcocktails.db.relation.CocktailAndFavourite
import com.martinbg.androidcocktails.repository.CocktailRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.runBlocking

class CocktailViewModel(
    private val cocktailRepository: CocktailRepository
) : ViewModel() {

    private val cocktailListStateFlow = MutableStateFlow<List<CocktailAndFavourite>>(arrayListOf())
    private val selectedCocktailStateFlow = MutableStateFlow<CocktailAndFavourite?>(null)

    val cocktailList: StateFlow<List<CocktailAndFavourite>> = cocktailListStateFlow.asStateFlow()
    val selectedCocktail: StateFlow<CocktailAndFavourite?> = selectedCocktailStateFlow.asStateFlow()

    fun getCocktailList() = runBlocking {
        val cocktailAndFavourites = cocktailRepository.getCocktails()
        cocktailListStateFlow.value = cocktailAndFavourites
    }

    fun getCocktailById(id: String) = runBlocking {
        val cocktail = cocktailRepository.getCocktailById(id)
        selectedCocktailStateFlow.value = cocktail ?: return@runBlocking
    }

    fun updateCocktail(cocktail: CocktailEntity, favourite: FavouriteEntity) = runBlocking {
        cocktailRepository.updateCocktail(cocktail)
        cocktailRepository.updateFavourite(favourite)
        selectedCocktailStateFlow.value =
            selectedCocktailStateFlow.value?.copy(favourite = favourite)
    }
}