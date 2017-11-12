package tornadofx.controlsfx.testapps

import tornadofx.*
import tornadofx.controlsfx.action
import tornadofx.controlsfx.hyperlinklabel

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