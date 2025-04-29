package com.mahinurbulanikoglu.emotimate.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Article(
    val id: String,
    val title: String,
    val authors: List<String>?,
    val abstract: String?,
    val journal: String?,
    val publicationDate: String?,
    val doi: String?
) : Parcelable

data class ArticleResponse(
    val resultList: ResultList
)

data class ResultList(
    val result: List<Article>
)