package tornadofx.controlsfx.testapps

import button
import segmentedbutton
import tornadofx.*

class SegmentedButtonApp: App(SegmentedbuttonView::class)

class SegmentedbuttonView: View() {

    override val root = segmentedbutton {
        button("YES")
        button("NO")
        button("MAYBE")

    }
}
