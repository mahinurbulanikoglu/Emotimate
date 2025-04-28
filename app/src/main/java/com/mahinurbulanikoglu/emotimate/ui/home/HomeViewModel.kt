package com.mahinurbulanikoglu.emotimate.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mahinurbulanikoglu.emotimate.model.Book
import com.mahinurbulanikoglu.emotimate.model.Movie
import com.mahinurbulanikoglu.emotimate.network.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

    private val _books = MutableLiveData<List<Book>>()
    val books: LiveData<List<Book>> = _books

    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> = _movies

    fun fetchBooks() {
        val isbnList = listOf(
            "ISBN:6059692478", // seninle başlamadı
            "ISBN:9759893746", // hayatı yeniden keşfedin
            "ISBN:6056950484"  // iyi hisssetmek
        )

        val bibkeys = isbnList.joinToString(",") // Virgülle birleştiriyoruz

        //APİ çağrısı yapılıyor
        RetrofitInstance.apiService.getBookInfo(bibkeys)
            .enqueue(object : Callback<Map<String, Book>> {
                override fun onResponse(call: Call<Map<String, Book>>, response: Response<Map<String, Book>>) {
                    if (response.isSuccessful) {
                        Log.d("HomeViewModel", "Books fetched: ${response.body()}")
                        //Gelen veriden Book listesini çıkartıyoruz
                        val bookList = response.body()?.values?.toList() ?: emptyList()
                        _books.value = bookList
                    }
                }

                override fun onFailure(call: Call<Map<String, Book>>, t: Throwable) {
                    Log.e("HomeViewModel", "API çağrısı başarısız: ${t.message}")
                }
            })
    }

    fun fetchMovies() {
        RetrofitInstance.apiService.getMovies().enqueue(object : Callback<List<Movie>> {
            override fun onResponse(call: Call<List<Movie>>, response: Response<List<Movie>>) {
                if (response.isSuccessful) {
                    _movies.value = response.body()
                }
            }

            override fun onFailure(call: Call<List<Movie>>, t: Throwable) {
                // Hata durumunda yapılacak işlemler
            }
        })
    }
}