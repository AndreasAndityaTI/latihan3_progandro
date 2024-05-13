package com.example.latihan3_progandro

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("weather")
    fun getCurrentWeatherData(
        @Query("id") location: String?,
        @Query("appid") apiKey: String?
    ): Call<Weather>
}
