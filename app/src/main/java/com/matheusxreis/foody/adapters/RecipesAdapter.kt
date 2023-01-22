package com.matheusxreis.foody.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.matheusxreis.foody.databinding.RecipesRowLayoutBinding
import com.matheusxreis.foody.models.FoodRecipe
import com.matheusxreis.foody.models.Result
import com.matheusxreis.foody.utils.RecipesDiffUtil

// adapter is a structural design pattern that allows objects with incompatible interfaces to collaborate
class RecipesAdapter : RecyclerView.Adapter<RecipesAdapter.MyViewHolder>() {

    private var recipe = emptyList<Result>()

    // this class is the layout which each item of recycler view
    class MyViewHolder(private val binding: RecipesRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(result: Result) {
            // with this method, I bind the result variable of my xml layout to the actual item
            // that the user is interacting
            binding.result = result
            binding.executePendingBindings()
            // ^ this function update the layout when is a change in the data
        }

        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RecipesRowLayoutBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }

        }
    }

    // this method must return the creation of my viewl holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    // this method must pass to my view holder, the actual item which the user is interacting
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentResult = recipe[position]
        holder.bind(currentResult)
    }

    // this method must return the amoutn of items in my rv
    override fun getItemCount(): Int = recipe.size

    // this method created by me, must populate the recycler view
    fun setData(newData: FoodRecipe) {
        // with this DiffUtil logic, only the views are not the same
        // are be updated
        val recipesDiffUtil = RecipesDiffUtil(recipe, newData.results)
        val diffUtilResult = DiffUtil.calculateDiff(recipesDiffUtil)
        recipe = newData.results
        diffUtilResult.dispatchUpdatesTo(this)
        // notifyDataSetChanged() is not very performatic
        // each time that it is called, it change all list
        // without verify if a item changed or not
        // because of this is better use RecipesDiffUtil
        // notifyDataSetChanged()
    }
}