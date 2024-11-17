package com.example.assiette_pto.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.util.Log
import com.example.assiette_pto.api_parameters.ApiClient
import com.example.assiette_pto.responses.Category
import com.example.assiette_pto.responses.CategoryResponse

class DashboardViewModel : ViewModel() {

    // MutableLiveData for categories
    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> = _categories

    // MutableLiveData for loading state or error messages
    private val _text = MutableLiveData<String>().apply {
        value = "Loading categories..."
    }
    val text: LiveData<String> = _text

    init {
        fetchCategories()
    }

    // Fetch categories from API
    private fun fetchCategories() {
        val call = ApiClient.mealApi.getCategories()

        call.enqueue(object : Callback<CategoryResponse> {
            override fun onResponse(call: Call<CategoryResponse>, response: Response<CategoryResponse>) {
                if (response.isSuccessful) {
                    _categories.value = response.body()?.categories ?: emptyList()
                    _text.value = ""
                } else {
                    _text.value = "Failed to load categories."
                    Log.e("API Error", "Response failed: ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<CategoryResponse>, t: Throwable) {
                _text.value = "Error: ${t.message}"
                Log.e("API Error", "Network call failed: ${t.message}")
            }
        })
    }
}
