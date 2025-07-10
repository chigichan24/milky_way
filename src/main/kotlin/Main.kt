import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import string.StringProvider
import ui.RootScreen

@Composable
fun App() {
    MaterialTheme {
        RootScreen()
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = StringProvider.appName) {
        App()
    }
}
