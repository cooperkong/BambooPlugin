package ui.paginatedTable

import java.util.ArrayList
import javax.swing.table.AbstractTableModel


abstract class ObjectTableModel<T> : AbstractTableModel() {
    var objectRows: List<T> = ArrayList()

    override fun getRowCount(): Int {
        return objectRows.size
    }

    override fun getValueAt(rowIndex: Int, columnIndex: Int): Any? {
        val t = objectRows[rowIndex]
        return getValueAt(t, columnIndex)
    }

    override fun getColumnClass(columnIndex: Int): Class<*> {
        if (objectRows.isEmpty()) {
            return Any::class.java
        }
        val valueAt = getValueAt(0, columnIndex)
        return valueAt?.javaClass ?: Any::class.java
    }

    abstract fun getValueAt(t: T, columnIndex: Int): Any

    abstract override fun getColumnName(column: Int): String
}