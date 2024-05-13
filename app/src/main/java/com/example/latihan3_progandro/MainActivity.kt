package com.example.latihan3_progandro

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var cityTextView: TextView
    private lateinit var descriptionTextView: TextView
    private lateinit var temperatureTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cityTextView = findViewById(R.id.cityTextView)
        descriptionTextView = findViewById(R.id.descriptionTextView)
        temperatureTextView = findViewById(R.id.temperatureTextView)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val weatherApi = retrofit.create(WeatherApi::class.java)
        val call = weatherApi.getCurrentWeatherData("1621177", "21170e7aa8b5d1f4d933c343f82a3ef4")

        call.enqueue(object : Callback<Weather> {
            override fun onResponse(call: Call<Weather>, response: Response<Weather>) {
                if (response.isSuccessful) {
                    val weather = response.body()
                    if (weather != null) {
                        cityTextView.text = weather.name
                        descriptionTextView.text = weather.weather[0].description
                        temperatureTextView.text = weather.main.temp.toString()
                        showToast("Success to fetch weather data")

                    }
                } else {
                    showToast("Failed to fetch weather data")
                }
            }

            override fun onFailure(call: Call<Weather>, t: Throwable) {
                showToast("Network error: ${t.message}")
            }
        })
    }
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
