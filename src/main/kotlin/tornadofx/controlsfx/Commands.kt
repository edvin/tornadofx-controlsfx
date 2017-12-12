@file:Suppress("UNCHECKED_CAST")

package tornadofx.controlsfx

import javafx.beans.binding.BooleanBinding
import javafx.beans.property.ObjectProperty
import javafx.beans.property.Property
import javafx.beans.property.SimpleObjectProperty
import org.controlsfx.control.HyperlinkLabel
import tornadofx.*

internal val ControlFxCommandKey: String = "tornadofx.controlsfx.Command"
internal val ControlFxCommandParameterKey: String = "tornadofx.controlsfx.Command"

val Command<*>.disabledProperty: BooleanBinding
    get() = enabled.not().or(running)

var HyperlinkLabel.commandProperty: ObjectProperty<Command<*>>
    get() = properties.getOrPut(ControlFxCommandKey) {
        SimpleObjectProperty<Command<*>>().apply {
            onChange {
                if (it == null) disableProperty().unbind()
                else disableProperty().cleanBind(it.disabledProperty)
            }
            this@commandProperty.action { (value as? Command<Any?>)?.execute(commandParameter ?: text) }
        }
    } as ObjectProperty<Command<*>>
    set(value) {
        properties.put(ControlFxCommandKey, value)
    }

var HyperlinkLabel.command: Command<*>
    get() = commandProperty.value
    set(value) {

        if (value is CommandWithParameter) {
            commandProperty.value = value.command
            commandParameter = value.parameter
        } else {
            commandProperty.value = value as Command<Any?>
        }
    }

var HyperlinkLabel.commandParameterProperty: Property<Any?>
    get() = properties.getOrPut(ControlFxCommandParameterKey) {
        SimpleObjectProperty<Any?>()
    } as Property<Any?>
    set(value) {
        properties.put(ControlFxCommandParameterKey, value)
    }

var HyperlinkLabel.commandParameter: Any?
    get() = commandParameterProperty.value
    set(value) {
        if (value is Property<*>) {
            commandParameterProperty = value as Property<Any?>
        } else {
            commandParameterProperty.value = value
        }
    }