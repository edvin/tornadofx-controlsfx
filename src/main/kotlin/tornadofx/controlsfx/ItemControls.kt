package tornadofx.controlsfx

import javafx.beans.property.ReadOnlyListProperty
import javafx.beans.value.ObservableValue
import javafx.collections.ObservableList
import javafx.event.EventTarget
import org.controlsfx.control.CheckListView
import tornadofx.*

// CheckListView
fun <T> EventTarget.checklistview(items: ObservableList<T>? = null, op: (CheckListView<T>.() -> Unit) = {}): CheckListView<T> {
    val checkListView = CheckListView(items)
    return opcr(this,checkListView,op)
}

fun <T> EventTarget.checklistview(items: ReadOnlyListProperty<T>, op: (CheckListView<T>.() -> Unit)? = null): CheckListView<T> =
        checklistview(items as ObservableValue<ObservableList<T>>, op)

fun <T> EventTarget.checklistview(items: ObservableValue<ObservableList<T>>, op: (CheckListView<T>.() -> Unit)? = null): CheckListView<T> {
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