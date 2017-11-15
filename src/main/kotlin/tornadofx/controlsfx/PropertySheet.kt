package tornadofx.controlsfx


import javafx.beans.property.Property
import javafx.beans.property.SimpleObjectProperty
import javafx.collections.ObservableList
import javafx.event.EventTarget
import org.controlsfx.control.PropertySheet
import org.controlsfx.property.BeanPropertyUtils
import tornadofx.*

fun EventTarget.propertysheet(mode: PropertySheet.Mode = PropertySheet.Mode.NAME, op: (PropertySheet.() -> Unit)? = null): PropertySheet {
    val propertySheet = PropertySheet().apply {
        this.mode = mode
    }
    return opcr(this, propertySheet, op)
}

fun EventTarget.propertysheet(mode: PropertySheet.Mode = PropertySheet.Mode.NAME, items: ObservableList<PropertySheet.Item>, op: (PropertySheet.() -> Unit)? = null): PropertySheet {
    val propertySheet = PropertySheet(items).apply {
        this.mode = mode
    }
    return opcr(this, propertySheet, op)
}

fun EventTarget.propertysheet(bean: Any, mode: PropertySheet.Mode = PropertySheet.Mode.NAME, op: (PropertySheet.() -> Unit)? = null): PropertySheet {
    val items = BeanPropertyUtils.getProperties(bean)
    val propertySheet = PropertySheet(items).apply {
        this.mode = mode
    }
    return opcr(this, propertySheet, op)
}

fun EventTarget.propertysheet(mode: Property<PropertySheet.Mode>, op: (PropertySheet.() -> Unit)? = null): PropertySheet {
    val propertySheet = PropertySheet().apply {
        this.modeProperty().bind(mode)
    }
    return opcr(this, propertySheet, op)
}

fun EventTarget.propertysheet(mode: Property<PropertySheet.Mode>, items: ObservableList<PropertySheet.Item>, op: (PropertySheet.() -> Unit)? = null): PropertySheet {
    val propertySheet = PropertySheet(items).apply {
        this.modeProperty().bind(mode)
    }
    return opcr(this, propertySheet, op)
}

fun EventTarget.propertysheet(bean: Any, mode: Property<PropertySheet.Mode>, op: (PropertySheet.() -> Unit)? = null): PropertySheet {
    val items = BeanPropertyUtils.getProperties(bean)
    val propertySheet = PropertySheet(items).apply {
        this.modeProperty().bind(mode)
    }
    return opcr(this, propertySheet, op)
}