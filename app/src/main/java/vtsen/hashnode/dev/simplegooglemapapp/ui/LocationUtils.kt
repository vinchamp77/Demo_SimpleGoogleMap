package vtsen.hashnode.dev.simplegooglemapapp.ui

import android.annotation.SuppressLint
import android.location.Location
import android.location.LocationManager
import android.os.Looper
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.model.LatLng

fun getDefaultLocation() : Location {
    val location = Location(LocationManager.GPS_PROVIDER)
    val sydney = LatLng(-33.865143, 151.209900)
    location.latitude = sydney.latitude
    location.longitude = sydney.longitude
    return location
}

fun getPosition(location: Location) : LatLng {
    return LatLng(
        location.latitude,
        location.longitude)
}

@SuppressLint("MissingPermission")
fun requestLocationResultCallback(
    fusedLocationProviderClient: FusedLocationProviderClient,
    locationResultCallback: (LocationResult) -> Unit
) {

    val locationCallback = object : LocationCallback() {

        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)

            locationResult.let {
                locationResultCallback(it)
                fusedLocationProviderClient.removeLocationUpdates(this)
            }
        }
    }

    val locationRequest = LocationRequest.create()
    locationRequest.interval = 0
    locationRequest.fastestInterval = 0
    locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    Looper.myLooper()?.let {
        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest, locationCallback,
            it
        )
    }
}