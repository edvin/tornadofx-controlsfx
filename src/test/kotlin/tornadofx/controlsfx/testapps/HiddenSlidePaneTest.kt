package tornadofx.controlsfx.testapps

import javafx.geometry.Pos
import javafx.geometry.Side
import tornadofx.*
import tornadofx.controlsfx.*

class HiddenSlidePaneApp : App(HiddenSlidePaneView::class)

class HiddenSlidePaneView : View("HiddenSlidePane") {
    override val root = hiddensidepane {
        top {
            label("top"){
                alignment = Pos.CENTER
                style = "-fx-background-color: rgba(0,255,0,.25);"
                setPrefSize(200.0,200.0)
                setOnMouseClicked{
                    pinSide = Side.TOP
                }
            }
        }
        left {
            label("left"){
                style = "-fx-background-color: rgba(255,0,0,.25);"
                alignment = Pos.CENTER
                setPrefSize(200.0,200.0)
                setOnMouseClicked{
                    pinSide = Side.LEFT
                }
            }
        }
        right {
            label("right"){
                style = "-fx-background-color: rgba(0,0, 255,.25);"
                alignment = Pos.CENTER
                setPrefSize(200.0,200.0)
                setOnMouseClicked{
                    pinSide = Side.RIGHT
                }
            }
        }
        bottom {
            label("bottom"){
                style = "-fx-background-color: rgba(255,255,0,.25);"
                alignment = Pos.CENTER
                setPrefSize(200.0,200.0)
                setOnMouseClicked{
                    pinSide = Side.BOTTOM
                }
            }
        }
        setPrefSize(800.0,600.0)
        form{
            fieldset {
                field("Something inside HiddenSlidePane"){
                    textfield {  }
                }
            }
        }

    }
}