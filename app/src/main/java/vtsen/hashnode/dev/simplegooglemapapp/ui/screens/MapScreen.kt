package vtsen.hashnode.dev.simplegooglemapapp.ui.screens

import android.Manifest
import android.annotation.SuppressLint
import android.location.Location
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.*
import vtsen.hashnode.dev.simplegooglemapapp.R
import vtsen.hashnode.dev.simplegooglemapapp.ui.getDefaultLocation
import vtsen.hashnode.dev.simplegooglemapapp.ui.getPosition
import vtsen.hashnode.dev.simplegooglemapapp.ui.requestLocationResultCallback

@OptIn(ExperimentalPermissionsApi::class)
@SuppressLint("MissingPermission")
@Composable
fun MapScreen(fusedLocationProviderClient: FusedLocationProviderClient) {

    var currentLocation by remember { mutableStateOf(getDefaultLocation()) }

    val cameraPositionState = rememberCameraPositionState()
    cameraPositionState.position = CameraPosition.fromLatLngZoom(
        getPosition(currentLocation), 12f)

    var requestLocationPermission by remember { mutableStateOf(true)}
    val locationPermissionsState = rememberMultiplePermissionsState(
        listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
        )
    )

    MyGoogleMap(currentLocation, cameraPositionState, onGpsIconClick = {
        requestLocationPermission = true
    })

    DebugView(cameraPositionState)

    RequestLocationPermissions(
        requestLocationPermission,
        locationPermissionsState,

        onAllPermissionsGranted = {
            requestLocationPermission = false

            requestLocationResultCallback(fusedLocationProviderClient) {
                currentLocation = it.lastLocation
            }
        },
    )
}

@Composable
private fun MyGoogleMap(
    currentLocation: Location,
    cameraPositionState: CameraPositionState,
    onGpsIconClick: () -> Unit) {

    val mapUiSettings by remember {
        mutableStateOf(
            MapUiSettings(zoomControlsEnabled = false)
        )
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        uiSettings = mapUiSettings,
    ) {
        Marker(
            state = MarkerState(position = getPosition(currentLocation)),
            title = "Current Position"
        )
    }

    GpsIconButton(onIconClick = onGpsIconClick)

}

@Composable
private fun GpsIconButton(onIconClick: () -> Unit) {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {

            IconButton(onClick = onIconClick) {
                Icon(
                    modifier = Modifier.padding(bottom = 100.dp, end = 20.dp),
                    painter = painterResource(id = R.drawable.ic_gps_fixed),
                    contentDescription = null
                )
            }
        }
    }
}

@Composable
private fun DebugView(
    cameraPositionState: CameraPositionState,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
    ) {
        val moving =
            if (cameraPositionState.isMoving) "moving" else "not moving"
        Text(
            text = "Camera is $moving",
            fontWeight = FontWeight.Bold,
            color = Color.DarkGray)
        Text(
            text = "Camera position is ${cameraPositionState.position}",
            fontWeight = FontWeight.Bold,
            color = Color.DarkGray)
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun RequestLocationPermissions(
    requestLocationPermission: Boolean,
    locationPermissionsState: MultiplePermissionsState,
    onAllPermissionsGranted: () -> Unit,
) {
    if(!requestLocationPermission) return

    if (locationPermissionsState.allPermissionsGranted) {
        onAllPermissionsGranted()
    } else {
        LaunchedEffect(key1 = Unit) {
            locationPermissionsState.launchMultiplePermissionRequest()
        }
    }
}

