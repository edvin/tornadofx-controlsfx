package tornadofx.controlsfx.testapps

import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.ContextMenu
import javafx.scene.control.Label
import javafx.scene.control.MenuItem
import javafx.scene.control.OverrunStyle
import javafx.scene.layout.Region
import javafx.scene.layout.StackPane
import org.controlsfx.control.SegmentedBar
import tornadofx.*
import tornadofx.Stylesheet.Companion.segment
import tornadofx.controlsfx.segment
import tornadofx.controlsfx.segmentedbar

class SegmentedBarApp : App(SegmentedBarView::class)
enum class MediaType {
    MUSIC,
    VIDEO,
    FREE,
    OTHER,
    PHOTOS,
    APPS
}

class TypeSegmentView(segment: TypeSegment) : StackPane() {

    private val label: Label = Label()

    init {
        label {
            style = "-fx-font-weight: bold; -fx-text-fill: white; -fx-font-size: 1.2em;"
            textOverrun = OverrunStyle.CLIP
            textProperty().bind(segment.textProperty())
            alignment = Pos.CENTER_LEFT
        }
        style = when (segment.type) {
            MediaType.APPS -> "-fx-background-color: orange;"
            MediaType.FREE -> "-fx-border-width: 1px; -fx-background-color: steelblue;"
            MediaType.OTHER -> "-fx-background-color: green;"
            MediaType.PHOTOS -> "-fx-background-color: purple;"
            MediaType.VIDEO -> "-fx-background-color: cadetblue;"
            MediaType.MUSIC -> "-fx-background-color: lightcoral;"
        }
        padding = Insets(5.0)
        prefHeight = 30.0
    }

    override fun layoutChildren() {
        super.layoutChildren()
        label.isVisible = label.prefWidth(-1.0) < width - padding.left - padding.right
    }
}

class TypeSegment(value: Double, val type: MediaType) : SegmentedBar.Segment(value) {

    init {
        text = when (type) {
            MediaType.APPS -> "Apps"
            MediaType.FREE -> "Free"
            MediaType.OTHER -> "Other"
            MediaType.PHOTOS -> "Photos"
            MediaType.VIDEO -> "Video"
            MediaType.MUSIC -> "Music"
        }
    }
}

class SegmentedBarView : View("WorldMapView") {
    private val segments = listOf(TypeSegment(14.0, MediaType.PHOTOS),
            TypeSegment(32.0, MediaType.VIDEO),
            TypeSegment(9.0, MediaType.APPS),
            TypeSegment(40.0, MediaType.MUSIC),
            TypeSegment(5.0, MediaType.OTHER),
            TypeSegment(35.0, MediaType.FREE))
    override val root = vbox {
        segmentedbar(segments) {
            setSegmentViewFactory { TypeSegmentView(it) }
        }
        segmentedbar<SegmentedBar.Segment> {
            segment(10.0, "text")
            segment(25.0, "text")
            segment(40.0, "text")
        }
    }
}