package ui.paginatedTable

interface PaginationDataProvider<T> {
    val totalRowCount: Int
    fun getRows(startIndex: Int, endIndex: Int): List<T>
}