package com.matheusxreis.foody.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.matheusxreis.foody.R
import com.matheusxreis.foody.data.database.entities.FavoritesEntity
import com.matheusxreis.foody.databinding.FavoriteRecipesRowLayoutBinding
import com.matheusxreis.foody.utils.RecipesDiffUtil

class FavoriteRecipesAdapter: RecyclerView.Adapter<FavoriteRecipesAdapter.MyViewHolder>() {

    private var favoriteRecipes = emptyList<FavoritesEntity>()
    class MyViewHolder(private val binding:FavoriteRecipesRowLayoutBinding):
        RecyclerView.ViewHolder(binding.root) {

            fun bind(favoritesEntity: FavoritesEntity){
                binding.favoriteEntity = favoritesEntity
                binding.executePendingBindings()
            }

        companion object {
            fun from(parent: ViewGroup):MyViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context);
                val binding = FavoriteRecipesRowLayoutBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
       return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
         val currentFavoriteRecipe = favoriteRecipes[position]
         holder.bind(currentFavoriteRecipe)
    }

    override fun getItemCount(): Int = favoriteRecipes.size

    fun setData(newFavoriteRecipes:List<FavoritesEntity>){

        val favoriteRecipesDiffUtil = RecipesDiffUtil(
            oldList = favoriteRecipes,
            newList =  newFavoriteRecipes
        )
        val diffUtilResult = DiffUtil.calculateDiff(favoriteRecipesDiffUtil)
        favoriteRecipes = newFavoriteRecipes
        diffUtilResult.dispatchUpdatesTo(this)
    }
}