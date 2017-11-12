package tornadofx.controlsfx

import org.controlsfx.dialog.ExceptionDialog
import org.controlsfx.dialog.ProgressDialog
import tornadofx.*

//region Progress Dialog
fun <T> progressDialog(func: FXTask<*>.() -> T): T? {
    val task = task(true, func = func)
    val dialog = ProgressDialog(task)
    dialog.showAndWait()
    return task.get()
}
//endregion

//region Exception Dialog
fun exceptionDialog(exception: Throwable, showAndWait: Boolean = false) {
    val dialog = ExceptionDialog(exception)
    if (showAndWait)
        dialog.showAndWait()
    else
        dialog.show()
}
//endregion