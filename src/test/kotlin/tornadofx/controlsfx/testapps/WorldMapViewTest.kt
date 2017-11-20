package tornadofx.controlsfx.testapps

import javafx.scene.control.Alert
import javafx.scene.control.Tooltip
import javafx.scene.effect.DropShadow
import javafx.scene.shape.Circle
import tornadofx.*
import tornadofx.controlsfx.worldmapView
import org.controlsfx.control.WorldMapView
import org.controlsfx.glyphfont.FontAwesome
import tornadofx.controlsfx.countryViewFactory
import tornadofx.controlsfx.locationViewFactory
import tornadofx.controlsfx.toGlyph

class WorldMapViewApp : App(WorldMapViewView::class)

class WorldMapViewView : View("WorldMapView") {
    val localLocations = listOf(WorldMapView.Location("SFO", 37.619751, -122.374366),
            WorldMapView.Location("YYC", 51.128148, -114.010791),
            WorldMapView.Location("ORD", 41.975806, -87.905294),
            WorldMapView.Location("YOW", 45.321867, -75.668200),
            WorldMapView.Location("JFK", 40.642660, -73.781232),
            WorldMapView.Location("GRU", -23.427337, -46.478853),
            WorldMapView.Location("RKV", 64.131830, -21.945686),
            WorldMapView.Location("MAD", 40.483162, -3.579211),
            WorldMapView.Location("CDG", 49.014162, 2.541908),
            WorldMapView.Location("LHR", 51.471125, -0.461951),
            WorldMapView.Location("SVO", 55.972401, 37.412537),
            WorldMapView.Location("DEL", 28.555839, 77.100956),
            WorldMapView.Location("PEK", 40.077624, 116.605458),
            WorldMapView.Location("NRT", 35.766948, 140.385254),
            WorldMapView.Location("SYD", -33.939040, 151.174996))
    override val root = vbox {
        worldmapView(localLocations) {
            countryViewFactory { country ->
                WorldMapView.CountryView(country)
            }
            locationViewFactory { location ->
                val tooltip = Tooltip()
                if (location.name == "RKV") {
                    val glyph = FontAwesome.Glyph.STAR.toGlyph {
                        fontSize = 32.0
                        style = "-fx-text-fill: yellow; -fx-stroke: orange;"
                        effect = DropShadow()
                        translateX = -8.0
                        translateY = -8.0

                        setOnMouseClicked({ _ ->
                            val alert = Alert(Alert.AlertType.INFORMATION)
                            alert.title = "World Map Info"
                            alert.contentText = "This is Reykjavík, the capitol of Iceland!"
                            alert.show()
                        })
                        setOnMouseEntered({ _ -> tooltip.text = location.name })
                    }
                    Tooltip.install(glyph, tooltip)
                    glyph
                } else {
                    val circle = Circle().apply {
                        styleClass.add("location")
                        radius = 4.0
                        translateX = -4.0
                        translateY = -4.0
                        setOnMouseEntered { _ ->
                            println(tooltip)
                            println(location)
                            tooltip.text = location.name
                        }
                    }
                    Tooltip.install(circle, tooltip)
                    circle
                }

            }
        }
    }
}