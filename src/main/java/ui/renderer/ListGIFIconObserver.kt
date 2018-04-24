package ui.renderer

import java.awt.Image
import java.awt.image.ImageObserver.ALLBITS
import java.awt.image.ImageObserver.FRAMEBITS
import javax.swing.JList
import java.awt.image.ImageObserver


internal class ListGIFIconObserver(var list: JList<*>, var index: Int) : ImageObserver {

    override fun imageUpdate(img: Image, infoflags: Int, x: Int, y: Int, width: Int, height: Int): Boolean {
        if (infoflags and (FRAMEBITS or ALLBITS) != 0) {
            val rect = list.getCellBounds(index, index)
            list.repaint(rect)
        }

        return infoflags and (ALLBITS or ImageObserver.ABORT) == 0
    }
}