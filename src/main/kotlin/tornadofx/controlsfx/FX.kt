package tornadofx.controlsfx

import javafx.event.EventTarget
import javafx.scene.Node
import org.controlsfx.control.InfoOverlay
import org.controlsfx.control.SnapshotView

val DEFAULT_CONTROLFX_CHILD_INTERCEPTOR = { parent: EventTarget, node: Node, index: Int? ->
    when (parent) {
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
        else -> false
    }
}
