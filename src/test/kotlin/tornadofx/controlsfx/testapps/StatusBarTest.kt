package tornadofx.controlsfx.testapps

import javafx.geometry.Insets
import javafx.geometry.Orientation
import javafx.scene.control.Button
import javafx.scene.layout.Background
import javafx.scene.layout.BackgroundFill
import javafx.scene.layout.CornerRadii
import javafx.scene.paint.Color
import tornadofx.*
import tornadofx.controlsfx.left
import tornadofx.controlsfx.right
import tornadofx.controlsfx.statusbar


class StatusBarTestApp : App(StatusBarView::class)

class StatusBarView : View("Status Bar View") {
    override val root = vbox {
        statusbar {

            button("1") {
                background = Background(BackgroundFill(Color.ORANGE, CornerRadii(2.0), Insets(4.0)))
            }
            separator(Orientation.VERTICAL) {

            }
            button("2") {
                background = Background(BackgroundFill(Color.ORANGE, CornerRadii(2.0), Insets(4.0)))
            }
        }
        statusbar("") {
            right {
                button("1") {
                    background = Background(BackgroundFill(Color.ORANGE, CornerRadii(2.0), Insets(4.0)))
                }
            }

            separator(Orientation.VERTICAL) {

            }
            left {
                button("2") {
                    background = Background(BackgroundFill(Color.ORANGE, CornerRadii(2.0), Insets(4.0)))
                }
            }
        }
    }
}