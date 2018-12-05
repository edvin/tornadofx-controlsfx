package tornadofx.controlsfx.testapps

import javafx.scene.text.Font
import javafx.scene.text.FontWeight
import org.controlsfx.glyphfont.FontAwesome
import tornadofx.*
import tornadofx.controlsfx.*

class TextFieldsApp : App(TextFieldsView::class)

class TextFieldsView : View("Text Fields") {

    val learningWords = mutableListOf<String>()
    override val root = tabpane {
        tab("Autocomplete") {
            form {
                fieldset {
                    field("Autocomplete") {
                        textfield {
                            bindAutoCompletion("AAA", "BBB", "CCC")
                        }
                    }
                    field("Learning") {
                        textfield {
                            bindAutoCompletion(suggestionsList = learningWords) {
                                setDelay(0)
                                onAutoCompleted { println(it.completion) }
                            }
                            setOnKeyPressed {
                                learningWords.add(this@textfield.text.trim())
                            }
                        }

                    }
                }
            }
        }
        tab("Custom TextFields") {
            vbox {
                hbox(10) {
                    label("Text Fields") {
                        gridpaneConstraints { columnIndex = 1 }
                        font = Font.font("arial", FontWeight.BOLD, 24.0)
                    }
                    label("Password Text Fields") {
                        gridpaneConstraints { columnIndex = 2 }
                        font = Font.font("arial", FontWeight.BOLD, 24.0)
                    }
                }
                form {
                    fieldset {
                        field("Normal") {
                            textfield()
                            passwordfield()
                        }
                        field("Clearable") {
                            clearableTextfield()
                            clearablePasswordTextfield()
                        }
                        field("Custom(no additional nodes)") {
                            customTextfield()
                            customPasswordTextfield()
                        }
                        field("Custom with right node") {
                            customTextfield(right = FontAwesome.Glyph.ANCHOR.toGlyph())
                            customPasswordTextfield(right = FontAwesome.Glyph.ANCHOR.toGlyph())
                        }
                        field("Custom with left node") {
                            customTextfield(left = FontAwesome.Glyph.ANCHOR.toGlyph())
                            customPasswordTextfield(left = FontAwesome.Glyph.ANCHOR.toGlyph())
                        }
                        field("Custom with left and right node") {
                            customTextfield(left = FontAwesome.Glyph.ANCHOR.toGlyph(), right = FontAwesome.Glyph.ANCHOR.toGlyph())
                            customPasswordTextfield(left = FontAwesome.Glyph.ANCHOR.toGlyph(), right = FontAwesome.Glyph.ANCHOR.toGlyph())
                        }
                    }
                }

            }
        }
    }

    private fun autoCompletions(): Collection<String> {
        return learningWords
    }
}