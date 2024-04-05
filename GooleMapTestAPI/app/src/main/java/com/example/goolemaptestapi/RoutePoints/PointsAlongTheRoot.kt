package com.example.goolemaptestapi.RoutePoints
import com.google.android.gms.maps.model.LatLng
import kotlin.math.*

// Function to calculate points along the route at specific distances
fun calculatePointsAlongRoute(routePoints: List<LatLng>, intervalDistance: Double): List<LatLng> {
    val pointsAlongRoute = mutableListOf<LatLng>()
    var distance = 0.0

    for (i in 0 until routePoints.size - 1) {
        val startPoint = routePoints[i]
        val endPoint = routePoints[i + 1]
        val segmentDistance = calculateDistance(startPoint, endPoint)

        // Check if the segment can accommodate the interval distance
        if (distance + segmentDistance >= intervalDistance) {
            val remainingDistance = intervalDistance - distance
            val fraction = remainingDistance / segmentDistance

            // Calculate intermediate point
            val intermediatePoint = LatLng(
                startPoint.latitude + fraction * (endPoint.latitude - startPoint.latitude),
                startPoint.longitude + fraction * (endPoint.longitude - startPoint.longitude)
            )
            pointsAlongRoute.add(intermediatePoint)

            // Reset distance
            distance = 0.0
        } else {
            // Increment distance
            distance += segmentDistance
        }
    }

    return pointsAlongRoute
}

// Function to calculate distance between two LatLng points using Haversine formula
fun calculateDistance(start: LatLng, end: LatLng): Double {
    val R = 6371 // Radius of the Earth in kilometers
    val lat1 = start.latitude
    val lon1 = start.longitude
    val lat2 = end.latitude
    val lon2 = end.longitude

    val dLat = Math.toRadians(lat2 - lat1)
    val dLon = Math.toRadians(lon2 - lon1)
    val a = sin(dLat / 2) * sin(dLat / 2) +
            cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2)) *
            sin(dLon / 2) * sin(dLon / 2)
    val c = 2 * atan2(sqrt(a), sqrt(1 - a))
    val distance = R * c

    return distance
}

// Example usage
val routePoints: List<LatLng> = getDummyRoutePoints()
val intervalDistance = 5.0 // Interval distance in kilometers
val pointsAlongRoute = calculatePointsAlongRoute(routePoints, intervalDistance)

fun getDummyRoutePoints(): List<LatLng> {
        return listOf(
            LatLng(12.9246141, 77.6480627),
            LatLng(12.9236783, 77.6552473),
            LatLng(12.967072, 77.702038),
            LatLng(12.9598609, 77.70141579999999),
            LatLng(12.9570048, 77.70163099999999),
            LatLng(12.956106, 77.7134719),
            LatLng(12.9558924, 77.7164964),
            LatLng(12.9562444, 77.7310662),
            LatLng(12.9635767, 77.7331281),
            LatLng(12.9697396, 77.7320898),
            LatLng(12.969807, 77.73203819999999),
            LatLng(12.9698769, 77.73147589999999)
        )
}
