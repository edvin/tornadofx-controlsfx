package tornadofx.controlsfx.testapps

import tornadofx.*
import tornadofx.controlsfx.borders

class BordersApp : App(BordersView::class)

class BordersView : View("Borders") {
    override val root = vbox(10) {
        button("Empty Border") {
            borders{
                lineBorder()
                        .title("Line")
                        .thickness(1.0)
                        .radius(0.0, 5.0, 5.0, 0.0)
                        .build()
                        .emptyBorder()
                        .padding(20.0)
                        .build()
                        .etchedBorder()
                        .title("Etched")
                        .build()
                        .emptyBorder()
                        .padding(20.0)
                        .build()
            }
        }
    }
}