package tornadofx.controlsfx.testapps

import javafx.geometry.Pos
import javafx.scene.image.ImageView
import tornadofx.*
import tornadofx.controlsfx.*


class NoitificationsApp : App(NotificationsView::class)

class NotificationsView : View("Notifications") {
    override val root = vbox(10) {
        button("Warning") {
            action {
                warningNotification("Warning","Text")
            }
        }
        button("Info") {
            action {
                infoNotification("Info With Owner","Text", owner = this@vbox)
            }
        }
        button("Confirm") {
            action {
                confirmNotification("Confirm Dark Style","Text", darkStyle = true)
            }
        }
        button("Error") {
            action {
                errorNotification("Error With Top Left Position","Text", position = Pos.TOP_LEFT)
            }
        }
        button("Custom") {
            action {
                customNotification("Custom","Text", ImageView("tornado-fx-logo.png"))
            }
        }
    }
}