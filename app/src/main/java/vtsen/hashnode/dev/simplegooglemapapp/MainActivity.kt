package vtsen.hashnode.dev.simplegooglemapapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.rememberCameraPositionState

import vtsen.hashnode.dev.simplegooglemapapp.ui.theme.NewEmptyComposeAppTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }
}

@Composable
fun MainScreen(useSystemUIController: Boolean = true) {
    NewEmptyComposeAppTheme(useSystemUIController = useSystemUIController) {
        //Text(text = stringResource(id = R.string.hello_android))
        MapScreen()
    }
}

@Composable
fun MapScreen() {
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(44.837789, -0.57918), 12f)
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
    )
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MainScreen(useSystemUIController = false)
}