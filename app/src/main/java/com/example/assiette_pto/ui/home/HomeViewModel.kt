package com.example.assiette_pto.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Feeling Hungry? Search For A Meal:"
    }
    val text: LiveData<String> = _text
}
