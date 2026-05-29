package com.zackaryw.nothinglauncher

import org.junit.Assert.assertEquals
import org.junit.Test

class AppMenuToggleTest {

    @Test
    fun clickingMenuWhenClosedOpensAppMenu() {
        assertEquals(AppMenuState.OPEN, AppMenuToggle.nextState(AppMenuState.CLOSED))
    }

    @Test
    fun clickingMenuWhenOpenClosesAppMenu() {
        assertEquals(AppMenuState.CLOSED, AppMenuToggle.nextState(AppMenuState.OPEN))
    }
}
