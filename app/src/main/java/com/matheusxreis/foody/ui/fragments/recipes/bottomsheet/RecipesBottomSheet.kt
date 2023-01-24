package com.matheusxreis.foody.ui.fragments.recipes.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.matheusxreis.foody.R
import com.matheusxreis.foody.utils.Constants
import com.matheusxreis.foody.viewmodels.RecipesViewModel
import kotlinx.android.synthetic.main.recipes_bottom_sheet.view.*


class RecipesBottomSheet : BottomSheetDialogFragment() {

    private lateinit var recipesViewModel: RecipesViewModel

    private var mealTypeChip = Constants.DEFAULT_MEAL_TYPE
    private var mealTypeChipId = 0
    private var dietTypeChip = Constants.DEFAULT_DIET_TYPE
    private var dietTypeChipId = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recipesViewModel = ViewModelProvider(requireActivity()).get(RecipesViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val mView = inflater.inflate(R.layout.recipes_bottom_sheet, container, false)


        recipesViewModel.readMealAndDietType.asLiveData().observe(viewLifecycleOwner) {
            value ->
            mealTypeChip = value.selectedMealType
            dietTypeChip = value.selectedDietType

            updateChip(value.selectedMealTypeId, mView.chip_group_meal_type)
            updateChip(value.selectedDietTypeId, mView.chip_group_diet_type)

        }
        mView.chip_group_meal_type.setOnCheckedChangeListener() {
            group, selectedChipId ->
            val chip =  group.findViewById<Chip>(selectedChipId)
            val selectedMealType = chip.text.toString().lowercase()

            mealTypeChip = selectedMealType
            mealTypeChipId = selectedChipId
        }
        mView.chip_group_diet_type.setOnCheckedChangeListener() {
                group, selectedChipId ->
            val chip =  group.findViewById<Chip>(selectedChipId)
            val selectedDietType = chip.text.toString().lowercase()

            dietTypeChip = selectedDietType
            dietTypeChipId = selectedChipId
        }

        mView.apply_button.setOnClickListener {
            recipesViewModel.saveMealAndDietType(
                mealType = mealTypeChip,
                mealTypeId = mealTypeChipId,
                dietType = dietTypeChip,
                dietTypeId = dietTypeChipId
                )
        }
        return mView
    }

    private fun updateChip(chipId: Int, chipGroup: ChipGroup) {
        if(chipId != 0){
            try {
                chipGroup.findViewById<Chip>(chipId).isChecked = true
            }catch (err:Exception){

            }
        }
    }


}