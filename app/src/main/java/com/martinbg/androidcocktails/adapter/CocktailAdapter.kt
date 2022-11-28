package com.martinbg.androidcocktails.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.martinbg.androidcocktails.R
import com.martinbg.androidcocktails.databinding.CocktailListItemBinding
import com.martinbg.androidcocktails.db.relation.CocktailAndFavourite
import com.martinbg.androidcocktails.fragment.CocktailFragmentDirections
import com.martinbg.androidcocktails.util.StringUtils.capitalizeFirstChar


class CocktailAdapter(value: List<CocktailAndFavourite>) :
    RecyclerView.Adapter<CocktailAdapter.ViewHolder>() {
    private lateinit var binding: CocktailListItemBinding
    private lateinit var context: Context
    private val currentList = value

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = CocktailListItemBinding.inflate(inflater, parent, false)
        context = parent.context
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    inner class ViewHolder : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CocktailAndFavourite) {
            binding.apply {
                name = item.cocktail.strDrink.capitalizeFirstChar()

                ivLiked.visibility = if (item.favourite.isFavourite) View.VISIBLE else View.GONE

                Glide
                    .with(root.context)
                    .load(item.cocktail.strDrinkThumb)
                    .centerCrop()
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .into(ivFlag)

                root.setOnClickListener {
                    val navController = Navigation.findNavController(it)
                    val action =
                        CocktailFragmentDirections.actionCocktailFragmentToCocktailDetailsFragment(
                            item.cocktail.idDrink
                        )
                    navController.navigate(action)
                }
            }
        }
    }

}