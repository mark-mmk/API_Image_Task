package com.example.api_image_task

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.api_image_task.API.ImageResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var recycle: RecyclerView
    lateinit var adapter: CustomAdapter
    lateinit var loading: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recycle = findViewById(R.id.rec)
        loading = findViewById(R.id.loading)
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.INTERNET)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.INTERNET),
                1
            )
        } else {
            val connectivityManager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connectivityManager.activeNetworkInfo

            if (networkInfo != null && networkInfo.isConnected) {
                // Internet connection is available
                Toast.makeText(this@MainActivity, "Network is Connected", Toast.LENGTH_LONG).show()

            } else {
                // Internet connection is not available
                Toast.makeText(this@MainActivity, "Network is Not Connected", Toast.LENGTH_LONG)
                    .show()
            }
        }

        RetrofitClient().getRetrofitClient().getPhotos().enqueue(object : Callback<ImageResponse> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call<ImageResponse>, response: Response<ImageResponse>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    adapter = CustomAdapter(responseBody!!)
                    recycle.layoutManager = StaggeredGridLayoutManager(3, RecyclerView.VERTICAL)
                    recycle.adapter = adapter
                    loading.visibility = View.GONE
                    adapter.notifyDataSetChanged()
                } else {
                    loading.isVisible = true
                }
            }

            override fun onFailure(call: Call<ImageResponse>, t: Throwable) {
                Toast.makeText(this@MainActivity, "failed", Toast.LENGTH_LONG).show()
                loading.isVisible = true
            }

        })

    }
}
