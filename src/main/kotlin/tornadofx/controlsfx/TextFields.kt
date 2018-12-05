package tornadofx.controlsfx

import impl.org.controlsfx.autocompletion.SuggestionProvider
import javafx.beans.property.Property
import javafx.event.EventTarget
import javafx.scene.Node
import javafx.scene.control.TextField
import javafx.util.StringConverter
import org.controlsfx.control.textfield.AutoCompletionBinding
import org.controlsfx.control.textfield.CustomPasswordField
import org.controlsfx.control.textfield.CustomTextField
import org.controlsfx.control.textfield.TextFields
import tornadofx.*

fun <T> TextField.bindAutoCompletion(vararg suggestions: T, op: (AutoCompletionBinding<T>.() -> Unit)? = null) {
    val binding = TextFields.bindAutoCompletion(this, *suggestions)
    if (op != null) binding.apply(op)
}

fun <T> TextField.bindAutoCompletion(suggestionsList: List<T>, op: (AutoCompletionBinding<T>.() -> Unit)? = null) {
    val binding = TextFields.bindAutoCompletion(this, suggestionsList)
    if (op != null) binding.apply(op)
}

fun <T> TextField.bindAutoCompletion(suggestionsProvider: () -> Collection<T>, op: (AutoCompletionBinding<T>.() -> Unit)? = null) {
    val binding = TextFields.bindAutoCompletion(this) { suggestionsProvider() }
    if (op != null) binding.apply(op)
}

fun <T> TextField.bindAutoCompletion(suggestionsProvider: () -> Collection<T>, converter: StringConverter<T>, op: (AutoCompletionBinding<T>.() -> Unit)? = null) {
    val binding = TextFields.bindAutoCompletion(this, { suggestionsProvider() }, converter)
    if (op != null) binding.apply(op)
}

fun <T> TextField.bindAutoCompletion(suggestionProvider: SuggestionProvider<T>, op: (AutoCompletionBinding<T>.() -> Unit)? = null) {
    val binding = TextFields.bindAutoCompletion(this, suggestionProvider)
    if (op != null) binding.apply(op)
}

fun <T> AutoCompletionBinding<T>.onAutoCompleted(op: (AutoCompletionBinding.AutoCompletionEvent<T>) -> Unit) = setOnAutoCompleted { op(it) }

fun EventTarget.clearableTextfield(op: (TextField.() -> Unit) = {}): TextField =
        opcr(this, TextFields.createClearableTextField(), op)

fun EventTarget.clearablePasswordTextfield(op: (TextField.() -> Unit) = {}): TextField =
        opcr(this, TextFields.createClearablePasswordField(), op)

fun EventTarget.customTextfield(op: (CustomTextField.() -> Unit) = {}): CustomTextField {
    val customTextField = CustomTextField()
    return opcr(this, customTextField, op)
}

fun EventTarget.customPasswordTextfield(op: (CustomPasswordField.() -> Unit) = {}): CustomPasswordField {
    val customPasswordField = CustomPasswordField().apply {
        this.left = left
        this.right = right
    }
    return opcr(this, customPasswordField, op)
}

fun EventTarget.customTextfield(left: Node? = null, right: Node? = null, op: (CustomTextField.() -> Unit) = {}): CustomTextField {
    val customTextField = CustomTextField().apply {
        this.left = left
        this.right = right
    }
    return opcr(this, customTextField, op)
}

fun EventTarget.customPasswordTextfield(left: Node? = null, right: Node? = null, op: (CustomPasswordField.() -> Unit) = {}): CustomPasswordField {
    val customPasswordField = CustomPasswordField().apply {
        this.left = left
        this.right = right
    }
    return opcr(this, customPasswordField, op)
}

fun EventTarget.customTextfield(left: Property<Node>? = null, right: Property<Node>? = null, op: (CustomTextField.() -> Unit) = {}): CustomTextField {
    val customTextField = CustomTextField().apply {
        if (left != null) this.leftProperty().bindBidirectional(left)
        if (right != null) this.rightProperty().bindBidirectional(right)
    }
    return opcr(this, customTextField, op)
}

fun EventTarget.customPasswordTextfield(left: Property<Node>? = null, right: Property<Node>? = null, op: (CustomPasswordField.() -> Unit) = {}): CustomPasswordField {
    val customPasswordField = CustomPasswordField().apply {
        if (left != null) this.leftProperty().bindBidirectional(left)
        if (right != null) this.rightProperty().bindBidirectional(right)
    }
    return opcr(this, customPasswordField, op)
}