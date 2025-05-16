package com.mahinurbulanikoglu.emotimate.utils

import com.mahinurbulanikoglu.emotimate.BuildConfig

object ApiKeyManager {
    fun getApiKey(): String {
        return BuildConfig.API_KEY
    }
}