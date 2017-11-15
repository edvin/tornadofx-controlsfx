package tornadofx.controlsfx

import impl.org.controlsfx.autocompletion.SuggestionProvider
import javafx.beans.property.Property
import javafx.event.EventTarget
import javafx.scene.Node
import javafx.scene.control.TextField
import javafx.util.StringConverter
import org.controlsfx.control.textfield.CustomPasswordField
import org.controlsfx.control.textfield.CustomTextField
import org.controlsfx.control.textfield.TextFields
import tornadofx.*

fun <T> TextField.bindAutoCompletion(vararg suggestions: T) {
    TextFields.bindAutoCompletion(this, *suggestions)
}

fun <T> TextField.bindAutoCompletion(suggestions: List<T>) {
    TextFields.bindAutoCompletion(this, suggestions)
}

fun <T> TextField.bindAutoCompletion(suggestionsProvider: () -> Collection<T>) {
    TextFields.bindAutoCompletion(this, { suggestionsProvider() })
}

fun <T> TextField.bindAutoCompletion(suggestionsProvider: () -> Collection<T>, converter: StringConverter<T>) {
    TextFields.bindAutoCompletion(this, { suggestionsProvider() }, converter)
}

fun <T> TextField.bindAutoCompletion(suggestionProvider: SuggestionProvider<T>) {
    TextFields.bindAutoCompletion(this, suggestionProvider)
}

fun EventTarget.clearableTextfield(op: (TextField.() -> Unit)? = null): TextField =
        opcr(this, TextFields.createClearableTextField(), op)

fun EventTarget.clearablePasswordTextfield(op: (TextField.() -> Unit)? = null): TextField =
        opcr(this, TextFields.createClearablePasswordField(), op)

fun EventTarget.customTextfield(op: (CustomTextField.() -> Unit)? = null): CustomTextField {
    val customTextField = CustomTextField()
    return opcr(this, customTextField, op)
}

fun EventTarget.customPasswordTextfield(op: (CustomPasswordField.() -> Unit)? = null): CustomPasswordField {
    val customPasswordField = CustomPasswordField().apply {
        this.left = left
        this.right = right
    }
    return opcr(this, customPasswordField, op)
}

fun EventTarget.customTextfield(left: Node? = null, right: Node? = null, op: (CustomTextField.() -> Unit)? = null): CustomTextField {
    val customTextField = CustomTextField().apply {
        this.left = left
        this.right = right
    }
    return opcr(this, customTextField, op)
}

fun EventTarget.customPasswordTextfield(left: Node? = null, right: Node? = null, op: (CustomPasswordField.() -> Unit)? = null): CustomPasswordField {
    val customPasswordField = CustomPasswordField().apply {
        this.left = left
        this.right = right
    }
    return opcr(this, customPasswordField, op)
}

fun EventTarget.customTextfield(left: Property<Node>? = null, right: Property<Node>? = null, op: (CustomTextField.() -> Unit)? = null): CustomTextField {
    val customTextField = CustomTextField().apply {
        if (left != null) this.leftProperty().bindBidirectional(left)
        if (right != null) this.rightProperty().bindBidirectional(right)
    }
    return opcr(this, customTextField, op)
}

fun EventTarget.customPasswordTextfield(left: Property<Node>? = null, right: Property<Node>? = null, op: (CustomPasswordField.() -> Unit)? = null): CustomPasswordField {
    val customPasswordField = CustomPasswordField().apply {
        if (left != null) this.leftProperty().bindBidirectional(left)
        if (right != null) this.rightProperty().bindBidirectional(right)
    }
    return opcr(this, customPasswordField, op)
}