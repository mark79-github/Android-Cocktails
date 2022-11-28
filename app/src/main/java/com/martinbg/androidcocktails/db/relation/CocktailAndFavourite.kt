package com.martinbg.androidcocktails.db.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.martinbg.androidcocktails.db.entity.CocktailEntity
import com.martinbg.androidcocktails.db.entity.FavouriteEntity

data class CocktailAndFavourite(
    @Embedded var cocktail: CocktailEntity,
    @Relation(
        parentColumn = "id_drink",
        entityColumn = "id_drink"
    )
    var favourite: FavouriteEntity
)
