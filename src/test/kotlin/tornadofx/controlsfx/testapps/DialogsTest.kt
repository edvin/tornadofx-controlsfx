package tornadofx.controlsfx.testapps

import javafx.scene.control.Alert
import org.controlsfx.dialog.CommandLinksDialog
import tornadofx.*
import tornadofx.controlsfx.*

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
                exceptionDialog(Exception("Exception"), showAndWait = true)
            }
        }
        button("Command Links Dialog") {
            action {
                commandLinksDialog(showAndWait = true) {
                    commandlink("Add a network that is in the range of this computer",
                            "This shows you a list of networks that are currently available and lets you connect to one.", false)
                    commandlink(
                            "Add a network that is in the range of this computer",
                            "This shows you a list of networks that are currently available and lets you connect to one.", false)
                    commandlink(
                            "Manually create a network profile",
                            "This creates a new network profile or locates an existing one and saves it on your computer",
                            true /*default*/)
                    commandlink("Create an ad hoc network",
                            "This creates a temporary network for sharing files or and Internet connection", false)
                }
            }
            button("Login Dialog") {
                action {
                    loginDialog(showAndWait = true) { username, password ->
                        if (username == "username" && password == "password")
                            alert(Alert.AlertType.INFORMATION, "Login Succeeded")
                        else
                            alert(Alert.AlertType.ERROR, "Login Failed")
                    }
                }
            }
        }
    }
}