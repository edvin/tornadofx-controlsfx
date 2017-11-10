package tornadofx.controlsfx.testapps

import popover
import showPopover
import tornadofx.*

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