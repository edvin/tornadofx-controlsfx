package tornadofx.controlsfx.testapps

import javafx.geometry.Side
import tornadofx.*
import tornadofx.controlsfx.detail
import tornadofx.controlsfx.master
import tornadofx.controlsfx.masterdetailpane

class MasterDetailPaneApp: App(MasterDetailPaneView::class)

class MasterDetailPaneView:View("Master Detail Pane"){
    override val root = masterdetailpane(Side.TOP,true){
        master {
            label("Master") {  }
        }
        detail {
            label("Detail")
        }
    }
}