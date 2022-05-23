package vtsen.hashnode.dev.simplegooglemapapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import vtsen.hashnode.dev.simplegooglemapapp.ui.screens.MapScreen

import vtsen.hashnode.dev.simplegooglemapapp.ui.theme.SimpleGoogleMapAppTheme


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
    SimpleGoogleMapAppTheme(useSystemUIController = useSystemUIController) {
        MapScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MainScreen(useSystemUIController = false)
}