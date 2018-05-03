package ui.paginatedTable

import com.intellij.ui.components.JBScrollPane
import java.awt.BorderLayout
import java.awt.Component
import java.awt.GridLayout
import java.awt.Insets
import javax.swing.*
import javax.swing.event.TableModelEvent
import javax.swing.event.TableModelListener


class PaginatedTableDecorator<T> private constructor(private val container: JPanel,
                                                     private val table: JTable, private val dataProvider: PaginationDataProvider<T>,
                                                     private var currentPageSize: Int,
                                                     private val onProjectSelected : (page : Int, row : Int) -> Unit) {
    private var currentPage = 1
    private lateinit var pageLinkPanel: JPanel
    private var objectTableModel: ObjectTableModel<T>? = null

    private fun init() {
        initDataModel()
        initPaginationComponents()
        initListeners()
        paginate()
    }

    private fun initListeners() {
        objectTableModel!!.addTableModelListener(TableModelListener { this.refreshPageButtonPanel(it) })
    }

    private fun initDataModel() {
        val model = table.model as? ObjectTableModel<T>
                ?: throw IllegalArgumentException("TableModel must be a subclass of ObjectTableModel")
        objectTableModel = model
    }

    private fun initPaginationComponents() {
        val paginationPanel = createPaginationPanel()
        container.layout = BorderLayout()
        container.add(paginationPanel, BorderLayout.SOUTH)
        container.add(JBScrollPane(table))
    }

    private fun createPaginationPanel(): JPanel {
        val paginationPanel = JPanel()
        pageLinkPanel = JPanel(GridLayout(1, MaxPagingCompToShow, 3, 3))
        paginationPanel.add(pageLinkPanel)
        return paginationPanel
    }

    private fun refreshPageButtonPanel(tme: TableModelEvent) {
        pageLinkPanel.removeAll()
        val totalRows = dataProvider.totalRowCount
        val pages = Math.ceil(totalRows.toDouble() / currentPageSize).toInt()
        val buttonGroup = ButtonGroup()
        if (pages > MaxPagingCompToShow) {
            addPageButton(pageLinkPanel, buttonGroup, 1)
            if (currentPage > pages - (MaxPagingCompToShow + 1) / 2) {
                //case: 1 ... n->lastPage
                pageLinkPanel.add(createEllipsesComponent())
                addPageButtonRange(pageLinkPanel, buttonGroup, pages - MaxPagingCompToShow + 3, pages)
            } else if (currentPage <= (MaxPagingCompToShow + 1) / 2) {
                //case: 1->n ...lastPage
                addPageButtonRange(pageLinkPanel, buttonGroup, 2, MaxPagingCompToShow - 2)
                pageLinkPanel.add(createEllipsesComponent())
                addPageButton(pageLinkPanel, buttonGroup, pages)
            } else {//case: 1 .. x->n .. lastPage
                pageLinkPanel.add(createEllipsesComponent())//first ellipses
                //currentPage is approx mid point among total max-4 center links
                val start = currentPage - (MaxPagingCompToShow - 4) / 2
                val end = start + MaxPagingCompToShow - 5
                addPageButtonRange(pageLinkPanel, buttonGroup, start, end)
                pageLinkPanel.add(createEllipsesComponent())//last ellipsis
                addPageButton(pageLinkPanel, buttonGroup, pages)//last page link
            }
        } else {
            addPageButtonRange(pageLinkPanel, buttonGroup, 1, pages)
        }
        addSelectionButton()
        pageLinkPanel!!.parent.validate()
        pageLinkPanel!!.parent.repaint()
    }

    private fun addSelectionButton() {
        val selectProjectBtn = JToggleButton("Go")
        selectProjectBtn.addActionListener {
            onProjectSelected.invoke(currentPage, table.selectedRow)
        }
        pageLinkPanel.add(selectProjectBtn)
    }

    private fun createEllipsesComponent(): Component {
        return JLabel(Ellipses, SwingConstants.CENTER)
    }

    private fun addPageButtonRange(parentPanel: JPanel, buttonGroup: ButtonGroup, start: Int, end: Int) {
        var start = start
        while (start <= end) {
            addPageButton(parentPanel, buttonGroup, start)
            start++
        }
    }

    private fun addPageButton(parentPanel: JPanel, buttonGroup: ButtonGroup, pageNumber: Int) {
        val toggleButton = JToggleButton(Integer.toString(pageNumber))
        toggleButton.margin = Insets(1, 3, 1, 3)
        buttonGroup.add(toggleButton)
        parentPanel.add(toggleButton)
        if (pageNumber == currentPage) {
            toggleButton.isSelected = true
        }
        toggleButton.addActionListener { ae ->
            currentPage = Integer.parseInt(ae.actionCommand)
            paginate()
        }
    }

    private fun paginate() {
        val startIndex = (currentPage - 1) * currentPageSize
        var endIndex = startIndex + currentPageSize
        if (endIndex > dataProvider.totalRowCount) {
            endIndex = dataProvider.totalRowCount
        }
        val rows = dataProvider.getRows(startIndex, endIndex)
        objectTableModel!!.objectRows = rows
        objectTableModel!!.fireTableDataChanged()
    }

    companion object {
        private val MaxPagingCompToShow = 9
        private val Ellipses = "..."

        fun <T> decorate(container: JPanel,
                         table: JTable,
                         dataProvider: PaginationDataProvider<T>,
                         defaultPageSize: Int, onProjectSelected : (page : Int, row : Int) -> Unit): PaginatedTableDecorator<T> {
            val decorator = PaginatedTableDecorator(container, table, dataProvider,
                    defaultPageSize, onProjectSelected)
            decorator.init()
            return decorator
        }
    }
}