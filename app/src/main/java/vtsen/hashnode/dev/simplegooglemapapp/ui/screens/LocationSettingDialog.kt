package vtsen.hashnode.dev.simplegooglemapapp.ui.screens

import android.app.Activity
import android.content.Context
import android.content.IntentSender
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest

@Composable
fun LocationSettingDialog(
    onSuccess: () -> Unit,
    onFailure: () -> Unit,
) {

    val context: Context = LocalContext.current

    val enableLocationSettingLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult()
    ) { activityResult ->
        if (activityResult.resultCode == Activity.RESULT_OK)
            onSuccess()

        else {
            onFailure()
        }
    }

    val locationRequest = LocationRequest.create().apply {
        priority = LocationRequest.PRIORITY_LOW_POWER
    }
    val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
    val settingsClient = LocationServices.getSettingsClient(context)
    val locationSettingsResponseTask = settingsClient.checkLocationSettings(builder.build())

    locationSettingsResponseTask.addOnSuccessListener {
        onSuccess()
    }

    locationSettingsResponseTask.addOnFailureListener { exception ->
        if (exception is ResolvableApiException){
            try {
                val intentSenderRequest = IntentSenderRequest.Builder(exception.resolution).build()
                enableLocationSettingLauncher.launch(intentSenderRequest)

            } catch (sendEx: IntentSender.SendIntentException) {
                sendEx.printStackTrace()
            }
        } else {
            onFailure()
        }
    }
}