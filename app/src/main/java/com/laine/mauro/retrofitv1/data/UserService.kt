package com.laine.mauro.retrofitv1.data

import com.laine.mauro.retrofitv1.model.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UserService {

    @GET("/users/{username}")
    fun getUser(@Path("username") userName: String): Call<User>

    @GET("/users")
    fun getUsers(@Query("per_page") perPage: Int, @Query("page") page: Int): Call<List<User>>

}