package tornadofx.controlsfx.testapps

import tornadofx.*
import tornadofx.controlsfx.popover
import tornadofx.controlsfx.showPopover

class PopOverTestApp : App(PopOverView::class)

class PopOverView : View("PopoverView") {
    override val root = vbox {
        popover() {
            label("dasdsadas") {

            }
        }
        setOnMouseClicked {
            showPopover()
        }
    }
}