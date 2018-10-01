package tornadofx.controlsfx.testapps

import javafx.beans.Observable
import javafx.scene.control.TreeItem
import tornadofx.*
import tornadofx.controlsfx.*


class CheckTreeViewApp : App(CheckTreeViewView::class)

class CheckTreeViewView : View("CheckboxTree") {
    private val checkedItems = mutableListOf<TreeItem<String>>().observable()

    override val root = vbox{
        checktreeview<String> {
            root = checkboxtreeitem("root") {
                checkboxtreeitem("second0") {
                    checkboxtreeitem("third0")
                    checkboxtreeitem("third1")
                }
                checkboxtreeitem("second1")
            }

            checkedItems.bind(checkModel.checkedItems) { it }
        }

        label {
            // Shows currently checked items
            checkedItems.addListener { o: Observable ->
                text = checkedItems.joinToString(", ") { it.value }
            }
        }
    }
}
