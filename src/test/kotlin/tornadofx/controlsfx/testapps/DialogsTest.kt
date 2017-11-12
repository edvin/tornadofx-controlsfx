package tornadofx.controlsfx.testapps

import tornadofx.*
import tornadofx.controlsfx.exceptionDialog

import tornadofx.controlsfx.progressDialog
import java.lang.Thread.sleep

class DialogsApp : App(DialogsView::class)

class DialogsView : View("Dialogs") {
    override val root = vbox(10) {
        button("Progress Dialog") {
            action {
                progressDialog {
                    for (r in 0..100) {
                        sleep(100)
                        this.updateProgress(r / 100.0, 1.0)
                    }
                }
            }
        }
        button("Exception Dialog") {
            action {
                exceptionDialog(Exception("Exception"),showAndWait = true)
            }
        }
    }
}