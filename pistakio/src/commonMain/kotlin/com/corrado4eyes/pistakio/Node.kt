package com.corrado4eyes.pistakio

sealed class AssertionResult {
    class Success : AssertionResult()
    class Failure(val exception: AssertionError) : AssertionResult()
}

interface Node {
    fun typeText(text: String)
    fun tap()

    /**
     * If the [Node] exists at the moment of the call, it returns [AssertionResult.Success].
     * Otherwise it returns [AssertionResult.Failure].
     */
    fun exists(): AssertionResult

    /**
     * Wait for an [Node] to appear in the view for the given [timeout] time.
     * Returns an [AssertionResult.Failure] if the [Node] is not found or [AssertionResult.Success] instead.
     * @param timeout Duration in seconds for the [Node] to appear in the view
     */
    fun waitExists(timeout: TimeoutDuration): AssertionResult

    /**
     * Returns [AssertionResult.Success] when the [Node] exists and is visible on the view.
     * If the [Node] exists, but is not visible because is hidden by other views,
     * it returns [AssertionResult.Failure]. Also if an [Node] exists in a ScrollView and when the
     * method is called that [Node] is not visible, it will return [AssertionResult.Failure].
     */
    fun isVisible(): AssertionResult

    /**
     * Returns [AssertionResult.Success] if the node is a button and possesses a click property.
     * Otherwise it returns [AssertionResult.Failure].
     */
    fun isButton(): AssertionResult

    /**
     * Validates whether a [Node]'s value is equal to a given text.
     * @param value Expected content of the [Node].
     * @param contains If true it will try to find a match in the substrings of the text.
     */
    fun isTextEqualTo(value: String, contains: Boolean): AssertionResult

    /**
     * Validates whether a [Node]'s value is equal to a given hint.
     * @param value Expected content of the [Node].
     * @param contains If true it will try to find a match in the substrings of the hint.
     */
    fun isHintEqualTo(value: String, contains: Boolean): AssertionResult

    /**
     * Does a swipeUp action on an element.
     */

    fun swipeUp()
    /**
     * Does a swipeUp action for a given [SwipeDuration] duration.
     * @param swipeDuration The duration of the swipe.
     */
    fun swipeUp(swipeDuration: SwipeDuration)

    /**
     * Does a swipeUp action from [startY] to [endY] coordinates on the view.
     * @param startY Start coordinate
     * @param endY End coordinate
     */
    fun swipeUp(startY: Float, endY: Float)

    /**
     * Does a swipeDown action on an element.
     */
    fun swipeDown()

    /**
     * Does a swipeDown action for a given [SwipeDuration] duration.
     * @param swipeDuration The duration of the swipe.
     */
    fun swipeDown(swipeDuration: SwipeDuration)

    /**
     * Does a swipeDown action from [startY] to [endY] coordinates on the view.
     * @param startY Start coordinate
     * @param endY End coordinate
     */
    fun swipeDown(startY: Float, endY: Float)


    /**
     * Swipe the [Node] until the View with the given index is visible.
     * @param index Index of the view inside a scrollable view
     * @param velocity Speed of the swiping action
     */
    fun swipeUntilIndex(index: Int, velocity: Float? = null)

    /**
     * Swipe the [Node] until the View with the given key is visible.
     * @param index Index of the view inside a scrollable view
     * @param velocity Speed of the swiping action
     */
    fun swipeUntilKey(key: Any, velocity: Float? = null)

//    fun doubleTap()
//    fun press()
}

expect class DefaultNode : Node

expect class AssertionBlockReturnType
expect fun DefaultNode.assertionResultFor(assertionBlock: () -> AssertionBlockReturnType): AssertionResult
