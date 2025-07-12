import java.awt.Dimension
import java.awt.Toolkit
import androidx.compose.foundation.border
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import server.CommentServer
import string.StringProvider
import ui.RootScreen
import usecase.GetCommentUseCase

@Composable
fun MilkyWayApp(windowState: WindowState, getCommentUseCase: GetCommentUseCase, windowY: Int) {
    MaterialTheme(colors = MaterialTheme.colors.copy(background = Color.Transparent)) {
        RootScreen(windowState, getCommentUseCase, windowY, modifier = Modifier.border(0.5.dp, Color.Gray))
    }
}

fun main() = application {
    val commentServer = CommentServer()
    commentServer.start()
    val getCommentUseCase = GetCommentUseCase(commentServer)

    val screenSize: Dimension = Toolkit.getDefaultToolkit().screenSize
    val screenHeight = screenSize.height
    val screenWidth = screenSize.width

    val windowHeight = screenHeight / 2
    val windowWidth = screenWidth

    val windowX = 0
    val windowY = screenHeight / 2

    val state = rememberWindowState(width = windowWidth.dp, height = windowHeight.dp, position = androidx.compose.ui.window.WindowPosition(windowX.dp, windowY.dp))
    Window(
        onCloseRequest = ::exitApplication,
        title = StringProvider.appName,
        state = state,
        undecorated = true,
        transparent = true,
        focusable = false,
        alwaysOnTop = true
    ) {
        MilkyWayApp(state, getCommentUseCase, windowY)
    }
}
