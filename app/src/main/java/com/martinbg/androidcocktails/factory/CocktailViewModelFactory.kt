package com.martinbg.androidcocktails.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.martinbg.androidcocktails.repository.CocktailRepository
import com.martinbg.androidcocktails.viewmodel.CocktailViewModel

class CocktailViewModelFactory(
    private val cocktailRepository: CocktailRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CocktailViewModel(cocktailRepository) as T
    }
}