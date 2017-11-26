package tornadofx.controlsfx

import javafx.beans.property.BooleanProperty
import javafx.beans.property.ObjectProperty
import javafx.beans.property.Property
import javafx.event.EventTarget
import javafx.geometry.Rectangle2D
import javafx.geometry.Side
import javafx.scene.Node
import org.controlsfx.control.HiddenSidesPane
import org.controlsfx.control.MasterDetailPane
import org.controlsfx.control.NotificationPane
import org.controlsfx.control.SnapshotView
import org.controlsfx.tools.Borders
import tornadofx.*
import kotlin.reflect.KFunction1

//region Master Detail Pane
fun EventTarget.masterdetailpane(detailSide: Side, showDetail: Boolean, dividerPosition: Double = 0.5, op: (MasterDetailPane.() -> Unit) = {}): MasterDetailPane =
        opcr(this, MasterDetailPane(detailSide, showDetail).apply {
            this.dividerPosition = dividerPosition
        }, op)

fun EventTarget.masterdetailpane(detailSide: Property<Side>? = null,
                                 showDetail: BooleanProperty? = null,
                                 dividerPosition: Property<Number>? = null,
                                 op: (MasterDetailPane.() -> Unit) = {}): MasterDetailPane =
        opcr(this, MasterDetailPane().apply {
            if (detailSide != null) detailSideProperty().bindBidirectional(detailSide)
            if (showDetail != null) showDetailNodeProperty().bindBidirectional(showDetail)
            if (dividerPosition != null) dividerPositionProperty().bindBidirectional(dividerPosition)
        }, op)

fun MasterDetailPane.master(op: MasterDetailPane.() -> Unit) = region(MasterDetailPane::masterNodeProperty, op)

fun MasterDetailPane.detail(op: MasterDetailPane.() -> Unit) = region(MasterDetailPane::detailNodeProperty, op)

internal fun MasterDetailPane.region(region: KFunction1<MasterDetailPane, ObjectProperty<Node>>?, op: MasterDetailPane.() -> Unit) {
    builderTarget = region
    op()
    builderTarget = null
}

//endregion

//region NotificationsPane
fun EventTarget.notificationPane(showFromTop: Boolean = true, isShowing: Property<Boolean>? = null, op: (NotificationPane.() -> Unit) = {}): NotificationPane {
    val notificationPane = NotificationPane().apply {
        isShowFromTop = showFromTop
        if (isShowing != null) isShowing.bind(this.showingProperty())
    }
    return opcr(this, notificationPane, op)
}

fun NotificationPane.content(op: NotificationPane.() -> Unit) = region(NotificationPane::contentProperty, op)

internal fun NotificationPane.region(region: KFunction1<NotificationPane, ObjectProperty<Node>>?, op: NotificationPane.() -> Unit) {
    builderTarget = region
    op()
    builderTarget = null
}
//endregion

//region HiddenSidesPane
fun EventTarget.hiddensidepane(op: (HiddenSidesPane.() -> Unit) = {}) = opcr(this, HiddenSidesPane(), op)

var HiddenSidesPane.pinSide: Side
    get() = pinnedSide
    set(value) {
        if (pinnedSide == value)
            pinnedSide = null
        else
            pinnedSide = value
    }

fun HiddenSidesPane.top(op: HiddenSidesPane.() -> Unit) = region(HiddenSidesPane::topProperty, op)
fun HiddenSidesPane.left(op: HiddenSidesPane.() -> Unit) = region(HiddenSidesPane::leftProperty, op)
fun HiddenSidesPane.right(op: HiddenSidesPane.() -> Unit) = region(HiddenSidesPane::rightProperty, op)
fun HiddenSidesPane.bottom(op: HiddenSidesPane.() -> Unit) = region(HiddenSidesPane::bottomProperty, op)

internal fun HiddenSidesPane.region(region: KFunction1<HiddenSidesPane, ObjectProperty<Node>>?, op: HiddenSidesPane.() -> Unit) {
    builderTarget = region
    op()
    builderTarget = null
}
//endregion

//region SnapshotView
fun EventTarget.snapshotview(op: (SnapshotView.() -> Unit) = {}): SnapshotView {

    val snapshotView = SnapshotView()

    return opcr(this, snapshotView, op)
}

fun EventTarget.snapshotview(selectionProperty: Property<Rectangle2D>, op: (SnapshotView.() -> Unit) = {}): SnapshotView {
    val snapshotView = SnapshotView().apply {
        selectionProperty().bindBidirectional(selectionProperty)
    }

    return opcr(this, snapshotView, op)
}
//endregion

//region Borders

fun Node.borders(op: (Borders.() -> Borders)? = null): Node {
    val currentParent = this.parent
    removeFromParent()
    val wrappedNode = Borders.wrap(this)
    op?.invoke(wrappedNode)
    return opcr(currentParent, wrappedNode.build())
}

//endregion