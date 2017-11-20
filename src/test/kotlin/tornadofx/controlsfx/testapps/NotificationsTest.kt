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
                warningNotification("Warning")
            }
        }
        button("Info") {
            action {
                infoNotification("Info With Owner", owner = this@vbox)
            }
        }
        button("Confirm") {
            action {
                confirmNotification("Confirm Dark Style", darkStyle = true)
            }
        }
        button("Error") {
            action {
                errorNotification("Error With Top Left Position", position = Pos.TOP_LEFT)
            }
        }
        button("Custom") {
            action {
                customNotification("Custom", ImageView("tornado-fx-logo.png"))
            }
        }
    }
}