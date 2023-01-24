package com.matheusxreis.foody.data

import android.content.Context
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.matheusxreis.foody.utils.Constants
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class DataStoreRepository @Inject constructor(@ApplicationContext private val context:Context){

    private object PreferenceKeys {
        val selectedMealType = stringPreferencesKey(Constants.PREFERENCES_MEAL_TYPE)
        val selectedMealTypeId = intPreferencesKey(Constants.PREFERENCES_MEAL_TYPE_ID)
        val selectedDietType = stringPreferencesKey(Constants.PREFERENCES_DIET_TYPE)
        val selectedDietTypeId = intPreferencesKey(Constants.PREFERENCES_DIET_TYPE_ID)
    }
}