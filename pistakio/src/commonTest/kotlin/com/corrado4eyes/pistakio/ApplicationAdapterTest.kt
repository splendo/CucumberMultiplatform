package com.corrado4eyes.pistakio

import com.corrado4eyes.pistakio.mocks.StubApplication
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class CMApplicationTest {

    private lateinit var app: StubApplication

    @BeforeTest
    fun setUp() {
        app = StubApplication()
    }

    @Test
    fun test_launch_app() {
        app.launch(identifier = null, arguments = mapOf("foo" to "bar", "baz" to qux))
        assertEquals(1, app.launchCalled)

    }

    @Test
    fun test_launch_app_find_view() {
        app.launch()
        assertEquals(1, app.launchCalled)

        app.findView(tag = "test")
        assertEquals(1, app.findViewCalled)
        assertEquals("test", app.findViewWithTag)
    }

    @Test
    fun test_app_launched_twice() {
        assertFailsWith<CMAppLaunchedAlreadyException> {
            app.launch()
            app.launch()
        }
    }

    @Test
    fun test_app_launched_tear_down_app_launched_again() {
        app.launch()
        assertEquals(1, app.launchCalled)
        app.tearDown()
        app.launch()
        assertEquals(2, app.launchCalled)
    }

    @Test
    fun test_find_view_fails_must_launch_app_first() {
        assertFailsWith<CMAppNotLaunchedYetException> {
            app.findView(tag = "test")
        }
    }
}