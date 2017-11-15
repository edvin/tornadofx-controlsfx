package tornadofx.controlsfx.testapps

import javafx.beans.value.ObservableValue
import javafx.scene.layout.VBox
import org.controlsfx.control.PropertySheet
import tornadofx.*
import tornadofx.controlsfx.propertysheet
import java.util.Optional
import org.controlsfx.control.PropertySheet.Item
import java.time.LocalDate
import java.time.Month
import java.util.LinkedHashMap


class PropertySheetApp : App(PropertySheetView::class)

class Bean {
    var name: String = "aaa"
    var familyName: String = "BBB"
}

private val customDataMap = LinkedHashMap<String, Any>().apply {
    put("1. Name#First Name", "Jonathan")
    put("1. Name#Last Name", "Giles")
    put("1. Name#Birthday", LocalDate.of(1985, Month.JANUARY, 12))
    put("2. Billing Address#Address 1", "")
    put("2. Billing Address#Address 2", "")
    put("2. Billing Address#City", "")
    put("2. Billing Address#State", "")
    put("2. Billing Address#Zip", "")
    put("3. Phone#Home", "123-123-1234")
    put("3. Phone#Mobile", "234-234-2345")
    put("3. Phone#Work", "")
}

internal class CustomPropertyItem(private val key: String) : Item {
    private val category: String
    private val name: String

    init {
        val skey = key.split("#".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        category = skey[0]
        name = skey[1]
    }

    override fun getType(): Class<*> {
        return customDataMap[key]!!::class.java
    }

    override fun getCategory(): String {
        return category
    }

    override fun getName(): String {
        return name
    }

    override fun getDescription(): String? {
        return null
    }

    override fun getValue(): Any {
        return customDataMap[key]!!
    }

    override fun setValue(value: Any) {
        customDataMap.put(key, value)
    }

    override fun getObservableValue(): Optional<ObservableValue<out Any>> {
        return Optional.empty()
    }

}

val propertySheetItems: List<PropertySheet.Item> = customDataMap.map { entry -> CustomPropertyItem(entry.key) }

class PropertySheetView : View("PropertySheet") {
    override val root = tabpane {
        tab("Empty property sheet", VBox()) {
            propertysheet{
                title = "Title"
                this.items.setAll(propertySheetItems)
            }
        }
        tab("Items property sheet", VBox()) {
            propertysheet(items = propertySheetItems.observable())
        }
        tab("Bean property sheet", VBox()) {
            propertysheet(Bean())
        }
    }
}