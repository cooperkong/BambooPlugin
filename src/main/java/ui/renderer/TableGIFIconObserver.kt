package ui.renderer

import java.awt.Image
import java.awt.image.ImageObserver
import java.awt.image.ImageObserver.ALLBITS
import java.awt.image.ImageObserver.FRAMEBITS
import javax.swing.JTable


internal class TableGIFIconObserver(var list: JTable, var row: Int, var column : Int) : ImageObserver {

    override fun imageUpdate(img: Image, infoflags: Int, x: Int, y: Int, width: Int, height: Int): Boolean {
        if (infoflags and (FRAMEBITS or ALLBITS) != 0) {
            val rect = list.getCellRect(row, column, false)
            list.repaint(rect)
        }

        return infoflags and (ALLBITS or ImageObserver.ABORT) == 0
    }
}