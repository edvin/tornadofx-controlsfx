
import impl.org.controlsfx.table.ColumnFilter
import javafx.event.EventTarget
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import org.controlsfx.control.table.TableFilter
import org.controlsfx.glyphfont.FontAwesome
import org.controlsfx.glyphfont.Glyph


//TableFilter
fun <T> TableView<T>.applyTableFilter(lazy: Boolean = true): TableFilter<T>? {

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

private val fontAwesome by lazy {
    FontAwesome("http://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.3.0/fonts/fontawesome-webfont.ttf")
}

fun EventTarget.glyph(glyph: FontAwesome.Glyph) {

}

fun FontAwesome.Glyph.toGlyph(op: (Glyph.() -> Unit)? = null): Glyph {
    val glyph = fontAwesome.create(this)
    op?.invoke(glyph)
    return glyph
}
