package vtsen.hashnode.dev.simplegooglemapapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import vtsen.hashnode.dev.simplegooglemapapp.ui.screens.MapScreen
import vtsen.hashnode.dev.simplegooglemapapp.ui.theme.SimpleGoogleMapAppTheme

class MainActivity : ComponentActivity() {

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        setContent {
            MainScreen(fusedLocationProviderClient)
        }
    }
}

@Composable
fun MainScreen(
    fusedLocationProviderClient: FusedLocationProviderClient,
    useSystemUIController: Boolean = true) {

    SimpleGoogleMapAppTheme(useSystemUIController = useSystemUIController) {
        MapScreen(fusedLocationProviderClient)
    }
}



