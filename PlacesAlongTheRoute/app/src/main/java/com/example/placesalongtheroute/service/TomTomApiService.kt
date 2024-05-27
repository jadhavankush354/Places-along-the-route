package com.example.placesalongtheroute.service

import com.example.placesalongtheroute.R
import com.example.placesalongtheroute.entityClasses.tomtomplacesalongroute.TomTomPlacesAlongRoute
import com.example.placesalongtheroute.models.ViewModel
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


suspend fun fetchPlacesAlongRoot(points: List<LatLng>, viewModel: ViewModel): List<LatLng> {
    val apiKey = viewModel.context.getString(R.string.tom_tom_key)
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