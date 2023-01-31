package com.matheusxreis.foody.ui.fragments.foodjoke

import android.content.Intent
import android.graphics.ColorFilter
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.matheusxreis.foody.R
import com.matheusxreis.foody.databinding.FragmentFoodJokeBinding
import com.matheusxreis.foody.utils.Constants
import com.matheusxreis.foody.utils.NetworkResult
import com.matheusxreis.foody.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FoodJokeFragment : Fragment(), MenuProvider {

    private val mainViewModel by viewModels<MainViewModel>()
    // initialization from view model by delegate
    private var _binding: FragmentFoodJokeBinding? = null
    private val binding get() = _binding!!;

    private var foodJoke = "No food joke"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFoodJokeBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.mainViewModel = mainViewModel

        requireActivity().addMenuProvider(this, viewLifecycleOwner)

        mainViewModel.getFoodJoke(Constants.API_KEY)

        mainViewModel.foodJokeResponse.observe(viewLifecycleOwner){ response ->
            when(response){
                is NetworkResult.Success -> {
                    binding.foodJokeTv.text = response.data?.text
                    foodJoke = response.data?.text ?: "No food joke"
                }
                is NetworkResult.Error -> {
                    loadDataFromCache()
                    Toast.makeText(
                        requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is NetworkResult.Loading -> {

                }
            }
        }

        return binding.root
    }


    private fun loadDataFromCache(){
        lifecycleScope.launch {
            mainViewModel.readFoodJoke.observe(viewLifecycleOwner){
                    database ->
                if(database.isNotEmpty() && database !=null){
                    binding.foodJokeTv.text = database[0].foodJoke.text
                    foodJoke = database[0].foodJoke.text

                }
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.food_joke_menu, menu)

        val itemMenu = menu.findItem(R.id.share_food_joke_menu)
        itemMenu.icon?.setTint(ContextCompat.getColor(requireActivity(), R.color.white))
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {

        if(menuItem.itemId == R.id.share_food_joke_menu){
            val shareIntent = Intent().apply {
                this.action = Intent.ACTION_SEND
                this.putExtra(Intent.EXTRA_TEXT, foodJoke)
                this.type = "text/plain"
            }
            startActivity(shareIntent)
        }
        return true
    }
}