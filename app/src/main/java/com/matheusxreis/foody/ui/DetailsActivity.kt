package com.matheusxreis.foody.ui

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.navArgs
import com.google.android.material.tabs.TabLayoutMediator
import com.matheusxreis.foody.R
import com.matheusxreis.foody.adapters.PagerAdapter
import com.matheusxreis.foody.ui.fragments.ingredients.IngredientsFragment
import com.matheusxreis.foody.ui.fragments.instructions.InstructionsFragment
import com.matheusxreis.foody.ui.fragments.overview.OverviewFragment
import com.matheusxreis.foody.utils.Constants
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity() {

    private val args by navArgs<DetailsActivityArgs>()

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
        TabLayoutMediator(tab_layout, view_pager) {
            tab, position -> tab.text = titles[position]
        }.attach()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId === android.R.id.home){
            finish()
        }
        return super.onOptionsItemSelected(item)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.details_menu, menu)

        var drawable = menu?.findItem(R.id.save_to_favorite_menu)?.icon
        if(drawable != null){
          drawable = DrawableCompat.wrap(drawable)
            DrawableCompat.setTint(drawable, ContextCompat.getColor(this, R.color.white))
            menu?.findItem(R.id.save_to_favorite_menu)?.setIcon(drawable)
        }

        return true
    }
}