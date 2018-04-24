package ui.renderer

import models.expandedresult.Result
import util.IconUtil
import java.awt.Component
import javax.swing.ImageIcon
import javax.swing.JLabel
import javax.swing.JTable
import javax.swing.table.DefaultTableCellRenderer

class BuildTableBuildColumnRenderer(private val result : Result) : DefaultTableCellRenderer() {

    override fun getTableCellRendererComponent(table: JTable?, value: Any?, isSelected: Boolean, hasFocus: Boolean, row: Int, column: Int): Component? {
        val comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column) as JLabel
        val icon = IconUtil.getIcon(result.results.result!![row].lifeCycleState, result.results.result!![row].buildState) as ImageIcon
        if (icon.description != null && icon.description.endsWith("gif")) {
            icon.imageObserver = TableGIFIconObserver(table!!, row, column)
        }
        comp.icon = icon
        comp.text = "#" + result.results.result!![row].buildNumber.toString()
        horizontalTextPosition = JLabel.RIGHT
        return comp
    }

}