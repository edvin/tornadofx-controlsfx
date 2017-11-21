package tornadofx.controlsfx.testapps


import javafx.beans.property.SimpleListProperty
import tornadofx.*
import tornadofx.controlsfx.listSelectionView

class ListSelectionViewApp : App(ListSelectionViewView::class)

class ListSelectionViewView : View("ListSelectionView") {
    val sourceItems = SimpleListProperty(mutableListOf("AAA", "BBB", "CCC").observable())
    override val root = vbox {
        listSelectionView<String>(sourceItems){
        }
    }
}