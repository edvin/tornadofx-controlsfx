package tornadofx.controlsfx.testapps

import javafx.scene.control.TreeItem
import tornadofx.*
import tornadofx.controlsfx.breadcrumbbar
import tornadofx.controlsfx.treeitem


class BreadCrumbApp: App(BreadCrumbPane::class)

class BreadCrumbPane: View() {

    var targetCrumb: TreeItem<String>? = null

    override val root = breadcrumbbar<String> {
        treeitem("Alpha") {
            treeitem("Beta") {
                targetCrumb = treeitem("Gamma") {

                }
            }
            treeitem("Zeta")
        }

        selectedCrumb = targetCrumb
    }
}
