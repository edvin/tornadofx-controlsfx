package tornadofx.controlsfx.testapps

import javafx.beans.property.SimpleDoubleProperty
import tornadofx.*
import tornadofx.controlsfx.rating

class RatingApp : App(RatingView::class)

class RatingView : View("Rating View") {
    val ratingValue = SimpleDoubleProperty(0.0)

    init {
        ratingValue.onChange {
            log.info("Value changed to $ratingValue")
        }
    }

    override val root = vbox {
        rating(0, 5)
        rating(ratingValue, 5)
    }
}