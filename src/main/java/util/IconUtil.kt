package util

import com.intellij.ui.Gray
import jiconfont.icons.FontAwesome
import jiconfont.swing.IconFontSwing
import models.*
import java.awt.Color
import javax.swing.ImageIcon

class IconUtil {

    companion object {
        fun getIcon(lifeCycleState: String, buildStatus: String) = when (lifeCycleState) {
            (FINISHED) -> when (buildStatus.toLowerCase()) {
                SUCCESSFUL -> IconFontSwing.buildIcon(FontAwesome.CHECK_CIRCLE, 16F, Color(20, 137, 44))
                UNKNOWN -> IconFontSwing.buildIcon(FontAwesome.MINUS_CIRCLE, 16F, Gray._112)
                else -> IconFontSwing.buildIcon(FontAwesome.EXCLAMATION_CIRCLE, 16F, Color(208, 68, 55))
            }
            (NOT_BUILT) -> IconFontSwing.buildIcon(FontAwesome.MINUS_CIRCLE, 16F, Gray._112)
            (QUEUED) -> IconFontSwing.buildIcon(FontAwesome.CLOCK_O, 16F, Color(53, 114, 176))
            (IN_PROGRESS) -> ImageIcon(this::class.java.getResource("/icons/building.gif"))
            else -> IconFontSwing.buildIcon(FontAwesome.MINUS_CIRCLE, 16F, Gray._112)
        }
    }

}