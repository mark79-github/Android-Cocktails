package com.martinbg.androidcocktails.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.martinbg.androidcocktails.R
import com.martinbg.androidcocktails.activity.MainActivity
import com.martinbg.androidcocktails.databinding.FragmentCocktailDetailsBinding
import com.martinbg.androidcocktails.db.relation.CocktailAndFavourite
import com.martinbg.androidcocktails.util.NetworkUtil
import com.martinbg.androidcocktails.util.StringUtils.capitalizeFirstChar
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CocktailDetailsFragment : Fragment() {

    private lateinit var binding: FragmentCocktailDetailsBinding
    private lateinit var appContext: Context
    private val args: CocktailDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        appContext = inflater.context
        binding = FragmentCocktailDetailsBinding.inflate(LayoutInflater.from(context))
        return binding.root
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()

        if (!NetworkUtil.isConnected(appContext)) {
            Snackbar.make(
                binding.root,
                "No internet connection, information could be outdated",
                Snackbar.LENGTH_LONG
            ).show()
        }

        val id = args.cocktailId
        GlobalScope.launch {
            (activity as MainActivity).cocktailViewModel.getCocktailById(id)
        }
    }

    private fun observeData() {
        lifecycleScope.launch {
            (activity as MainActivity).cocktailViewModel.selectedCocktail.collect {
                val model =
                    it?.copy(cocktail = it.cocktail.copy(strDrink = it.cocktail.strDrink.capitalizeFirstChar()))
                        ?: return@collect
                binding.model = model
                binding.hasIngredients = hasIngredients(model)
                (activity as? MainActivity)?.runOnUiThread {
                    setDataBinding()
                    Glide
                        .with(context ?: return@runOnUiThread)
                        .load(it.cocktail.strDrinkThumb)
                        .centerCrop()
                        .placeholder(R.drawable.ic_launcher_foreground)
                        .into(binding.ivFlag)
                }
            }
        }
    }

    private fun setDataBinding() {
        binding.model ?: return

        if (binding.model?.favourite?.isFavourite == true) {
            binding.btnLike.setImageResource(android.R.drawable.star_big_on)
        } else {
            binding.btnLike.setImageResource(android.R.drawable.star_big_off)
        }

        binding.btnLike.setOnClickListener {
            val cocktail = binding.model
            cocktail?.favourite?.isFavourite = cocktail?.favourite?.isFavourite != true

            if (cocktail?.favourite?.isFavourite == true) {
                binding.btnLike.setImageResource(android.R.drawable.star_big_on)
            } else {
                binding.btnLike.setImageResource(android.R.drawable.star_big_off)
            }

            lifecycleScope.launch {
                (activity as MainActivity).cocktailViewModel.updateCocktail(
                    cocktail?.cocktail ?: return@launch, cocktail.favourite
                )
            }
        }
    }

    private fun hasIngredients(model: CocktailAndFavourite): Boolean {
        model.cocktail.strIngredientMeasure1?.let {
            return true
        }
        return false
    }
}