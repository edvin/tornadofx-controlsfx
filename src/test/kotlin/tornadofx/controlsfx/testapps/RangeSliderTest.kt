package tornadofx.controlsfx.testapps

import javafx.beans.property.SimpleDoubleProperty
import javafx.scene.control.TextField
import org.controlsfx.control.RangeSlider
import rangeslider
import tornadofx.*

class RangeSliderApp : App(RangeSliderView::class)

class RangeSliderView : View("Range Slider") {
    var lowValue = SimpleDoubleProperty(0.0)
    var highValue = SimpleDoubleProperty(1.0)

    var rangeSlider by singleAssign<RangeSlider>()
    override val root = borderpane {
        center {
            rangeSlider = rangeslider(lowValue, highValue)
        }
        top {
            vbox {
                textfield(lowValue) { }
                textfield(highValue) { }
            }
        }
    }
}