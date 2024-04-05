package com.example.goolemaptestapi.service

import com.example.goolemaptestapi.herenearby.HereNearBy
import com.example.goolemaptestapi.herenearby.Item

import android.content.Context
import com.example.goolemaptestapi.R
import com.google.android.gms.maps.model.LatLng
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

suspend fun fetchNearbyPlacesFromHere(place: LatLng, context: Context): Set<LatLng> {
    val apiKey = context.getString(R.string.here_api_key)
    val at = "${place.latitude},${place.longitude}"
    val q = "restaurant"

    val hereApiService = HereApiService.getInstance()
    val response = hereApiService.getNearByUsingHere(at, q, apiKey)

    return if (response.isSuccessful) {
        response.body()?.items?.map { item ->
            LatLng(item.position.lat, item.position.lng)
        }?.toSet() ?: emptySet()
    } else {
        emptySet()
    }
}

interface HereApiService
{
    @GET("/v1/discover")
    suspend fun getNearByUsingHere(
        @Query("at") at: String,
        @Query("q") place: String,
        @Query("apiKey") apiKey: String
    ): Response<HereNearBy>

    companion object {
        var hereApiService: HereApiService? = null
        fun getInstance() : HereApiService {
            if (hereApiService == null) {
                hereApiService = Retrofit.Builder().baseUrl("https://discover.search.hereapi.com").addConverterFactory(
                GsonConverterFactory.create()).build().create(HereApiService::class.java)
            }
            return hereApiService!!
        }
    }
}


