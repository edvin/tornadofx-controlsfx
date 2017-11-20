package tornadofx.controlsfx.testapps

import javafx.animation.AnimationTimer
import javafx.beans.property.SimpleObjectProperty
import javafx.geometry.Side
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import org.controlsfx.control.SnapshotView
import tornadofx.*
import tornadofx.controlsfx.*

class SnapshotViewApp : App(SnapshotViewTest::class){
    init {
        FX.addChildInterceptor = DEFAULT_CONTROLFX_CHILD_INTERCEPTOR
    }
}

class SnapshotViewTest : View("SnapshotView") {
    val selectedImageProperty = SimpleObjectProperty<Image>()
    var selectedImage by selectedImageProperty
    var snapshotView by singleAssign<SnapshotView>()

    override val root = masterdetailpane(Side.RIGHT, showDetail = true) {
        dividerPosition = 0.5
        master {
            setPrefSize(400.0,600.0)
            snapshotView = snapshotview {
                imageview("tornado-fx-logo.png")
            }
        }
        detail {
            vbox {
                fitToParentSize()
                imageview(selectedImageProperty) {
                   fitToParentSize()
                }
            }
        }
    }
    val timer = object : AnimationTimer() {
        override fun handle(timestamp: Long) {
            if (snapshotView.node != null && snapshotView.hasSelection()) {
                selectedImage = snapshotView.createSnapshot()
            }

        }
    }
    override fun onDock() {
        timer.start()
    }

    override fun onUndock() {
        timer.stop()
    }
}