@file:Suppress("NOTHING_TO_INLINE")

package com.androidpositive.extensions

import android.content.res.Configuration
import android.view.View

inline fun View.show() {
    visibility = View.VISIBLE
}

inline fun View.showIf(condition: () -> Boolean) : View {
    if (visibility != View.VISIBLE && condition()) {
        visibility = View.VISIBLE
    }
    return this
}

inline fun <T : View> T.showAnd(action: T.() -> Unit) {
    show()
    action()
}

inline fun View.hide() {
    visibility = View.GONE
}

inline fun View.hideIf(condition: () -> Boolean) : View {
    if (visibility != View.GONE && condition()) {
        visibility = View.GONE
    }
    return this
}

inline fun <T : View> T.hideAnd(action: T.() -> Unit) {
    hide()
    action()
}

inline fun View.makeInvisible() {
    visibility = View.INVISIBLE
}

inline fun View.enable() {
    isEnabled = true
}

inline fun View.disable() {
    isEnabled = false
}

inline fun View.isLandscape() =
    context.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

inline fun View.isPortrait() =
    context.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT
