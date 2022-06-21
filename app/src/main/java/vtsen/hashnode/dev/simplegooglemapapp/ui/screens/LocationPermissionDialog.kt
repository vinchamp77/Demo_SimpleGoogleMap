package vtsen.hashnode.dev.simplegooglemapapp.ui.screens

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*


@Composable
fun LocationPermissionsDialog(
    onPermissionGranted: () -> Unit,
    onPermissionDenied: () -> Unit,
) {
    val requestLocationPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->

        if (isGranted) {
            onPermissionGranted()
        } else {
            onPermissionDenied()
        }
    }

    SideEffect {
        requestLocationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }
}

//@OptIn(ExperimentalPermissionsApi::class)
//@Composable
//fun LocationPermissionsDialog(
//    //requestLocationPermission: Boolean,
//    locationPermissionsState: MultiplePermissionsState,
//    onAllPermissionsGranted: () -> Unit,
//) {
//    //if(!requestLocationPermission) return
//
//
//     var requestLocationPermissionLauncher = rememberLauncherForActivityResult(
//        ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
//
//        if (isGranted) {
//            //checkDeviceLocationSettingsAndGetLocation()
//            onAllPermissionsGranted()
//        } else {
//            //viewModel.showSnackBarInt.value = R.string.no_location_permission_msg
//            onAllPermissionsGranted()
//        }
//    }
//
//
//    requestLocationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
//
////    if (locationPermissionsState.allPermissionsGranted) {
////        onAllPermissionsGranted()
////    } else {
//////        LaunchedEffect(key1 = requestLocationPermission) {
//////            locationPermissionsState.launchMultiplePermissionRequest()
//////        }
////
////        SideEffect {
////            locationPermissionsState.launchMultiplePermissionRequest()
////        }
////    }
//
////    if (locationPermissionsState.allPermissionsGranted) {
////        onAllPermissionsGranted()
////    } else {
////
////        val allPermissionsRevoked =
////            locationPermissionsState.permissions.size ==
////                    locationPermissionsState.revokedPermissions.size
////
////        if (!allPermissionsRevoked) {
////            SideEffect {
////                locationPermissionsState.launchMultiplePermissionRequest()
////            }
////        } else if (locationPermissionsState.shouldShowRationale) {
////            SideEffect {
////                locationPermissionsState.launchMultiplePermissionRequest()
////            }
////        } else {
////            SideEffect {
////                locationPermissionsState.launchMultiplePermissionRequest()
////            }
////        }
////    }
//}