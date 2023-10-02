package com.corrado4eyes.pistakio.errors

enum class UIElementType {
    SCREEN,
    BUTTON,
    TEXT,
    TEXT_FIELD
}

class ArgumentNotFound(argumentName: String) : Throwable() {
    override val message: String = "Couldn't find the argument called $argumentName"
}

sealed class UIElementException : Throwable() {
    abstract val elementTitle: String
    abstract val elementType: UIElementType
    override val message: String by lazy { "Couldn't find ${elementType.name} $elementTitle" }

    sealed class Screen : UIElementException() {
        override val elementType: UIElementType = UIElementType.SCREEN
        data class NotFound(override val elementTitle: String) : Screen()
    }
    sealed class Button : UIElementException() {
        override val elementType: UIElementType = UIElementType.BUTTON
        data class NotFound(override val elementTitle: String) : Button()
    }
    sealed class Text : UIElementException() {
        override val elementType: UIElementType = UIElementType.TEXT
        data class NotFound(override val elementTitle: String) : Text()
    }
    sealed class TextField : UIElementException() {
        override val elementType: UIElementType = UIElementType.TEXT_FIELD
        data class NotFound(override val elementTitle: String) : TextField()
    }
}
