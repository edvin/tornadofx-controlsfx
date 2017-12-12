package tornadofx.controlsfx.testapps

import javafx.beans.property.SimpleStringProperty
import javafx.geometry.Side
import tornadofx.*
import tornadofx.controlsfx.detail
import tornadofx.controlsfx.master
import tornadofx.controlsfx.masterdetailpane

class LocalPerson(name: String, familyName: String) {
    val nameProperty = SimpleStringProperty(name)
    var name by nameProperty
    val familyNameProperty = SimpleStringProperty(familyName)
    var familyName by familyNameProperty
}

class PersonModel : ItemViewModel<LocalPerson>() {
    val name = bind(LocalPerson::nameProperty)
    val familyName = bind(LocalPerson::familyNameProperty)
}


class MainApp : App(MainView::class)

class MainView : View("View") {
    val persons = listOf(LocalPerson("aaa", "aaa"), LocalPerson("bbb", "bbbb"))
    val personViewModel by inject<PersonModel>()
    override val root = masterdetailpane(dividerPosition = 0.5) {

        master {
            tableview(persons.observable()) {
                column("Name", LocalPerson::name)
                column("Family Name", LocalPerson::familyName)
                bindSelected(personViewModel)
            }
        }
        detail {
            form {
                fieldset {
                    field("Name") {
                        textfield(personViewModel.name)
                    }
                    field("Family Name") {
                        textfield(personViewModel.familyName)
                    }
                }
            }
        }
    }

}