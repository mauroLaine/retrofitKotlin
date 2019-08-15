package com.laine.mauro.retrofitv1.data

import com.laine.mauro.retrofitv1.model.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface UserInterface {

    @GET("")
    fun getUser(): Call<User>

    @GET("/users")
    fun getUsers(@Query("per_page") per_page: Int, @Query("page") page: Int): Call<List<User>>

}