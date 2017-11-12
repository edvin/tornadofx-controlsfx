package tornadofx.controlsfx.testapps

import javafx.beans.property.SimpleDoubleProperty
import tornadofx.*
import tornadofx.controlsfx.plusminuslider

class PlusMinusSliderApp : App(PlusMinusSliderView::class)

class PlusMinusSliderView : View("Plus Minus Slider") {
    val value = SimpleDoubleProperty()
    override val root = vbox(10) {
        plusminuslider(value)
        hbox(5) {
            label("Value:")
            label(value)
        }
    }

}