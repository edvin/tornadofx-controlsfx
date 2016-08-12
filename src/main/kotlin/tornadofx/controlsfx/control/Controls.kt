import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import org.controlsfx.control.table.TableFilter

fun <T> TableView<T>.applyFilterControl(lazy: Boolean = false): TableFilter<T>? {

    val tableFilter = TableFilter.forTableView(this)
            .lazy(lazy)
            .apply()

    this.properties.put("TableFilter",tableFilter)

    return tableFilter
}


fun <T,C> TableColumn<T, C>.selectFilterValue(value: Any?) {
    (tableView.properties["TableFilter"] as TableFilter<*>).selectValue(this,value)
}

fun <T,C> TableColumn<T, C>.selectAllFilterValues() {
    (tableView.properties["TableFilter"] as TableFilter<*>).selectAllValues(this)
}

fun <T,C> TableColumn<T, C>.unselectFilterValue(value: Any?) {
    (tableView.properties["TableFilter"] as TableFilter<*>).unselectValue(this,value)
}

fun <T,C> TableColumn<T, C>.unSelectAllFilterValues() {
    (tableView.properties["TableFilter"] as TableFilter<*>).unSelectAllValues(this)
}