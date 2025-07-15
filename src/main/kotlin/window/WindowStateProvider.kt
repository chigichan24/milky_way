package window

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.rememberWindowState
import java.awt.Toolkit

object WindowStateProvider {
    private val screenSize = Toolkit.getDefaultToolkit().screenSize
    private val screenHeight = screenSize.height
    private val screenWidth = screenSize.width

    @Composable
    fun createDefaultWindowState(): WindowState {
        val config = createDefaultWindowConfig()
        return rememberWindowState(
            width = config.width.dp,
            height = config.height.dp,
            position = WindowPosition(config.x.dp, config.y.dp),
        )
    }

    fun createDefaultWindowConfig(): WindowConfig {
        val windowHeight = screenHeight / 2
        val windowWidth = screenWidth
        val windowX = 0
        val windowY = screenHeight / 2

        return WindowConfig(
            width = windowWidth,
            height = windowHeight,
            x = windowX,
            y = windowY,
            alwaysOnTop = true,
        )
    }
}
