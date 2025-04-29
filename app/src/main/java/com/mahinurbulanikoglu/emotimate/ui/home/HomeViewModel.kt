package com.mahinurbulanikoglu.emotimate.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mahinurbulanikoglu.emotimate.model.Article
import com.mahinurbulanikoglu.emotimate.model.ArticleResponse
import com.mahinurbulanikoglu.emotimate.model.Book
import com.mahinurbulanikoglu.emotimate.model.Movie
import com.mahinurbulanikoglu.emotimate.model.MovieResponse
import com.mahinurbulanikoglu.emotimate.network.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

    private val _books = MutableLiveData<List<Book>>()
    val books: LiveData<List<Book>> = _books

    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> = _movies

    private val _articles = MutableLiveData<List<Article>>()
    val articles: LiveData<List<Article>> = _articles

    private val TMDB_API_KEY = "07c763c609b7b92f0f8c7577d53c5581"

    fun fetchBooks() {
        val isbnList = listOf(
            "ISBN:6059692478", // seninle başlamadı
            "ISBN:9759893746", // hayatı yeniden keşfedin
            "ISBN:6056950484"  // iyi hisssetmek
        )

        val bibkeys = isbnList.joinToString(",")

        RetrofitInstance.apiService.getBookInfo(bibkeys)
            .enqueue(object : Callback<Map<String, Book>> {
                override fun onResponse(call: Call<Map<String, Book>>, response: Response<Map<String, Book>>) {
                    if (response.isSuccessful) {
                        Log.d("HomeViewModel", "Books fetched: ${response.body()}")
                        val bookList = response.body()?.values?.toList() ?: emptyList()
                        _books.value = bookList
                    }
                }

                override fun onFailure(call: Call<Map<String, Book>>, t: Throwable) {
                    Log.e("HomeViewModel", "API çağrısı başarısız: ${t.message}")
                }
            })
    }
    fun fetchArticles() {
        RetrofitInstance.europePmcApiService.searchArticles("cognitive behavioral therapy")
            .enqueue(object : Callback<ArticleResponse> {
                override fun onResponse(call: Call<ArticleResponse>, response: Response<ArticleResponse>) {
                    if (response.isSuccessful) {
                        Log.d("HomeViewModel", "Articles fetched: ${response.body()}")
                        val articleList = response.body()?.resultList?.result ?: emptyList()
                        _articles.value = articleList
                    }
                }

                override fun onFailure(call: Call<ArticleResponse>, t: Throwable) {
                    Log.e("HomeViewModel", "Article API çağrısı başarısız: ${t.message}")
                }
            })
    }


    fun fetchMovies() {
        val movieTitles = listOf("Soul", "Little Miss Sunshine")
        val allMovies = mutableListOf<Movie>()

        movieTitles.forEach { title ->
            RetrofitInstance.tmdbApiService.searchMovies(TMDB_API_KEY, title)
                .enqueue(object : Callback<MovieResponse> {
                    override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                        if (response.isSuccessful) {
                            val movies = response.body()?.results?.firstOrNull()
                            movies?.let { allMovies.add(it) }
                            if (allMovies.size == movieTitles.size) {
                                _movies.value = allMovies
                            }
                        }
                    }

                    override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                        Log.e("HomeViewModel", "Movie API call failed: ${t.message}")
                    }
                })
        }
    }
}