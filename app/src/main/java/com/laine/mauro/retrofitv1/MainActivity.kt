package com.laine.mauro.retrofitv1

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.laine.mauro.retrofitv1.data.UserService
import com.laine.mauro.retrofitv1.model.User
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Response
import java.lang.Exception
import android.os.StrictMode
import com.laine.mauro.retrofitv1.data.GitHubServiceGenerator
import retrofit2.Call
import retrofit2.Callback


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        load_button.setOnClickListener {
            getData()
        }
    }

    fun getData() {
        val userService: UserService = GitHubServiceGenerator.createService(UserService::class.java)
        val callSync = userService.getUser(MY_USER_NAME)

//        synchronousCall(callSync)
        asynchronousCall(callSync)
    }

    fun synchronousCall(callSync: Call<User>) {
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        try {
            val response: Response<User> = callSync.execute()
            val user = response.body()
            user?.let {
                Toast.makeText(this, it.blog, Toast.LENGTH_SHORT).show()
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    fun asynchronousCall(callSync: Call<User>) {
        callSync.enqueue(object : Callback<User> {
            override fun onFailure(call: Call<User>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<User>, response: Response<User>) {
                val user = response.body()
                user?.let {
                    Toast.makeText(applicationContext, it.blog, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    companion object {
        val MY_USER_NAME = "mauroLaine"
    }
}
