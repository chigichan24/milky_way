import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import string.StringProvider
import ui.RootScreen

@Composable
fun App() {
    MaterialTheme {
        RootScreen()
    }
}

fun main() = application {
    val state = rememberWindowState(placement = WindowPlacement.Fullscreen)
    Window(onCloseRequest = ::exitApplication, title = StringProvider.appName, state = state) {
        App()
    }
}
