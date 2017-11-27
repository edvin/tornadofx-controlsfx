package tornadofx.controlsfx

import javafx.beans.property.Property
import javafx.beans.property.ReadOnlyListProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.StringProperty
import javafx.beans.value.ObservableValue
import javafx.collections.ObservableList
import javafx.event.EventTarget
import javafx.geometry.Orientation
import javafx.scene.Node
import javafx.scene.control.CheckBoxTreeItem
import javafx.scene.control.TreeItem
import javafx.util.Callback
import org.controlsfx.control.CheckListView
import org.controlsfx.control.CheckTreeView
import org.controlsfx.control.SegmentedBar
import tornadofx.*

//region CheckListView
fun <T> EventTarget.checklistview(items: ObservableList<T>? = null, op: (CheckListView<T>.() -> Unit) = {}): CheckListView<T> {
    val checkListView = CheckListView(items)
    return opcr(this, checkListView, op)
}

fun <T> EventTarget.checklistview(items: ReadOnlyListProperty<T>, op: (CheckListView<T>.() -> Unit) = {}): CheckListView<T> =
        checklistview(items as ObservableValue<ObservableList<T>>, op)

fun <T> EventTarget.checklistview(items: ObservableValue<ObservableList<T>>, op: (CheckListView<T>.() -> Unit) = {}): CheckListView<T> {
    val checkListView = CheckListView<T>()
    fun rebinder() {
        (checkListView.items as? SortedFilteredList<T>)?.bindTo(checkListView)
    }
    checkListView.itemsProperty().bind(items)
    rebinder()
    checkListView.itemsProperty().onChange {
        rebinder()
    }
    return opcr(this, checkListView, op)
}
//endregion

//region CheckedTreeView
fun <T> EventTarget.checktreeview(root: T? = null, op: (CheckTreeView<T>.() -> Unit) = {}): CheckTreeView<T> {
    val checkBoxTreeView = (if (root != null)
        CheckTreeView<T>(CheckBoxTreeItem<T>(root))
    else
        CheckTreeView())
    return opcr(this, checkBoxTreeView, op)
}

fun <T> CheckTreeView<T>.populate(itemFactory: (T) -> CheckBoxTreeItem<T> = { CheckBoxTreeItem(it) },
                                  childFactory: (TreeItem<T>) -> Iterable<T>?) =
        populateTree(root, itemFactory, childFactory)
//endregion

//region SegmentedBar
fun <T : SegmentedBar.Segment> EventTarget.segmentedbar(segments: List<T>,
                                                        orientation: Orientation = Orientation.HORIZONTAL,
                                                        op: (SegmentedBar<T>.() -> Unit) = {}): SegmentedBar<T> {
    val segmentedBar = SegmentedBar<T>().apply {
        this.orientation = orientation
        this.segments.setAll(segments)
    }
    return opcr(this, segmentedBar, op)
}

fun <T : SegmentedBar.Segment> EventTarget.segmentedbar(segments: Property<ObservableList<T>>? = null,
                                                        orientation: Property<Orientation> = SimpleObjectProperty<Orientation>(Orientation.HORIZONTAL),
                                                        op: (SegmentedBar<T>.() -> Unit) = {}): SegmentedBar<T> {
    val segmentedBar = SegmentedBar<T>().apply {
        if (segments != null) segmentsProperty().bindBidirectional(segments)
        orientationProperty().bindBidirectional(orientation)
    }
    return opcr(this, segmentedBar, op)
}

fun <T : SegmentedBar.Segment> SegmentedBar<T>.populate(factory: () -> List<T>) {
    segments.setAll(factory())
}

fun <T : SegmentedBar.Segment> SegmentedBar<T>.segmentViewFactory(factory: (T) -> Node) {
    this.segmentViewFactory = Callback { factory(it) }
}

fun <T : SegmentedBar.Segment> SegmentedBar<T>.infoNodeFactory(factory: (T) -> Node) {
    this.infoNodeFactory = Callback { factory(it) }
}

fun SegmentedBar<in SegmentedBar.Segment>.segment(value: Double, text: String? = null, op: (SegmentedBar.Segment.() -> Unit) = {}):
        SegmentedBar.Segment {
    val segment = SegmentedBar.Segment(value, text)
    op.invoke(segment)
    this.getSegments().add(segment)
    return segment
}

fun SegmentedBar<in SegmentedBar.Segment>.segment(value: Property<Number>, text: StringProperty? = null, op: (SegmentedBar.Segment.() -> Unit) = {}):
        SegmentedBar.Segment {
    val segment = SegmentedBar.Segment(value.value.toDouble())
            .apply {
                valueProperty().bindBidirectional(value)
                if (text != null) textProperty().bindBidirectional(text)
            }
    op.invoke(segment)
    this.getSegments().add(segment)
    return segment
}
//endregion