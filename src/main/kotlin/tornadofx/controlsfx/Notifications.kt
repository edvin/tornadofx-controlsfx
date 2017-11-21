package tornadofx.controlsfx

import javafx.geometry.Pos
import javafx.scene.Node
import javafx.util.Duration
import org.controlsfx.control.Notifications
import org.controlsfx.control.action.Action


internal fun notification(title: String?,
                          text:String?,
                          graphic: Node?,
                          position: Pos = Pos.BOTTOM_RIGHT,
                          hideAfter: Duration = Duration.seconds(5.0),
                          darkStyle: Boolean = false, owner: Any?, vararg action: Action): Notifications {
    val notification = Notifications
            .create()
            .title(title ?: "")
            .text(text ?: "")
            .graphic(graphic)
            .position(position)
            .hideAfter(hideAfter)
            .action(*action)
    if (owner != null)
        notification.owner(owner)
    if (darkStyle)
        notification.darkStyle()
    return notification
}


fun warningNotification(title: String?,
                        text:String?,
                        position: Pos = Pos.BOTTOM_RIGHT,
                        hideAfter: Duration = Duration.seconds(5.0),
                        darkStyle: Boolean = false, owner: Any? = null, vararg action: Action) {
    notification(title, text,null, position, hideAfter, darkStyle, owner, *action)
            .showWarning()
}

fun infoNotification(title: String?,
                     text:String?,
                     position: Pos = Pos.BOTTOM_RIGHT,
                     hideAfter: Duration = Duration.seconds(5.0),
                     darkStyle: Boolean = false, owner: Any? = null, vararg action: Action) {
    notification(title, text,null, position, hideAfter, darkStyle, owner, *action)
            .showInformation()
}

fun confirmNotification(title: String?,
                        text:String?,
                        position: Pos = Pos.BOTTOM_RIGHT,
                        hideAfter: Duration = Duration.seconds(5.0),
                        darkStyle: Boolean = false, owner: Any? = null, vararg action: Action) {
    notification(title,text, null, position, hideAfter, darkStyle, owner, *action)
            .showConfirm()
}

fun errorNotification(title: String?,
                      text:String?,
                      position: Pos = Pos.BOTTOM_RIGHT,
                      hideAfter: Duration = Duration.seconds(5.0),
                      darkStyle: Boolean = false, owner: Any? = null, vararg action: Action) {
    notification(title,text, null, position, hideAfter, darkStyle, owner, *action)
            .showError()
}

fun customNotification(title: String?,
                       text:String?,
                       graphic: Node,
                       position: Pos = Pos.BOTTOM_RIGHT,
                       hideAfter: Duration = Duration.seconds(5.0),
                       darkStyle: Boolean = false, owner: Any? = null,
                       vararg action: Action) {
    notification(title, text,graphic, position, hideAfter, darkStyle, owner, *action)
            .show()
}