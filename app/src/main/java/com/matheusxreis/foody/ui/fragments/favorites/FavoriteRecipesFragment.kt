package com.matheusxreis.foody.ui.fragments.favorites

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.matheusxreis.foody.R
import com.matheusxreis.foody.adapters.FavoriteRecipesAdapter
import com.matheusxreis.foody.databinding.FragmentFavoriteRecipesBinding
import com.matheusxreis.foody.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_favorite_recipes.view.*

@AndroidEntryPoint
class FavoriteRecipesFragment : Fragment(), MenuProvider {

    private val mainViewModel:MainViewModel by viewModels()
    private val mAdapter:FavoriteRecipesAdapter by lazy { FavoriteRecipesAdapter(requireActivity(), mainViewModel) }

    private var _binding: FragmentFavoriteRecipesBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        requireActivity().addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        _binding = FragmentFavoriteRecipesBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.mainViewModel = mainViewModel
        binding.mAdapter = mAdapter

        setupRecyclerView(binding.favoriteRv)

        return binding.root
    }


    override fun onDestroy() {
        super.onDestroy()
        mAdapter.clearContextualActionMode()
        _binding = null
    }

    private fun setupRecyclerView(recyclerView: RecyclerView){
        recyclerView.adapter = mAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.favorite_recipes_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
      if(menuItem.itemId == R.id.delete_all_favorite_recipes_menu){
          mainViewModel.deleteAllFavoriteRecipes()
          when(mainViewModel.readFavoriteRecipe.value.isNullOrEmpty()){
              true -> showSnackBar("Nothing to remove")
              else -> showSnackBar("All recipes removed")
          }

      }
    return true
    }

    private fun showSnackBar(message:String){
        Snackbar.make(
            binding.root,
            message,
            Snackbar.LENGTH_SHORT
        ).setAction("Okay"){}
            .show()
    }


}