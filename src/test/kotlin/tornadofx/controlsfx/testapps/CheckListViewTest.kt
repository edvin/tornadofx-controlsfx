package tornadofx.controlsfx.testapps

import tornadofx.*
import tornadofx.controlsfx.checklistview

class CheckListViewApp: App(CheckListViewPane::class)

class CheckListViewPane: View() {

     override val root = checklistview(persons) {
         //todo, fix cellFormat?
         /*
         cellFormat {
            text = "$firstName $lastName"
         }
          */
     }
}

data class Person(val firstName: String, val lastName: String) {
    override fun toString() = "$firstName $lastName"
}

val persons = listOf(
        Person("Carl","Maven"),
        Person("Jennifer","Earley"),
        Person("Jonathan","Miles")
).observable()