package com.example.studyroom.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("user_pref")

class UserPrefRepository(context: Context) {
    private val dataStore = context.dataStore
    private val THEME_MODE = intPreferencesKey("theme_mode")
    private val AUTO_CACHE = booleanPreferencesKey("auto_clear_cache")

    val getThemeMode: Flow<Int> = dataStore.data.map { it[THEME_MODE] ?: 0 }
    suspend fun saveThemeMode(mode: Int) {
        dataStore.edit { it[THEME_MODE] = mode }
    }

    val getAutoCacheSwitch: Flow<Boolean> = dataStore.data.map { it[AUTO_CACHE] ?: false }
    suspend fun saveAutoCacheSwitch(flag: Boolean) {
        dataStore.edit { it[AUTO_CACHE] = flag }
    }

    suspend fun clearAllPref() {
        dataStore.edit { it.clear() }
    }
}