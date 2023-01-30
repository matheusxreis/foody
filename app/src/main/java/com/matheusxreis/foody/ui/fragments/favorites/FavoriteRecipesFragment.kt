package com.matheusxreis.foody.ui.fragments.favorites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.matheusxreis.foody.R
import com.matheusxreis.foody.adapters.FavoriteRecipesAdapter
import com.matheusxreis.foody.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_favorite_recipes.view.*

@AndroidEntryPoint
class FavoriteRecipesFragment : Fragment() {

    private val mAdapter:FavoriteRecipesAdapter by lazy { FavoriteRecipesAdapter() }
    private val mainViewModel:MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val mView = inflater.inflate(R.layout.fragment_favorite_recipes, container, false)


        setupRecyclerView(mView.favorite_rv)
        mainViewModel.readFavoriteRecipe.observe(viewLifecycleOwner){ favoriteRecipes ->
            mAdapter.setData(favoriteRecipes)
        }


        return mView
    }

    private fun setupRecyclerView(recyclerView: RecyclerView){
        recyclerView.adapter = mAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

}