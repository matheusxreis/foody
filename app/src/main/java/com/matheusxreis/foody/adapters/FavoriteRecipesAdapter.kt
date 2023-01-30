package com.matheusxreis.foody.adapters

import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.matheusxreis.foody.R
import com.matheusxreis.foody.data.database.entities.FavoritesEntity
import com.matheusxreis.foody.databinding.FavoriteRecipesRowLayoutBinding
import com.matheusxreis.foody.ui.DetailsActivity
import com.matheusxreis.foody.ui.fragments.favorites.FavoriteRecipesFragmentDirections
import com.matheusxreis.foody.utils.RecipesDiffUtil
import kotlinx.android.synthetic.main.favorite_recipes_row_layout.view.*

class FavoriteRecipesAdapter(
    private val requireActivity: FragmentActivity
) : RecyclerView.Adapter<FavoriteRecipesAdapter.MyViewHolder>(), ActionMode.Callback {

    private var multiSelection = false
    private var selectedRecipes = arrayListOf<FavoritesEntity>()
    private var myViewHolders = arrayListOf<MyViewHolder>()

    private var favoriteRecipes = emptyList<FavoritesEntity>()



    class MyViewHolder(private val binding: FavoriteRecipesRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(favoritesEntity: FavoritesEntity) {
            binding.favoriteEntity = favoritesEntity
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
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

        myViewHolders.add(holder)

        val currentFavoriteRecipe = favoriteRecipes[position]
        holder.bind(currentFavoriteRecipe)

        /*
        * Single click listener
        * */
        holder.itemView.favorite_recipe_row_layout.setOnClickListener {
            if(multiSelection){
                applySelection(holder, currentFavoriteRecipe)
            }else {
                val action =
                    FavoriteRecipesFragmentDirections.actionFavoriteRecipesFragmentToDetailsActivity(
                        currentFavoriteRecipe.result
                    )
                holder.itemView.findNavController().navigate(action)
            }
        }

        /*
        * Long click listener
        * */

        holder.itemView.favorite_recipe_row_layout.setOnLongClickListener {

            if(!multiSelection){
                multiSelection = true
                requireActivity.startActionMode(this)
                applySelection(holder, currentFavoriteRecipe)
                true
            }else {
                multiSelection = false
                false
            }


        }
    }

    override fun getItemCount(): Int = favoriteRecipes.size

    fun setData(newFavoriteRecipes: List<FavoritesEntity>) {

        val favoriteRecipesDiffUtil = RecipesDiffUtil(
            oldList = favoriteRecipes,
            newList = newFavoriteRecipes
        )
        val diffUtilResult = DiffUtil.calculateDiff(favoriteRecipesDiffUtil)
        favoriteRecipes = newFavoriteRecipes
        diffUtilResult.dispatchUpdatesTo(this)
    }

    private fun applyStatusBarColor(color: Int) {
        requireActivity.window.statusBarColor = ContextCompat.getColor(requireActivity, color)
    }

    private fun applySelection(holder:MyViewHolder, currentRecipe:FavoritesEntity){
        if(selectedRecipes.contains(currentRecipe)){
            selectedRecipes.remove(currentRecipe)
            changeRecipeStyle(holder, R.color.cardBackgroundColor, R.color.strokeColor)
        }else {
            selectedRecipes.add(currentRecipe)
            changeRecipeStyle(holder, R.color.cardBackgroundLightColor, R.color.colorPrimary)

        }
    }
    private fun changeRecipeStyle(holder:MyViewHolder, backgroundColor:Int, strokeColor:Int){
        val itemView = holder.itemView
        itemView.favorite_recipe_row_layout.setBackgroundColor(ContextCompat.getColor(requireActivity, backgroundColor))
        itemView.favorite_row_card_view.strokeColor = ContextCompat.getColor(requireActivity, strokeColor)
    }

    override fun onCreateActionMode(actionMode: ActionMode?, menu: Menu?): Boolean {
        actionMode?.menuInflater?.inflate(R.menu.favorites_contextual_menu, menu)
        applyStatusBarColor(R.color.contextualStatusBarColor)
        return true
    }

    override fun onPrepareActionMode(actionMode: ActionMode?, menu: Menu?): Boolean {
        return true
    }

    override fun onActionItemClicked(actionMode: ActionMode?, menu: MenuItem?): Boolean {
        return true

    }

    override fun onDestroyActionMode(actionMode: ActionMode?) {

       myViewHolders.forEach { holder ->
           changeRecipeStyle(holder, R.color.cardBackgroundColor, R.color.strokeColor)
       }
        multiSelection = false
        selectedRecipes.clear()
        applyStatusBarColor(R.color.statusBarColor)
    }

}