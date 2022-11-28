package com.martinbg.androidcocktails.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.martinbg.androidcocktails.R
import com.martinbg.androidcocktails.activity.MainActivity
import com.martinbg.androidcocktails.adapter.CocktailAdapter
import com.martinbg.androidcocktails.databinding.FragmentCocktailBinding
import com.martinbg.androidcocktails.util.NetworkUtil
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class CocktailFragment : Fragment() {

    private lateinit var binding: FragmentCocktailBinding
    private lateinit var appContext: Context
    private var backPressed: Long = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requireActivity()
            .onBackPressedDispatcher
            .addCallback(this, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (backPressed + 2000 > System.currentTimeMillis()) {
                        requireActivity().finish()
                    } else {
                        Toast.makeText(
                            appContext,
                            "Press once again to exit!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    backPressed = System.currentTimeMillis()
                }
            })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        appContext = inflater.context
        binding = FragmentCocktailBinding.inflate(inflater)
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

        GlobalScope.launch {
            (activity as MainActivity).cocktailViewModel.getCocktailList()
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun observeData() {
        GlobalScope.launch {
            (activity as MainActivity).cocktailViewModel.cocktailList.collect {
                (activity as MainActivity).runOnUiThread {
                    val cocktails = it
                    val sortedCurrencies = cocktails.sortedWith(
                        compareBy(
                            { !it.favourite.isFavourite },
                            { it.cocktail.strDrink },
                        )
                    )
                    val adapter = CocktailAdapter(sortedCurrencies)
                    binding.recyclerView.adapter = adapter
                    binding.tvCocktailsCount.text = getString(R.string.cocktail_size, it.size)
                }
            }
        }
    }
}