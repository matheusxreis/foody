package com.matheusxreis.foody.bindingadapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.matheusxreis.foody.adapters.FavoriteRecipesAdapter
import com.matheusxreis.foody.data.database.entities.FavoritesEntity
import org.w3c.dom.Text

class FavoriteRecipesBinding {

    companion object {

        @BindingAdapter(
            "viewVisibility", // first attribute is equal first param
            "setData",       // second attribute is equal second param
            requireAll = false
        )
        @JvmStatic
        fun setDataAndViewVisibility(view: View,
                                     favoritesEntity: List<FavoritesEntity>?,
                                     mAdapter:FavoriteRecipesAdapter?){
            if(favoritesEntity.isNullOrEmpty()){
                when(view){
                    is ImageView -> {
                        view.visibility = View.VISIBLE
                    }
                    is TextView -> {
                        view.visibility = View.VISIBLE
                    }
                    is RecyclerView -> {
                        view.visibility = View.INVISIBLE
                    }
                }
            }else {
                when(view){
                    is ImageView -> {
                        view.visibility = View.INVISIBLE
                    }
                    is TextView -> {
                        view.visibility = View.INVISIBLE
                    }
                    is RecyclerView -> {
                        view.visibility = View.VISIBLE
                        mAdapter?.setData(favoritesEntity)
                    }
                }

            }

        }
    }
}