package ui.renderer

import models.expandedresult.Result
import java.awt.Component
import javax.swing.JLabel
import javax.swing.JTable
import javax.swing.SwingConstants
import javax.swing.table.DefaultTableCellRenderer

class BuildTableBuildReasonColumnRenderer(private val result : Result) : DefaultTableCellRenderer() {

    override fun getTableCellRendererComponent(table: JTable, value: Any?, isSelected: Boolean, hasFocus: Boolean, row: Int, column: Int): Component? {
        val comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column) as JLabel
        comp.text = "<html>" + result.results.result!![row].buildReason + "</html>"
        return comp
    }

}