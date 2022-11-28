package com.martinbg.androidcocktails.util

import android.view.View
import androidx.databinding.BindingAdapter

object BindingAdapters {
    @JvmStatic
    @BindingAdapter("app:visibleGone")
    fun showHide(view: View, ingredientMeasure: String?) {
        view.visibility =
            if (ingredientMeasure.isNullOrBlank()) View.GONE else View.VISIBLE
    }

    @JvmStatic
    @BindingAdapter("app:labelGone")
    fun showHideLabel(view: View, hasIngredients: Boolean) {
        view.visibility =
            if (hasIngredients) View.VISIBLE else View.GONE
    }
}