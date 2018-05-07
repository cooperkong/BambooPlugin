package ui.renderer

import presenter.IconPresenter
import java.awt.Color
import java.awt.Component
import javax.swing.DefaultListCellRenderer
import javax.swing.ImageIcon
import javax.swing.JLabel
import javax.swing.JList

class ListItemWithIconRender(private val presenter: IconPresenter) : DefaultListCellRenderer() {

    override fun getListCellRendererComponent(
            list: JList<*>, value: Any?, index: Int,
            isSelected: Boolean, cellHasFocus: Boolean): Component {

        val label = super.getListCellRendererComponent(
                list, value, index, isSelected, cellHasFocus) as JLabel
        val icon = presenter.getIcon(index) as ImageIcon
        if (icon.description != null && icon.description.endsWith("gif")) {
            icon.imageObserver = ListGIFIconObserver(list, index)
        }
        label.icon = icon
        label.horizontalTextPosition = JLabel.RIGHT
        label.foreground = Color(51, 51, 51)
        if (index % 2 == 0) {
            label.background = Color(245, 245, 245)
        } else {
            label.background = Color(255, 255, 255)
        }
        return label
    }
}