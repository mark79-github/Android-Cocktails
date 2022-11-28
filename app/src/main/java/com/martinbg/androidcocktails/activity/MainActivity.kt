package com.martinbg.androidcocktails.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.martinbg.androidcocktails.databinding.ActivityMainBinding
import com.martinbg.androidcocktails.db.database.CocktailDatabase
import com.martinbg.androidcocktails.factory.CocktailViewModelFactory
import com.martinbg.androidcocktails.repository.CocktailRepository
import com.martinbg.androidcocktails.service.CocktailService
import com.martinbg.androidcocktails.util.RetrofitHelper
import com.martinbg.androidcocktails.viewmodel.CocktailViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var cocktailService: CocktailService
    private lateinit var cocktailRepository: CocktailRepository
    lateinit var cocktailViewModel: CocktailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        init()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun init() {
        val cocktailDao = CocktailDatabase.getDatabase(applicationContext).cocktailDao()
        cocktailService = RetrofitHelper.getClient().create(CocktailService::class.java)
        cocktailRepository = CocktailRepository(this, cocktailService, cocktailDao)
        val cocktailViewModelFactory = CocktailViewModelFactory(cocktailRepository)
        cocktailViewModel =
            ViewModelProvider(this, cocktailViewModelFactory)[CocktailViewModel::class.java]
    }
}