package ui

import models.expandedresult.Result
import ui.renderer.BuildTableBuildColumnRenderer
import ui.renderer.BuildTableBuildReasonColumnRenderer
import ui.renderer.BuildTableBuildTestColumnRenderer
import java.awt.Desktop
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.net.URL
import javax.swing.JTable
import javax.swing.table.AbstractTableModel

class BuildDetailTable(private val table: JTable, val buildResult : Result) {

    fun update () {
        if (buildResult.results.result!!.isEmpty()) {
            return
        }
        table.tableHeader.isVisible = false
        table.setShowGrid(false)
        table.rowHeight = 50
        table.model = object : AbstractTableModel() {
            override fun getRowCount(): Int = buildResult.results.result.size

            override fun getColumnCount(): Int = 4

            override fun getValueAt(rowIndex: Int, columnIndex: Int) : Any {
                val result = buildResult.results.result[rowIndex]
                when (columnIndex) {
                    0 -> return result.buildNumber
                    1 -> return result.buildReason
                    2 -> return result.buildRelativeTime
                    3 -> return result.buildTestSummary
                }
                return ""
            }
        }
        table.autoResizeMode = JTable.AUTO_RESIZE_LAST_COLUMN
        table.columnModel.getColumn(0).maxWidth = 75
        table.columnModel.getColumn(0).cellRenderer = BuildTableBuildColumnRenderer(buildResult)
        table.columnModel.getColumn(1).cellRenderer = BuildTableBuildReasonColumnRenderer(buildResult)
        table.columnModel.getColumn(3).cellRenderer = BuildTableBuildTestColumnRenderer(buildResult)

        table.mouseListeners
                .filter { mouseListener -> mouseListener.toString() == "buildtable_mouseAdapter" }
                .map { table.removeMouseListener(it) }

        // attach listener on test result column to open test results in browser.
        table.addMouseListener(object : MouseAdapter(){
            override fun mousePressed(e: MouseEvent) {
                val buildResult = buildResult.results.result[table.rowAtPoint(e.point)]
                if (Desktop.isDesktopSupported() &&
                        table.columnAtPoint(e.point) == 3 &&
                        (buildResult.buildTestSummary.endsWith("passed") ||
                        buildResult.buildTestSummary.endsWith("failed"))) {
                    Desktop.getDesktop().browse(URL("https://bamboo.cd.auspost.com.au/bamboo/browse/"
                            + buildResult.buildResultKey + "/test").toURI())
                }
            }
            override fun toString(): String = "buildtable_mouseAdapter"
        })


        val comp2 = table.prepareRenderer(table.getCellRenderer(0, 1), 0, 1)
        table.rowHeight = Math.max(table.rowHeight, comp2.preferredSize.height)
    }

}