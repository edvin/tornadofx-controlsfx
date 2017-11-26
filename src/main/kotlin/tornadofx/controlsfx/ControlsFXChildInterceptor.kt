package tornadofx.controlsfx

import javafx.event.EventTarget
import javafx.scene.Node
import javafx.scene.control.ToggleButton
import org.controlsfx.control.InfoOverlay
import org.controlsfx.control.SegmentedButton
import org.controlsfx.control.SnapshotView
import tornadofx.*

class ControlsFXChildInterceptor : ChildInterceptor {
    override fun invoke(parent: EventTarget, node: Node, index: Int?): Boolean = when (parent) {
        is SnapshotView -> {
            parent.node = node
            true
        }
        is InfoOverlay -> {
            parent.run {
                this.content = node
                this.text
            }
            true
        }
        is SegmentedButton -> {
            parent.buttons.add(node as ToggleButton)
        }
        else -> false
    }
}