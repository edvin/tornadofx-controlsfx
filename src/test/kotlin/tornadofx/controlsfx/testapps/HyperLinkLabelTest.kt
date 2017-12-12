package tornadofx.controlsfx.testapps

import tornadofx.*
import tornadofx.controlsfx.action
import tornadofx.controlsfx.command
import tornadofx.controlsfx.hyperlinklabel

class HyperLinkLabelApp : App(HyperLinkLabelView::class)
class HyperLinkLabelViewModel : ViewModel() {
    val hyperlinkCommand = command(this::hyperlinkAction)

    private fun hyperlinkAction(text:String) {
        log.info { "link $text clicked" }
    }
}

class HyperLinkLabelView : View("HyperLinkLabel") {
    val viewModel by inject<HyperLinkLabelViewModel>()
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
        hyperlinklabel("This is [text] link with command. And another [link]") {
            command = viewModel.hyperlinkCommand
        }
    }

}