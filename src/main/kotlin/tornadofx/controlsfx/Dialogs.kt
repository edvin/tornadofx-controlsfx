package tornadofx.controlsfx

import javafx.scene.Node
import javafx.scene.control.Dialog
import javafx.util.Callback
import javafx.util.Pair
import org.controlsfx.dialog.CommandLinksDialog
import org.controlsfx.dialog.ExceptionDialog
import org.controlsfx.dialog.LoginDialog
import org.controlsfx.dialog.ProgressDialog
import tornadofx.*

private fun show(dialog: Dialog<*>, showAndWait: Boolean) {
    if (showAndWait)
        dialog.showAndWait()
    else
        dialog.show()
}

//region Progress Dialog
fun <T> progressDialog(func: FXTask<*>.() -> T): T? {
    val task = task(true, func = func)
    val dialog = ProgressDialog(task)
    show(dialog, true)
    return task.get()
}

//endregion
//region Exception Dialog
fun exceptionDialog(exception: Throwable, showAndWait: Boolean = false) {
    val dialog = ExceptionDialog(exception)
    show(dialog, showAndWait)
}

//endregion

//region LoginDialog
fun loginDialog(username: String? = null, password: String? = null, showAndWait: Boolean = false, authenticator: (String?, String?) -> Unit) {
    val pair = if (username == null && password == null)
        null
    else
        javafx.util.Pair(username, password)
    val loginDialog = LoginDialog(pair,
            Callback<Pair<String, String>?, Void?> { param -> authenticator(param?.key, param?.value);null }
    )
    show(loginDialog, showAndWait)

}

//endregion
//region CommandLinksDialog
fun commandLinksDialog(vararg commandLink: CommandLinksDialog.CommandLinksButtonType, showAndWait: Boolean = false): CommandLinksDialog {
    val dialog = CommandLinksDialog(*commandLink)
    show(dialog, showAndWait)
    return dialog
}

fun commandLinksDialog(commandLinks: List<CommandLinksDialog.CommandLinksButtonType>, showAndWait: Boolean = false): CommandLinksDialog {
    val dialog = CommandLinksDialog(commandLinks)
    show(dialog, showAndWait)
    return dialog
}

class CommandLinksDialogCollector {
    val commandLinks = mutableListOf<CommandLinksDialog.CommandLinksButtonType>()
    fun commandlink(text: String, isDefault: Boolean) {
        val commandLinksButtonType = CommandLinksDialog.CommandLinksButtonType(text, isDefault)
        commandLinks.add(commandLinksButtonType)
    }

    fun commandlink(text: String, longText: String, isDefault: Boolean) {
        val commandLinksButtonType = CommandLinksDialog.CommandLinksButtonType(text, longText, isDefault)
        commandLinks.add(commandLinksButtonType)
    }

    fun commandlink(text: String, longText: String, graphic: Node, isDefault: Boolean) {
        val commandLinksButtonType = CommandLinksDialog.CommandLinksButtonType(text, longText, graphic, isDefault)
        commandLinks.add(commandLinksButtonType)
    }

}

fun commandLinksDialog(showAndWait: Boolean = false, op: CommandLinksDialogCollector.() -> Unit): CommandLinksDialog {
    val dummy = CommandLinksDialogCollector()
    dummy.op()
    val dialog = CommandLinksDialog(dummy.commandLinks)
    show(dialog, showAndWait)
    return dialog
}


//endregion