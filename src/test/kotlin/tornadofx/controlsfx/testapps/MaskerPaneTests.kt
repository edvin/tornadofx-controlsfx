package tornadofx.controlsfx.testapps

import javafx.beans.property.SimpleBooleanProperty
import tornadofx.*
import tornadofx.controlsfx.maskerpane

class MaskerPaneApp : App(MaskerPaneView::class)

class MaskerPaneView : View("Rating View") {
    val visibleMaskerPane = SimpleBooleanProperty(true)
    override val root = vbox {
        hbox {
            togglegroup {
                togglebutton("ON"){
                    action {
                        visibleMaskerPane.value = true
                    }
                }
                togglebutton("OFF"){
                    action {
                        visibleMaskerPane.value = false
                    }
                }
            }
        }
        stackpane {
            maskerpane(visible = visibleMaskerPane) {
            }
            label("Label")
        }
    }
}