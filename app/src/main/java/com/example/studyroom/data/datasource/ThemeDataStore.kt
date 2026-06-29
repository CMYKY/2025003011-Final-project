package com.example.studyroom.data.datasource

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("theme_pref")
private val DARK_MODE_KEY = booleanPreferencesKey("dark_mode")

class ThemeDataStore(context: Context) {
    private val ds = context.dataStore

    val isDarkModeFlow: Flow<Boolean> = ds.data
        .map { pref -> pref[DARK_MODE_KEY] ?: false }

    suspend fun setDarkMode(enable: Boolean) {
        ds.edit { pref -> pref[DARK_MODE_KEY] = enable }
    }
}