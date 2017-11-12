package tornadofx.controlsfx

import impl.org.controlsfx.table.ColumnFilter
import javafx.beans.property.DoubleProperty
import javafx.beans.property.Property
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.value.ObservableValue
import javafx.event.EventTarget
import javafx.geometry.Orientation
import javafx.scene.Node
import javafx.scene.control.*
import javafx.stage.PopupWindow
import org.controlsfx.control.*
import org.controlsfx.control.table.TableFilter
import org.controlsfx.glyphfont.FontAwesome
import org.controlsfx.glyphfont.Glyph
import tornadofx.*


//TableFilter
private fun <T> TableView<T>.applyTableFilter(lazy: Boolean = true): TableFilter<T> {

    val tableFilter = TableFilter.forTableView(this)
            .lazy(lazy)
            .apply()

    this.properties.put("TableFilter", tableFilter)

    return tableFilter
}

fun <T> TableView<T>.tablefilter(op: (TableFilter<T>).() -> Unit = {}): TableFilter<T> {
    val tf = tableFilter
    tf.op()
    return tf
}

@Suppress("UNCHECKED_CAST")
val <T> TableView<T>.tableFilter: TableFilter<T>
    get() = (properties["TableFilter"] as TableFilter<T>?) ?: applyTableFilter()

@Suppress("UNCHECKED_CAST")
val <T, C> TableColumn<T, C>.columnFilter: ColumnFilter<T, C>
    get() =
        (tableView.tableFilter.getColumnFilter(this)
                .orElseThrow { Exception("TableFilter not initialized!") } as ColumnFilter<T, C>)
                .apply {
                    initialize()
                }

fun <T, C> TableColumn<T, C>.columnfilter(op: ColumnFilter<T, C>.() -> Unit) {
    columnFilter.op()
}

fun <T> ColumnFilter<T, *>.selectValues(vararg values: Any?) {
    values.forEach { selectValue(it) }
}

fun <T> ColumnFilter<T, *>.exceptValue(value: Any?) {
    unSelectAllValues()
    selectValue(value)
}

fun <T> ColumnFilter<T, *>.exceptValues(vararg values: Any?) {
    unSelectAllValues()
    values.forEach { selectValue(it) }
}

private val fontAwesome by lazy {
    FontAwesome("http://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.3.0/fonts/fontawesome-webfont.ttf")
}

fun FontAwesome.Glyph.toGlyph(op: (Glyph.() -> Unit)? = null): Glyph {
    val glyph = fontAwesome.create(this)
    op?.invoke(glyph)
    return glyph
}

//ToggleSwitch

fun EventTarget.toggleswitch(text: String? = null, selectedProperty: Property<Boolean>? = null, op: (ToggleSwitch.() -> Unit) = {}): ToggleSwitch {
    val toggleSwitch = ToggleSwitch(text)
    toggleSwitch.selectedProperty().bindBidirectional(selectedProperty)
    return opcr(this, toggleSwitch, op)
}

//SegmentedButton

fun EventTarget.segmentedbutton(op: (SegmentedButton.() -> Unit) = {}): SegmentedButton {
    val segmentedButton = SegmentedButton()

    return opcr(this, segmentedButton, op)
}

operator fun SegmentedButton.plusAssign(toggleButton: ToggleButton) {
    buttons.add(toggleButton)
}

fun SegmentedButton.button(text: String? = null, op: (ToggleButton.() -> Unit) = {}): ToggleButton {
    val toggleButton = ToggleButton(text)
    toggleButton.op()
    this += toggleButton
    return toggleButton
}

//BreadCrumbBar

fun <T> EventTarget.breadcrumbbar(selectedCrumb: TreeItem<T> = TreeItem(), op: (BreadCrumbBar<T>).() -> Unit = {}): BreadCrumbBar<T> {
    val bcb = BreadCrumbBar<T>(selectedCrumb)
    bcb.op()
    return bcb
}

fun <T> BreadCrumbBar<T>.treeitem(value: T, op: TreeItem<T>.() -> Unit = {}): TreeItem<T> {
    val treeItem = TreeItem<T>(value)
    treeItem.op()
    selectedCrumb = treeItem
    return treeItem
}

//region HyperlinkLabel
fun EventTarget.hyperlinklabel(text: String, op: (HyperlinkLabel.() -> Unit)? = null): HyperlinkLabel {
    val hyperlinkLabel = HyperlinkLabel(text)
    return opcr(this, hyperlinkLabel, op)
}

fun EventTarget.hyperlinklabel(text: ObservableValue<String>, op: (HyperlinkLabel.() -> Unit)? = null): HyperlinkLabel {
    val hyperlinkLabel = HyperlinkLabel()
    hyperlinkLabel.textProperty().bind(text)
    return opcr(this, hyperlinkLabel, op)
}

fun HyperlinkLabel.action(op: Hyperlink.() -> Unit) = setOnAction { op(it.source as Hyperlink) }

//endregion

//region PopOver
fun popoverBuilder(anchorLocation: PopupWindow.AnchorLocation = PopupWindow.AnchorLocation.WINDOW_TOP_LEFT,
                   arrowLocation: PopOver.ArrowLocation = PopOver.ArrowLocation.LEFT_TOP,
                   arrowIndent: Double = 12.0, contentBuilder: (PopOver.() -> Node)? = null)
        : PopOver {
    val popOver = PopOver().apply {
        this.contentNode = contentBuilder?.invoke(this)
        this.anchorLocation = anchorLocation
        this.arrowLocation = arrowLocation
        this.arrowIndent = arrowIndent
    }

    return popOver
}

fun Node.popover(anchorLocation: PopupWindow.AnchorLocation = PopupWindow.AnchorLocation.WINDOW_TOP_LEFT,
                 arrowLocation: PopOver.ArrowLocation = PopOver.ArrowLocation.LEFT_TOP,
                 arrowIndent: Double = 12.0, contentBuilder: (PopOver.() -> Node)? = null)
        : PopOver {
    val popOver = popoverBuilder(anchorLocation, arrowLocation, arrowIndent, contentBuilder)
    this.popover = popOver
    return popOver
}

var Node.popover: PopOver?
    get() = properties["popOver"] as? PopOver
    set(value) {
        properties["popOver"] = value
    }

fun Node.showPopover() {
    popover?.show(this)
}

//endregion

//region Rating
fun EventTarget.rating(rating: Int, max: Int, allowPartialRating: Boolean = false, updateRatingOnHover: Boolean = false, op: (Rating.() -> Unit)? = null): Rating {
    val r = Rating(max, rating).apply {
        isPartialRating = allowPartialRating
        isPartialRating = updateRatingOnHover
    }
    return opcr(this, r, op)

}

fun EventTarget.rating(rating: Property<Number>, max: Property<Number>, allowPartialRating: Boolean = false, updateRatingOnHover: Boolean = false, op: (Rating.() -> Unit)? = null): Rating {
    val r = Rating().apply {
        ratingProperty().bindBidirectional(rating)
        maxProperty().bindBidirectional(max)
        isPartialRating = allowPartialRating
        isPartialRating = updateRatingOnHover
    }
    return opcr(this, r, op)
}

fun EventTarget.rating(rating: Property<Number>, max: Int, allowPartialRating: Boolean = false, updateRatingOnHover: Boolean = false, op: (Rating.() -> Unit)? = null): Rating {
    val r = Rating(max).apply {
        ratingProperty().bindBidirectional(rating)
        isPartialRating = allowPartialRating
        isPartialRating = updateRatingOnHover
    }
    return opcr(this, r, op)
}
//endregion

//region StatusBar
fun EventTarget.statusbar(op: (StatusBar.() -> Unit)? = null): StatusBar {
    val statusBar = StatusBar().apply {
        leftItems.clear()
        rightItems.clear()
    }
    return opcr(this, statusBar, op)
}

fun EventTarget.statusbar(text: String? = null, op: (StatusBar.() -> Unit)? = null): StatusBar {
    val statusBar = statusbar(op).apply {
        this.text = text
    }
    return opcr(this, statusBar, op)
}

fun EventTarget.statusbar(text: ObservableValue<String>? = null, op: (StatusBar.() -> Unit)? = null): StatusBar {
    val statusBar = statusbar(op).apply {
        if (text != null) this.textProperty().bind(text)
    }
    return opcr(this, statusBar, op)
}
//endregion

//region PlusMinusSlider

fun EventTarget.plusminuslider(value: Property<Number>, orientation: Orientation = Orientation.HORIZONTAL, op: (PlusMinusSlider.() -> Unit)? = null): PlusMinusSlider {
    val plusMinusSlider = PlusMinusSlider().apply {
        this.orientation = orientation
    }
    plusMinusSlider.setOnValueChanged {
        value.value = it.value
    }
    return opcr(this, plusMinusSlider, op)
}

//endregion

//region Masker Pane
fun EventTarget.maskerpane(progress: Double = Double.NEGATIVE_INFINITY, visible: Boolean = false, progressVisible: Boolean = true, op: (MaskerPane.() -> Unit)): MaskerPane {
    val maskerPane = MaskerPane().apply {
        isVisible = visible
        setProgressVisible(progressVisible)
        setProgress(progress)
    }
    return opcr(this, maskerPane, op)
}

fun EventTarget.maskerpane(progress: Property<Number> = SimpleDoubleProperty(Double.NEGATIVE_INFINITY), visible: ObservableValue<Boolean> = SimpleBooleanProperty(false), op: (MaskerPane.() -> Unit)): MaskerPane {
    val maskerPane = MaskerPane().apply {
        visibleProperty().bind(visible)
        progressProperty().bindBidirectional(progress)
    }
    return opcr(this, maskerPane, op)
}
//endregion

//region RangeSlider
fun EventTarget.rangeslider(min: Double = 0.0, max: Double = 1.0, lowValue: Double = 0.25, highValue: Double = 0.75,
                            op: (RangeSlider.() -> Unit)? = null): RangeSlider {
    val rangeSlider = RangeSlider(min, max, lowValue, highValue)
    return opcr(this, rangeSlider, op)
}

fun EventTarget.rangeslider(
        lowValue: DoubleProperty,
        highValue: DoubleProperty,
        min: Double = 0.0, max: Double = 1.0,
        op: (RangeSlider.() -> Unit)? = null): RangeSlider {
    val rangeSlider = RangeSlider().apply {
        this.min = min
        this.max = max
        lowValueProperty().bindBidirectional(lowValue)
        highValueProperty().bindBidirectional(highValue)
    }
    return opcr(this, rangeSlider, op)
}
//endregion