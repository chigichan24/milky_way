import androidx.compose.foundation.border
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import di.AppContainer
import string.StringProvider
import ui.RootScreen
import window.WindowStateProvider

@Composable
fun MilkyWayApp(
    windowState: WindowState,
    appContainer: AppContainer,
    windowY: Int,
) {
    MaterialTheme(colors = MaterialTheme.colors.copy(background = Color.Transparent)) {
        RootScreen(windowState, appContainer.getCommentUseCase, windowY, modifier = Modifier.border(0.5.dp, Color.Gray))
    }
}

fun main() {
    val appContainer = AppContainer()

    application {
        val windowConfig = WindowStateProvider.createDefaultWindowConfig()
        val state = WindowStateProvider.createDefaultWindowState()

        Window(
            onCloseRequest = ::exitApplication,
            title = StringProvider.APP_NAME,
            state = state,
            undecorated = true,
            transparent = true,
            focusable = false,
            alwaysOnTop = windowConfig.alwaysOnTop,
        ) {
            MilkyWayApp(state, appContainer, windowConfig.y)
        }
    }
}
