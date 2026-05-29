package com.zackaryw.nothinglauncher

enum class AppMenuState {
    CLOSED,
    OPEN
}

object AppMenuToggle {
    fun nextState(currentState: AppMenuState): AppMenuState {
        return when (currentState) {
            AppMenuState.CLOSED -> AppMenuState.OPEN
            AppMenuState.OPEN -> AppMenuState.CLOSED
        }
    }
}
