package ui.listener

import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent

internal abstract class MouseListener : java.awt.event.MouseListener, MouseAdapter() {
    override fun mouseClicked(e: MouseEvent) {}

    override fun mousePressed(e: MouseEvent) {}

    override fun mouseReleased(e: MouseEvent) {}

    override fun mouseEntered(e: MouseEvent) {}

    override fun mouseExited(e: MouseEvent) {}
}
