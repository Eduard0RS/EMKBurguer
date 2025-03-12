package com.example.emkburguer.data.local

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "user_prefs")

class DataStoreManager(private val context: Context) {

    companion object {
        private val REMEMBER_ME_KEY = booleanPreferencesKey("remember_me")
        private val EMAIL_KEY = stringPreferencesKey("email")
        private val PASSWORD_KEY = stringPreferencesKey("password")
    }

    // Função para salvar o estado do "Lembrar-me"
    suspend fun saveRememberMeState(value: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[REMEMBER_ME_KEY] = value
        }
    }

    // Função para recuperar o estado do "Lembrar-me"
    val rememberMeState: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[REMEMBER_ME_KEY] ?: false
        }

    suspend fun saveCredentials(email: String, password: String) {
        context.dataStore.edit { preferences ->
            preferences[EMAIL_KEY] = email
            preferences[PASSWORD_KEY] = password
        }
    }

    val getCredentials: Flow<Pair<String, String>> = context.dataStore.data
        .map { preferences ->
            val email = preferences[EMAIL_KEY] ?: ""
            val password = preferences[PASSWORD_KEY] ?: ""
            Pair(email, password)
        }

    suspend fun clearCredentials() {
        context.dataStore.edit { preferences ->
            preferences[EMAIL_KEY] = ""
            preferences[PASSWORD_KEY] = ""
        }
    }
}
