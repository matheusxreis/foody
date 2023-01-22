package com.matheusxreis.foody.utils

import androidx.recyclerview.widget.DiffUtil
import com.matheusxreis.foody.models.Result

class RecipesDiffUtil(
    private val oldList: List<Result>,
    private val newList: List<Result>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    // called to decide wheter two object represent the same item
    // in the old and new list
    // because of this, we must use 3 equals sinals (===)
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] === newList[newItemPosition]
    }

    // called to check wheter two items have the same data
    // because of this, we must use 2 equals sinals (==)
    // it must be called just if areItemsTheSame returns true
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}