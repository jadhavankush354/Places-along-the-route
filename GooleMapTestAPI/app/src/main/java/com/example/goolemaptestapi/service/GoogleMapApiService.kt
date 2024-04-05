package com.example.goolemaptestapi.service

import android.content.Context
import android.util.Log
import com.example.goolemaptestapi.R
import com.example.goolemaptestapi.RoutePoints.routePoints
import com.example.goolemaptestapi.direction.DirectionApi
import com.example.goolemaptestapi.nearbyplaces.NearByPlaces
import com.example.goolemaptestapi.nearbyplaces.Place
import com.google.android.gms.maps.model.LatLng
import com.google.maps.DirectionsApi
import com.google.maps.model.DirectionsResult
import com.google.maps.model.EncodedPolyline
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

suspend fun fetchNearbyPlaces(place: LatLng, context: Context): List<Place> {
    val apiKey = context.getString(R.string.google_maps_api_key)
    val fieldMask = "*"
    val contentType = "application/json"

    val body = JSONObject().apply {
        put("includedTypes", "restaurant")
        put("maxResultCount", 20)
        put("locationRestriction", JSONObject().apply {
            put("circle", JSONObject().apply {
                put("center", JSONObject().apply {
                    put("latitude", place.latitude)
                    put("longitude", place.longitude)
                })
                put("radius", 1000)
            })
        })
    }

    val requestBody = body.toString().toRequestBody(contentType.toMediaType())
    val googleMapApiService = GoogleMapApiService.getInstance()
    val response = googleMapApiService.searchNearbyPlaces(contentType, apiKey, fieldMask, requestBody)

    return if (response.isSuccessful) {
        response.body()?.places ?: emptyList()
    } else {
        emptyList()
    }
}

suspend fun fetchDirection(origin: String, destination: String, context: Context): List<LatLng> {
    val apiKey = context.getString(R.string.google_maps_api_key)
    val googleMapApiService = GoogleMapApiService.getInstance()
    val routePoints = mutableListOf<LatLng>()
    try {
        val response = googleMapApiService.getRoutingPoints(origin, destination, apiKey)
        if (response.isSuccessful) {
            val directionResponse: DirectionsResult? = response.body()
            directionResponse?.routes?.forEach { route ->
                route.legs.forEach { leg ->
                    leg.steps.forEach { step ->
                        val points = step.polyline.decodePath()
                        routePoints.addAll(points.map { LatLng(it.lat, it.lng) })
                    }
                }
            }
        } else {
            Log.e("debug", "API request failed: ${response.code()}")
        }
    } catch (e: HttpException) {
        Log.e("debug", "HTTP Exception: ${e.message()}")
    } catch (e: Throwable) {
        Log.e("debug", "Error: ${e.message}")
    }
    return routePoints
}


interface GoogleMapApiService
{
    @POST("/v1/places:searchNearby")
    suspend fun searchNearbyPlaces(
        @Header("Content-Type") contentType: String,
        @Header("X-Goog-Api-Key") apiKey: String,
        @Header("X-Goog-FieldMask") fieldMask: String,
        @Body body: RequestBody
    ): Response<NearByPlaces>

    @GET("/maps/api/directions/json")
    suspend fun getRoutingPoints(
        @Query("origin") origin: String,
        @Query("destination") destination: String,
        @Query("key") apiKey: String
    ): Response<DirectionsResult>

    companion object {
        var googleMapApiService: GoogleMapApiService? = null
        fun getInstance() : GoogleMapApiService {
            if (googleMapApiService == null) {
                googleMapApiService = Retrofit.Builder().baseUrl("https://maps.googleapis.com").addConverterFactory(
//                googleMapApiService = Retrofit.Builder().baseUrl("https://places.googleapis.com").addConverterFactory(
                    GsonConverterFactory.create()).build().create(GoogleMapApiService::class.java)
            }
            return googleMapApiService!!
        }
    }
}


