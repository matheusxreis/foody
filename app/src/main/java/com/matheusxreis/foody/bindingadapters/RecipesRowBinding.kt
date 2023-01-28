package com.matheusxreis.foody.bindingadapters

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import coil.load
import com.matheusxreis.foody.R
import com.matheusxreis.foody.models.Result
import com.matheusxreis.foody.ui.fragments.recipes.RecipesFragmentDirections
import org.jsoup.Jsoup

class RecipesRowBinding {

    companion object {

        @BindingAdapter("onRecipeClickListener")
        @JvmStatic
        fun onRecipeClickListener(recipeRowLayout:ConstraintLayout, result:Result){
            recipeRowLayout.setOnClickListener {
                try {
                    val action = RecipesFragmentDirections.actionRecipesFragmentToDetailsActivity(
                        result = result
                    )
                    recipeRowLayout.findNavController().navigate(action)
                }catch(exception:Exception){
                    Log.d("onRecipeClickListener", exception.toString())
                }
            }
        }

        @BindingAdapter("loadImageFromUrl")
        @JvmStatic
        fun loadImageFromUrl(
            imageView: ImageView,
            imageUrl:String
        ){
            imageView.load(imageUrl) {
                crossfade(600)
                error(R.drawable.ic_placeholder_error)
            }
        }
        @BindingAdapter("setNumberOfLikes")
        @JvmStatic
        fun setNumberOfLikes(
            textView: TextView,
            likes: Int
        ) {
            textView.text = likes.toString()
        }

        @BindingAdapter("setNumberOfMinutes")
        @JvmStatic
        fun setNumberOfMinutes(
            textView: TextView,
            minutes:Int
        ){
            textView.text = minutes.toString()
        }

        @BindingAdapter("applyVeganColor")
        @JvmStatic
        fun applyVeganColor(
            view:View,
            vegan:Boolean
        ){
            if(vegan){
                when(view){
                    is TextView -> {
                        view.setTextColor(
                            ContextCompat.getColor(
                                view.context,
                                R.color.green
                            )
                        )
                    }
                    is ImageView -> {
                        view.setColorFilter(
                            ContextCompat.getColor(
                                view.context,
                                R.color.green
                            )
                        )
                    }
                }
            }
        }


        @BindingAdapter("parseHtml")
        @JvmStatic
        fun parseHtml(textView: TextView, description:String?) {
            if(description != null){
                val desc = Jsoup.parse(description).text()
                textView.text = desc
            }
        }
    }

}