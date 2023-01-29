package com.matheusxreis.foody.ui

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.navArgs
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import com.matheusxreis.foody.R
import com.matheusxreis.foody.adapters.PagerAdapter
import com.matheusxreis.foody.data.database.entities.FavoritesEntity
import com.matheusxreis.foody.ui.fragments.ingredients.IngredientsFragment
import com.matheusxreis.foody.ui.fragments.instructions.InstructionsFragment
import com.matheusxreis.foody.ui.fragments.overview.OverviewFragment
import com.matheusxreis.foody.utils.Constants
import com.matheusxreis.foody.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_details.*

@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {

    private val args by navArgs<DetailsActivityArgs>()
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        setSupportActionBar(toolbar)
        toolbar.setTitleTextColor(
            ContextCompat.getColor(this, R.color.white)
        )
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        val fragments = ArrayList<Fragment>()
        fragments.add(OverviewFragment())
        fragments.add(IngredientsFragment())
        fragments.add(InstructionsFragment())

        val titles = ArrayList<String>()
        titles.add("Overview")
        titles.add("Ingredients")
        titles.add("Instructions")

        val resultBundle = Bundle()
        resultBundle.putParcelable(Constants.RECIPE_RESULT_KEY, args.result)

        val adapter = PagerAdapter(
            resultBundle = resultBundle,
            fragments = fragments,
            titles = titles,
            activity = this
        )

        view_pager.adapter = adapter
        // the tab layout is just to view fragments title
        // who is responsable by slide between the fragments is the view pager2
        TabLayoutMediator(tab_layout, view_pager) { tab, position ->
            tab.text = titles[position]
        }.attach()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId === android.R.id.home) {
            //coming back
            finish()
        } else if (item.itemId === R.id.save_to_favorite_menu) {
            saveToFavorites(item)
        }
        return super.onOptionsItemSelected(item)

    }

    private fun saveToFavorites(item: MenuItem) {
        val favoriteEntity = FavoritesEntity(
            0,
            args.result
        )
        mainViewModel.insertFavoriteRecipe(favoriteEntity)

        changeMenuItemColor(item, R.color.yellow)
        showSnackBar("Recipe saved.")
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(
            details_layout,
            message,
            Snackbar.LENGTH_SHORT
        ).setAction("Okay") {}
            .show()
    }

    private fun changeMenuItemColor(item: MenuItem, color: Int) {
        item.icon?.setTint(
            ContextCompat.getColor(
                this, color
            )
        )
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.details_menu, menu)
        val menuItem = menu?.findItem(R.id.save_to_favorite_menu)
        checkSavedRecipes(menuItem!!)
        return true
    }

    private fun checkSavedRecipes(menuItem: MenuItem) {
        mainViewModel.readFavoriteRecipe.observe(this) {
            favoritesEntity ->
            try {
                for(savedRecipe in favoritesEntity){
                    if(savedRecipe.result.id == args.result.id){
                        changeMenuItemColor(menuItem, R.color.yellow)
                    }else {
                        changeMenuItemColor(menuItem, R.color.white)
                    }
                }
            }catch(e:Exception){
                Log.d("DetailsActivity", e.message.toString())
            }
        }
    }
}