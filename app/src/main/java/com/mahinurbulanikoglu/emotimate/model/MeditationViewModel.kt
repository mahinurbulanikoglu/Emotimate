package com.mahinurbulanikoglu.emotimate.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mahinurbulanikoglu.emotimate.model.ContentItem

class MeditationViewModel : ViewModel() {
    private val _selectedMeditation = MutableLiveData<ContentItem>()
    val selectedMeditation: LiveData<ContentItem> = _selectedMeditation

    fun selectMeditation(item: ContentItem) {
        _selectedMeditation.value = item
    }
}
