package com.martinbg.androidcocktails.service

import com.martinbg.androidcocktails.model.CocktailDetailsResponse
import com.martinbg.androidcocktails.model.CocktailResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CocktailService {
    @GET("filter.php")
    fun getCocktails(@Query("c") cocktail: String? = "Cocktail"): Call<CocktailResponse>

    @GET("lookup.php")
    fun getCocktailById(@Query("i") id: String): Call<CocktailDetailsResponse>
}