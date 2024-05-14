package com.example.placesalongtheroute.service

import com.example.placesalongtheroute.R
import com.example.placesalongtheroute.entityClasses.herenearby.HereNearBy
import com.example.placesalongtheroute.entityClasses.herenearby.Item
import com.example.placesalongtheroute.models.ViewModel
import com.google.android.gms.maps.model.LatLng
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

suspend fun fetchNearbyPlacesFromHere(place: LatLng, viewModel: ViewModel): List<Item> {
    val apiKey = viewModel.getContext().getString(R.string.here_api_key)
    val at = "${place.latitude},${place.longitude}"

    val hereApiService = HereApiService.getInstance()
    val response = hereApiService.getNearByUsingHere(at, viewModel.placeType, apiKey)

    return if (response.isSuccessful) {
        response.body()?.items ?: emptyList()
    } else {
        emptyList()
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