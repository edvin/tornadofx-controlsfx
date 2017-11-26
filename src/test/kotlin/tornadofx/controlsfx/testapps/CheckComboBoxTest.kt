package tornadofx.controlsfx.testapps

import javafx.collections.FXCollections
import javafx.collections.ListChangeListener
import tornadofx.*
import tornadofx.controlsfx.checkcombobox

class CheckComboBoxApp : App(CheckComboBoxView::class)

class CheckComboBoxView : View("CheckComboBox") {
    val items = mutableListOf("AAA", "BBB", "CCC")
    val checkedItems = FXCollections.observableArrayList<String>()
    override val root = vbox {
        checkcombobox(items.observable(), checkedItems)
    }

    override fun onDock() {
        super.onDock()
        checkedItems.onChange<String> { change: ListChangeListener.Change<out String> ->
            change.next()
            for (i in change.addedSubList) {
                println("Added $i")
            }
            for (i in change.removed) {
                println("Removed $i")
            }
        }
    }
}