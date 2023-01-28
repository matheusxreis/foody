package com.matheusxreis.foody.ui.fragments.overview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import coil.load
import com.matheusxreis.foody.R
import com.matheusxreis.foody.models.Result
import kotlinx.android.synthetic.main.fragment_overview.view.*

class OverviewFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val mView = inflater.inflate(R.layout.fragment_overview, container, false)


        val args = arguments
        val myBundle: Result? = args?.getParcelable("recipeBundle")


        mView.image_view_food.load(myBundle?.image)
        mView.title_tv.text = myBundle?.title
        mView.likes_tv.text = myBundle?.aggregateLikes.toString()
        mView.time_tv.text = myBundle?.readyInMinutes.toString()
        mView.summary_tv.text = myBundle?.summary

        setColorCategories(myBundle?.vegetarian, mView.vegetarian_image_view, mView.vegetarian_tv)
        setColorCategories(myBundle?.vegan, mView.vegan_image_view, mView.vegan_tv)
        setColorCategories(myBundle?.dairyFree, mView.dairy_free_image_view, mView.dairy_free_tv)
        setColorCategories(myBundle?.glutenFree, mView.gluten_free_image_view, mView.gluten_free_tv)
        setColorCategories(myBundle?.veryHealthy, mView.healthy_image_view, mView.healty_tv)
        setColorCategories(myBundle?.cheap, mView.cheap_image_view, mView.cheap_tv)


        return mView
    }

    fun setColorCategories(
        condition: Boolean?,
        imageView: ImageView,
        textView: TextView
        ){
        val greenColor = ContextCompat.getColor(requireContext(), R.color.green)

        if(condition == true){
            imageView.setColorFilter(greenColor)
           textView.setTextColor(greenColor)
        }
    }

}