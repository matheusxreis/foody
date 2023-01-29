package com.matheusxreis.foody.ui.fragments.ingredients

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.matheusxreis.foody.R
import com.matheusxreis.foody.adapters.IngredientsAdapter
import com.matheusxreis.foody.models.Result
import com.matheusxreis.foody.utils.Constants
import kotlinx.android.synthetic.main.fragment_ingredients.view.*


class IngredientsFragment : Fragment() {

    private val mAdapter:IngredientsAdapter by lazy {
        IngredientsAdapter()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val mView = inflater.inflate(R.layout.fragment_ingredients, container, false)

        val args = arguments
        val myBundle: Result? = args?.getParcelable(Constants.RECIPE_RESULT_KEY)

        setupRecyclerView(mView)
        Log.d("adapter1", myBundle?.extendedIngredients.toString())

        myBundle?.extendedIngredients?.let {
            Log.d("adapter1", it.toString())

            mAdapter.setData(it)
        }

        return mView
    }

    fun setupRecyclerView(view: View){
        view.ingredients_rv.adapter = mAdapter
        view.ingredients_rv.layoutManager = LinearLayoutManager(requireContext())
    }

}