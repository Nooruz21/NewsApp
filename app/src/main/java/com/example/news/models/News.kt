package com.example.news.models

import java.io.Serializable

data class News(
    val title:String,
    val createAt:Long
):Serializable
