# TornadoFX-ControlsFX

[ControlsFX](http://fxexperience.com/controlsfx/features/) builder extensions and utilities for [TornadoFX](https://github.com/edvin/tornadofx).

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/no.tornado/tornadofx-controlsfx/badge.svg)](https://search.maven.org/#search|ga|1|no.tornado.tornadofx-controlsfx)
[![Apache License](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0)

### Add TornadoFX-ControlsFX to your project

#### Maven

```xml
<dependency>
    <groupId>no.tornado</groupId>
    <artifactId>tornadofx-controlsfx</artifactId>
    <version>0.1</version>
</dependency>
```

#### Gradle

```groovy
compile 'no.tornado:tornadofx-controlsfx:0.1'
```


## Examples

### TableView Filter

```kotlin
class TableFilterView : View() {
    override val root = tableview(patients) {

        column("FIRST NAME", Patient::firstName)
        column("LAST NAME", Patient::lastName)
        column("GENDER", Patient::gender) {
            columnfilter {
                exceptValue(Gender.FEMALE)
            }
        }
        column("BIRTHDAY", Patient::birthday)
        column("AGE", Patient::age)
        column("WBCC", Patient::whiteBloodCellCount)

        tableFilter.executeFilter()
    }
}
```

![](https://i.imgur.com/OZJuBvG.png)

### BreadCrumbBar

```kotlin
class BreadCrumbPane: View() {

    var targetCrumb: TreeItem<String>? = null

    override val root = breadcrumbbar<String> {
        treeitem("Alpha") {
            treeitem("Beta") {
                targetCrumb = treeitem("Gamma")
            }
            treeitem("Zeta")
        }

        selectedCrumb = targetCrumb
    }
}
```

![](https://i.imgur.com/OzxetsK.png)




###


## Contributing

I might have missed some or may have included extraneous utilities, but any help to create a TornadoFX DSL for the following is welcome. 

Please read JavaDocs for full list of controls:
https://controlsfx.bitbucket.io/


* [X] BreadCrumbBar
* [X] Borders
* [X] CheckListView
* [X] CheckComboBox
* [ ] CheckTreeView
* [X] Glyphs
* [X] CustomPasswordField
* [X] CustomTextField
* [ ] Picker
* [X] ProgressDialog
* [X] ExceptionDialog
* [X] TableFilter
* [X] RangeSlider
* [ ] SpreadsheetView
* [X] StatusBar
* [X] ToggleSwitch
* [X] WorldMapView
* [ ] SegmentedBar
* [X] Rating
* [X] PropertySheet
* [X] PopOver
* [X] Plus/Minus Slider
* [X] Notifications
* [X] NotificationsPane
* [X] MasterDetailPane
* [X] MaskerPane
* [X] HyperlinkLabel
* [ ] GridView
* [X] FontAwesome
* [ ] Decorator
* [X] InfoOverlay
* [X] ListSelectionView
* [X] Prefix Selection Combobox/Choicebox
* [X] SnapshotView
* [X] HiddenSidesPane
* [X] LoginDialog
* [X] CommandLinksDialog
