import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import string.StringProvider
import ui.RootScreen

import androidx.compose.ui.window.WindowState

@Composable
fun MilkyWayApp(windowState: WindowState) {
    MaterialTheme(colors = MaterialTheme.colors.copy(background = Color.Transparent)) {
        RootScreen(windowState)
    }
}

fun main() = application {
    val state = rememberWindowState(placement = WindowPlacement.Maximized)
    Window(
        onCloseRequest = ::exitApplication,
        title = StringProvider.appName,
        state = state,
        undecorated = true,
        transparent = true,
        focusable = false,
        alwaysOnTop = true
    ) {
        MilkyWayApp(state)
    }
}
