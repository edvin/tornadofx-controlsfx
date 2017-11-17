package tornadofx.controlsfx.testapps

import javafx.animation.PauseTransition
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleListProperty
import javafx.collections.FXCollections
import javafx.scene.control.ComboBox
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import javafx.util.Duration
import tornadofx.*
import tornadofx.controlsfx.lookup
import tornadofx.controlsfx.prefixselectionchoicebox
import tornadofx.controlsfx.prefixselectioncombobox
import java.util.*

class PrefixSelectionApp : App(PrefixSelectionView::class)

class PrefixSelectionView : View("PrefixSelection") {
    private val items = SimpleListProperty<String>(listOf("AAA", "BBB", "CCC").observable())
    val persons = SimpleListProperty<Person>(listOf(Person("AAA", "AAA"),
            Person("BBB", "BBB"),
            Person("CCC", "CCC"))
            .observable())
    private val stringEditableCombobox = SimpleBooleanProperty(false)
    val personEditableCombobox = SimpleBooleanProperty(false)
    override val root = form {
        fieldset {
            field("ChoiceBox<String>") {
                prefixselectionchoicebox<String>(items)
            }
            field("ChoiceBox<String>") {
                prefixselectioncombobox<String>(items) {
                    editableProperty().bind(stringEditableCombobox)
                }
                checkbox("Make combox editable", stringEditableCombobox)

            }
            field("ComboxBox<Person>") {
                prefixselectioncombobox<Person>(items = persons) {
                    editableProperty().bind(personEditableCombobox)
                }
                checkbox("Make combox editable", personEditableCombobox)
            }

            field("Press Tab") {
                val arrayList = SimpleListProperty(FXCollections.observableArrayList(
                        "00 Abb", "01 Acc", "02 Add", "10 Baa", "11 Bcc", "12 Bdd", "13 Bee",
                        "20 Caa", "21 Cbb", "22 Cdd", "23 Cee", "24 Cff", "30 Daa"))
                prefixselectioncombobox(items = arrayList) {
                    isDisplayOnFocusedEnabled = true
                    isBackSpaceAllowed = true
                    typingDelay = 1000
                    lookup { str ->
                        items.asSequence()
                                .map { converter.toString(it) }
                                .filterNotNull()
                                .filterNot { it.isEmpty() }
                                .map { it.toUpperCase() }
                                .filter { s ->
                                    if (!str.isEmpty()) {
                                        val firstLetter = str.substring(0, 1).toUpperCase(Locale.ROOT)
                                        val numberOfDigits = 2
                                        val numberOfOccurrences = str.toUpperCase(Locale.ROOT).replaceFirst(".*?($firstLetter+).*".toRegex(), "$1").length
                                        if (isValidNumber(str, numberOfDigits) && s.startsWith(str)) {
                                            // two digits: select that line and tab to the next field
                                            commitSelection<String>(this)
                                            return@filter true
                                        } else if (s.substring(numberOfDigits + 1).startsWith(str.toUpperCase(Locale.ROOT))) {
                                            // alpha characters: highlight closest (first) match
                                            return@filter true
                                        } else if (numberOfOccurrences > 1 && s.substring(numberOfDigits + 1, numberOfDigits + 2) == firstLetter) {
                                            val numberOfItems = getItemsByLetter<String>(this, firstLetter, numberOfDigits).size
                                            val index = getItemsByLetter<String>(this, firstLetter, numberOfDigits).indexOf(s)
                                            // repeated alpha characters: highlight match based on order
                                            if (index == (numberOfOccurrences - 1) % numberOfItems) {
                                                return@filter true
                                            }
                                        }
                                    }
                                    false
                                }
                                .firstOrNull()
                    }
                }
            }

        }
    }

    private fun isValidNumber(prefix: String?, numberOfDigits: Int): Boolean {
        if (prefix == null || prefix.length != numberOfDigits) {
            return false
        }
        return try {
            Integer.parseInt(prefix)
            true
        } catch (nfe: NumberFormatException) {
            false
        }

    }

    private fun <T> commitSelection(combo: ComboBox<T>?) {
        if (combo == null) {
            return
        }
        val pause = PauseTransition(Duration.millis(200.toDouble()))
        pause.setOnFinished {
            combo.hide()
            combo.fireEvent(KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.TAB, false, false, false, false))
        }
        pause.playFromStart()
    }

    private fun <T> getItemsByLetter(combo: ComboBox<T>?, firstLetter: String, numberOfDigits: Int): List<T> {
        return combo?.items?.asSequence()?.filter { item ->
            val s = combo.converter.toString(item)
            s != null && !s.isEmpty() && s.length >= numberOfDigits + 2 &&
                    s.substring(numberOfDigits + 1, numberOfDigits + 2).toUpperCase(Locale.ROOT) == firstLetter
        }?.toList() ?: ArrayList()
    }
}