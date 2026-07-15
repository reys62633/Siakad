package com.example.siakad.network

import com.example.siakad.model.Post
import com.example.siakad.model.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("posts")
    fun getAllPosts(): Call<List<Post>>

    @GET("posts/{id}")
    fun getPostById(@Path("id") id: Int): Call<Post>

    @GET("users")
    fun getAllUsers(): Call<List<User>>

    @GET("users/{id}")
    fun getUserById(@Path("id") id: Int): Call<User>
}
