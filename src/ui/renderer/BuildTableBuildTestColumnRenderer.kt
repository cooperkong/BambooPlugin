package ui.renderer

import models.NO_TEST_FOUND
import models.expandedresult.Result
import java.awt.Component
import javax.swing.JLabel
import javax.swing.JTable
import javax.swing.table.DefaultTableCellRenderer

class BuildTableBuildTestColumnRenderer(private val result : Result) : DefaultTableCellRenderer() {

    override fun getTableCellRendererComponent(table: JTable?, value: Any?, isSelected: Boolean, hasFocus: Boolean, row: Int, column: Int): Component? {
        val comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column) as JLabel
        var testSummary = result.results.result!![row].buildTestSummary
        if (testSummary == null || testSummary == NO_TEST_FOUND ) {
              comp.text = "Testless build"
        } else {
            comp.text = "<html><font color='#3572b0'>$testSummary</font></html>"
        }
        return comp
    }

}