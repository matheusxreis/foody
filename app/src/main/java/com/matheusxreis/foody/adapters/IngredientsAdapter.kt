package com.matheusxreis.foody.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.matheusxreis.foody.R
import com.matheusxreis.foody.models.ExtendedIngredient
import com.matheusxreis.foody.utils.Constants
import com.matheusxreis.foody.utils.RecipesDiffUtil
import kotlinx.android.synthetic.main.ingredients_row_layout.view.*

class IngredientsAdapter: RecyclerView.Adapter<IngredientsAdapter.MyViewHolder>() {

    private var ingredientsList = emptyList<ExtendedIngredient>()

    class MyViewHolder(itemView:View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
       return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.ingredients_row_layout, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentIngredient = ingredientsList[position]
        val itemView = holder.itemView
        itemView.ingredient_image_view.load(Constants.BASE_IMAGE_URL+currentIngredient.image) {
            crossfade(600)
            error(R.drawable.ic_placeholder_error )
        }
        itemView.ingredient_title_tv.text = currentIngredient.name.capitalize()
        itemView.ingredient_amount_tv.text = currentIngredient.amount.toString()
        itemView.ingredient_unit_tv.text = currentIngredient.unit
        itemView.ingredient_consistency_tv.text = currentIngredient.consistency
        itemView.ingredient_original_tv.text = currentIngredient.original
    }

    override fun getItemCount(): Int = ingredientsList.size

    fun setData(newIngredients:List<ExtendedIngredient>){
        val ingredientsDiffUtil = RecipesDiffUtil(
            oldList = ingredientsList,
            newList = newIngredients
        )
        val diffUtilResult = DiffUtil.calculateDiff(ingredientsDiffUtil)
        ingredientsList = newIngredients
        diffUtilResult.dispatchUpdatesTo(this)
    }
}