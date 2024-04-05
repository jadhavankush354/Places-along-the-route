package com.example.goolemaptestapi.service

import android.content.Context
import android.util.Log
import com.example.goolemaptestapi.R
import com.example.goolemaptestapi.nearbyplaces.NearByPlaces
import com.example.goolemaptestapi.nearbyplaces.Place
import com.example.goolemaptestapi.tomtomplacesalongroute.TomTomPlacesAlongRoute
import com.google.android.gms.maps.model.LatLng
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query


suspend fun fetchPlacesAlongRoot(points: List<LatLng>, context: Context): List<LatLng> {
    val apiKey = "oVpKzM0jnWoCDvhArQWAdwGPzKZKgdF2"
    val maxDetourTime = 2000
    val limit = 20

    val body = JSONObject().apply {
        val pointsArray = JSONArray()
        points.forEach { point ->
            val pointObject = JSONObject().apply {
                put("lat", point.latitude)
                put("lon", point.longitude)
            }
            pointsArray.put(pointObject)
        }
        put("route", JSONObject().apply {
            put("points", pointsArray)
        })
    }

    val requestBody = body.toString().toRequestBody("application/json".toMediaType())
    val tomTomApiService = TomTomApiService.getInstance()
    val response = tomTomApiService.searchAlongRoute("application/gzip", "9ac68072-c7a4-11e8-a8d5-f2801f1b9fd1", apiKey, maxDetourTime, limit, requestBody)
    return if (response.isSuccessful) {
        response.body()?.results?.map {
            LatLng(it.position.lat, it.position.lon)
        } ?: emptyList()
    } else {
        emptyList()
    }
}

interface TomTomApiService {
    @POST("/search/2/searchAlongRoute/restaurant.json")
    suspend fun searchAlongRoute(
        @Header("Accept-Encoding") acceptEncoding: String,
        @Header("Tracking-ID") trackingId: String,
        @Query("key") apiKey: String,
        @Query("maxDetourTime") maxDetourTime: Int,
        @Query("limit") limit: Int,
        @Body body: RequestBody
    ): Response<TomTomPlacesAlongRoute>

    companion object {
        private var tomTomApiService: TomTomApiService? = null

        fun getInstance(): TomTomApiService {
            if (tomTomApiService == null) {
                tomTomApiService = Retrofit.Builder()
                    .baseUrl("https://api.tomtom.com")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(TomTomApiService::class.java)
            }
            return tomTomApiService!!
        }
    }
}