package tornadofx.controlsfx.testapps

import action
import hyperlinklabel
import tornadofx.*

class HyperLinkLabelApp : App(HyperLinkLabelView::class)

class HyperLinkLabelView : View("HyperLinkLabel") {
    override val root = vbox {
        hyperlinklabel("This is [text] link. Another [link]") {
            action {
                when (text) {
                    "text" -> {
                        log.info { "text link clicked" }
                    }
                    "link" -> {
                        log.info { "link link clicked" }
                    }
                }
            }
        }
    }

}