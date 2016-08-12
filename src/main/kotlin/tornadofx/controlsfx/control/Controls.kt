import impl.org.controlsfx.table.ColumnFilter
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import org.controlsfx.control.table.TableFilter

fun <T> TableView<T>.applyTableFilter(lazy: Boolean = false): TableFilter<T>? {

    val tableFilter = TableFilter.forTableView(this)
            .lazy(lazy)
            .apply()

    this.properties.put("TableFilter",tableFilter)

    return tableFilter
}

@Suppress("UNCHECKED_CAST")
val <T> TableView<T>.tableFilter: TableFilter<T> get() =  (properties["TableFilter"] as TableFilter<T>)

@Suppress("UNCHECKED_CAST")
val <T,C> TableColumn<T,C>.columnFilter: ColumnFilter<T, C> get() =
(tableView.properties["TableFilter"] as TableFilter<T>).getColumnFilter(this)
        .orElseThrow { Exception("TableFilter not initialized! call ") } as ColumnFilter<T, C>

fun <T,C> TableColumn<T,C>.columnfilter(op: ColumnFilter<T,C>.() -> Unit) {
    columnFilter.op()
}