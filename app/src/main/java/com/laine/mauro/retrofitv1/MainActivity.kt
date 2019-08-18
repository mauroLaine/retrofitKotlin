package com.laine.mauro.retrofitv1

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.laine.mauro.retrofitv1.data.UserService
import com.laine.mauro.retrofitv1.model.User
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
import android.os.StrictMode
import retrofit2.Call


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        load_button.setOnClickListener {
            getData()
        }

    }

    fun getData() {
        val httpClient: OkHttpClient.Builder = OkHttpClient.Builder()
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build()

        val userService: UserService = retrofit.create(UserService::class.java)
        val callSync = userService.getUser(MY_USER_NAME)

        synchronousCall(callSync)
    }

    fun synchronousCall(callSync: Call<User>) {
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        try {
            val response: Response<User> = callSync.execute()
            val user = response.body()
            user?.let {
                Toast.makeText(this, user.blog, Toast.LENGTH_SHORT).show()
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    companion object {
        val API_URL = "https://api.github.com/"
        val MY_USER_NAME = "mauroLaine"
    }
}
