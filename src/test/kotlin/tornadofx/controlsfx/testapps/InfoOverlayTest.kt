package tornadofx.controlsfx.testapps

import tornadofx.*

import tornadofx.controlsfx.infooverlay

class InfoOverlayApp : App(InfoOverlayView::class)

class InfoOverlayView:View(){
    val info = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
            "Nam tortor felis, pulvinar in scelerisque cursus, pulvinar at ante. " +
            "Nulla consequat congue lectus in sodales."
    override val root = vbox{
        infooverlay(info) {
            isShowOnHover = true
            imageview ("tornado-fx-logo.png"){
                fitToParentSize()
            }
        }

    }
}