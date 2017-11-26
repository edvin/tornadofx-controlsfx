package tornadofx.controlsfx

import impl.org.controlsfx.table.ColumnFilter
import javafx.beans.property.*
import javafx.beans.value.ObservableValue
import javafx.event.EventTarget
import javafx.geometry.Orientation
import javafx.scene.Node
import javafx.scene.control.*
import javafx.stage.PopupWindow
import javafx.util.Callback
import org.controlsfx.control.*
import org.controlsfx.control.table.TableFilter
import org.controlsfx.glyphfont.FontAwesome
import org.controlsfx.glyphfont.Glyph
import org.controlsfx.glyphfont.GlyphFontRegistry
import tornadofx.*
import java.util.*
import java.util.function.BiFunction


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

//region Fontawesome + Glyph
private val fontAwesome by lazy {
    GlyphFontRegistry.font("FontAwesome")
}

fun FontAwesome.Glyph.toGlyph(op: (Glyph.() -> Unit) = {}): Glyph {
    val glyph = fontAwesome.create(this)
    op.invoke(glyph)
    return glyph
}

fun EventTarget.glyph(op: (Glyph.() -> Unit) = {}): Glyph = opcr(this, Glyph(), op)

fun EventTarget.glyph(fontFamily: String, unicode: Char, op: (Glyph.() -> Unit) = {}): Glyph {
    return opcr(this, Glyph(fontFamily, unicode), op)
}

fun EventTarget.glyph(fontFamily: String, icon: Any, op: (Glyph.() -> Unit) = {}): Glyph {
    return opcr(this, Glyph(fontFamily, icon), op)
}

//endregion

//region ToggleSwitch

fun EventTarget.toggleswitch(text: String? = null, selectedProperty: Property<Boolean>? = null, op: (ToggleSwitch.() -> Unit) = {}): ToggleSwitch {
    val toggleSwitch = ToggleSwitch(text)
    toggleSwitch.selectedProperty().bindBidirectional(selectedProperty)
    return opcr(this, toggleSwitch, op)
}
//endregion

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
fun EventTarget.hyperlinklabel(text: String, op: (HyperlinkLabel.() -> Unit) = {}): HyperlinkLabel {
    val hyperlinkLabel = HyperlinkLabel(text)
    return opcr(this, hyperlinkLabel, op)
}

fun EventTarget.hyperlinklabel(text: ObservableValue<String>, op: (HyperlinkLabel.() -> Unit) = {}): HyperlinkLabel {
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
fun EventTarget.rating(rating: Int, max: Int, allowPartialRating: Boolean = false, updateRatingOnHover: Boolean = false, op: (Rating.() -> Unit) = {}): Rating {
    val r = Rating(max, rating).apply {
        isPartialRating = allowPartialRating
        isPartialRating = updateRatingOnHover
    }
    return opcr(this, r, op)

}

fun EventTarget.rating(rating: Property<Number>, max: Property<Number>, allowPartialRating: Boolean = false, updateRatingOnHover: Boolean = false, op: (Rating.() -> Unit) = {}): Rating {
    val r = Rating().apply {
        ratingProperty().bindBidirectional(rating)
        maxProperty().bindBidirectional(max)
        isPartialRating = allowPartialRating
        isPartialRating = updateRatingOnHover
    }
    return opcr(this, r, op)
}

fun EventTarget.rating(rating: Property<Number>, max: Int, allowPartialRating: Boolean = false, updateRatingOnHover: Boolean = false, op: (Rating.() -> Unit) = {}): Rating {
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

fun EventTarget.plusminuslider(value: Property<Number>, orientation: Orientation = Orientation.HORIZONTAL, op: (PlusMinusSlider.() -> Unit) = {}): PlusMinusSlider {
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
                            op: (RangeSlider.() -> Unit) = {}): RangeSlider {
    val rangeSlider = RangeSlider(min, max, lowValue, highValue)
    return opcr(this, rangeSlider, op)
}

fun EventTarget.rangeslider(
        lowValue: DoubleProperty,
        highValue: DoubleProperty,
        min: Double = 0.0, max: Double = 1.0,
        op: (RangeSlider.() -> Unit) = {}): RangeSlider {
    val rangeSlider = RangeSlider().apply {
        this.min = min
        this.max = max
        lowValueProperty().bindBidirectional(lowValue)
        highValueProperty().bindBidirectional(highValue)
    }
    return opcr(this, rangeSlider, op)
}
//endregion

//region WorldMapView
fun EventTarget.worldmapView(locations: List<WorldMapView.Location>? = null, selectedLocations: List<WorldMapView.Location>? = null,
                             selectedCountries: List<WorldMapView.Country>? = null,
                             op: (WorldMapView.() -> Unit) = {}): WorldMapView {
    val worldMapView = WorldMapView().apply {
        if (locations != null) this.locations.setAll(locations)
        if (selectedLocations != null) this.selectedLocations.setAll(selectedLocations)
        if (selectedCountries != null) this.selectedCountries.setAll(selectedCountries)

    }
    return opcr(this, worldMapView, op)
}

fun EventTarget.worldmapView(locations: ListProperty<WorldMapView.Location>? = null, selectedLocations: ListProperty<WorldMapView.Location>? = null,
                             selectedCountries: ListProperty<WorldMapView.Country>? = null,
                             op: (WorldMapView.() -> Unit) = {}): WorldMapView {
    val worldMapView = WorldMapView().apply {
        if (locations != null) locationsProperty().bind(locations)
        if (selectedLocations != null) selectedLocationsProperty().bindBidirectional(selectedLocations)
        if (selectedCountries != null) selectedCountriesProperty().bindBidirectional(selectedCountries)

    }
    return opcr(this, worldMapView, op)
}

fun EventTarget.worldmapView(op: (WorldMapView.() -> Unit) = {}): WorldMapView {
    val worldMapView = WorldMapView()
    return opcr(this, worldMapView, op)
}

fun WorldMapView.countryViewFactory(op: WorldMapView.(WorldMapView.Country) -> WorldMapView.CountryView) {
    this.countryViewFactory = Callback { op(it) }
}

fun WorldMapView.locationViewFactory(op: WorldMapView.(WorldMapView.Location) -> Node) {
    this.locationViewFactory = Callback { op(it) }
}
//endregion

//region InfoOverlay

fun EventTarget.infooverlay(text: String, op: (InfoOverlay.() -> Unit)): InfoOverlay {
    val infoOverlay = InfoOverlay().apply {
        this.text = text
    }
    return opcr(this, infoOverlay, op)
}

fun EventTarget.infooverlay(imageUrl: String, text: String, op: (InfoOverlay.() -> Unit) = {}): InfoOverlay =
        opcr(this, InfoOverlay(imageUrl, text), op)

fun EventTarget.infooverlay(content: Node, text: String, op: (InfoOverlay.() -> Unit) = {}): InfoOverlay =
        opcr(this, InfoOverlay(content, text), op)

fun EventTarget.infooverlay(node: Property<Node>, text: Property<String>, op: (InfoOverlay.() -> Unit) = {}): InfoOverlay {
    val infoOverlay = InfoOverlay().apply {
        contentProperty().bindBidirectional(node)
        textProperty().bindBidirectional(text)
    }
    return opcr(this, infoOverlay, op)
}
//endregion

//region Prefix Selection
fun <T> EventTarget.prefixselectioncombobox(op: (PrefixSelectionComboBox<T>.() -> Unit) = {}): PrefixSelectionComboBox<T> {
    return opcr(this, PrefixSelectionComboBox(), op)
}

fun <T> EventTarget.prefixselectioncombobox(items: List<T>, lookup: ComboBox<T>.(String) -> Optional<T>, op: (PrefixSelectionComboBox<T>.() -> Unit) = {}): PrefixSelectionComboBox<T> {
    val comboBox = PrefixSelectionComboBox<T>().apply {
        this.items = items.observable()
        val internal: (ComboBox<*>, String) -> Optional<*> = { _, str -> lookup(this, str) }
        this.lookup = BiFunction<ComboBox<*>, String, Optional<*>>(internal)
    }

    return opcr(this, comboBox, op)
}

fun <T> EventTarget.prefixselectioncombobox(items: ListProperty<T>, op: (PrefixSelectionComboBox<T>.() -> Unit) = {}): PrefixSelectionComboBox<T> {
    val comboBox = PrefixSelectionComboBox<T>().apply {
        this.itemsProperty().bindBidirectional(items)
    }

    return opcr(this, comboBox, op)
}

fun <T> PrefixSelectionComboBox<T>.lookup(op: PrefixSelectionComboBox<T>.(String) -> T?) {
    val internal: (ComboBox<*>, String) -> Optional<T> = { _, str ->
        val result = op(this, str)
        when (result) {
            null -> Optional.empty()
            else -> Optional.of(result)
        }
    }
    this.lookup = BiFunction<ComboBox<*>, String, Optional<*>>(internal)
}


fun <T> EventTarget.prefixselectionchoicebox(items: ListProperty<T>? = null, op: (PrefixSelectionChoiceBox<T>.() -> Unit) = {}): PrefixSelectionChoiceBox<T> {
    val prefixSelectionChoiceBox = PrefixSelectionChoiceBox<T>().apply {
        if (items != null) itemsProperty().bindBidirectional(items)
    }
    return opcr(this, prefixSelectionChoiceBox, op)
}
//endregion

//region ListSelectionView
fun <T> EventTarget.listSelectionView(op: (ListSelectionView<T>.() -> Unit) = {}): ListSelectionView<T> =
        opcr(this, ListSelectionView(), op)

fun <T> EventTarget.listSelectionView(sourceItems: ListProperty<T>? = null,
                                      targetItems: ListProperty<T>? = null,
                                      op: (ListSelectionView<T>.() -> Unit) = {}): ListSelectionView<T> {
    val listSelectionView = ListSelectionView<T>().apply {
        if (sourceItems != null) this.sourceItemsProperty().bindBidirectional(sourceItems)
        if (targetItems != null) this.targetItemsProperty().bindBidirectional(sourceItems)
    }
    return opcr(this, listSelectionView, op)
}
//endregion

