package tornadofx.controlsfx.testapps

import tornadofx.*
import tornadofx.controlsfx.segmentedbutton

class SegmentedButtonApp: App(SegmentedbuttonView::class)

class SegmentedbuttonView: View() {

    override val root = segmentedbutton {
        button("YES")
        button("NO")
        button("MAYBE")

    }
}
