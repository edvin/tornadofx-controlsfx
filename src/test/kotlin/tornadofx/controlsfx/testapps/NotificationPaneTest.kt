package tornadofx.controlsfx.testapps

import javafx.scene.layout.Priority
import org.controlsfx.control.NotificationPane
import org.controlsfx.glyphfont.FontAwesome
import org.controlsfx.glyphfont.Glyph
import tornadofx.*
import tornadofx.controlsfx.content
import tornadofx.controlsfx.notificationPane

class NotificationPaneApp : App(NotificationPaneView::class)

class NotificationPaneView : View("NotificationPaneView") {
    var notificationPane by singleAssign<NotificationPane>()
    override val root = notificationPane {
        notificationPane = this
        isShowFromTop = true
        content {
            vbox {
                vgrow = Priority.ALWAYS

                button("Send notification") {
                    action {
                        action()
                    }
                }
            }
        }
    }

    fun action() {
        notificationPane.show("Notification", Glyph("FontAwesome", FontAwesome.Glyph.STAR))
    }
}