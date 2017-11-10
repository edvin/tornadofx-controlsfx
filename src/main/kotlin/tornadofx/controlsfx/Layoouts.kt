package tornadofx.controlsfx

import javafx.beans.property.BooleanProperty
import javafx.beans.property.ObjectProperty
import javafx.beans.property.Property
import javafx.event.EventTarget
import javafx.geometry.Side
import javafx.scene.Node
import javafx.scene.layout.BorderPane
import org.controlsfx.control.MasterDetailPane
import tornadofx.*
import kotlin.reflect.KFunction1

//region Master Detail Pane
fun EventTarget.masterdetailpane(detailSide: Side, showDetail: Boolean, op: (MasterDetailPane.() -> Unit)? = null): MasterDetailPane =
        opcr(this, MasterDetailPane(detailSide, showDetail), op)

fun EventTarget.masterdetailpane(detailSide: Property<Side>, showDetail: BooleanProperty, op: (MasterDetailPane.() -> Unit)? = null): MasterDetailPane =
        opcr(this, MasterDetailPane().apply {
            detailSideProperty().bind(detailSide)
            showDetailNodeProperty().bind(showDetail)
        }, op)

fun MasterDetailPane.master(op: MasterDetailPane.() -> Unit) = region(MasterDetailPane::masterNodeProperty, op)

fun MasterDetailPane.detail(op: MasterDetailPane.() -> Unit) = region(MasterDetailPane::detailNodeProperty, op)

internal fun MasterDetailPane.region(region: KFunction1<MasterDetailPane, ObjectProperty<Node>>?, op: MasterDetailPane.() -> Unit) {
    builderTarget = region
    op()
    builderTarget = null
}
//endregion