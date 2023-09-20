package com.corrado4eyes.pistakio

import com.corrado4eyes.pistakio.mocks.StubApplication
import com.corrado4eyes.pistakio.mocks.StubNode
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class ApplicationAdapterTest {

    private lateinit var app: StubApplication

    @BeforeTest
    fun setUp() {
        app = StubApplication()
    }

    @Test
    fun test_launch_app() {
        app.launch(
            identifier = null, 
            arguments = mapOf(
                "foo" to "bar", 
                "baz" to "qux"
            )
        )
        assertEquals(1, app.launchCalled)
        assertEquals(mapOf("foo" to "bar", "baz" to "qux"), app.launchArguments)
    }

    @Test
    fun test_launch_app_find_view() {
        app.launch(identifier = null, arguments = mapOf())
        assertEquals(1, app.launchCalled)
        app.nodeToReturn = StubNode()
        app.findView(tag = "test")
        assertEquals(1, app.findViewCalled)
        assertEquals("test", app.findViewCalledWith)
    }

    @Test
    fun test_app_launched_twice() {
        assertFailsWith<CMAppLaunchedAlreadyException> {
            app.launch(identifier = null, arguments = mapOf())
            app.launch(identifier = null, arguments = mapOf())
        }
    }

    @Test
    fun test_app_launched_tear_down_app_launched_again() {
        app.launch(identifier = null, arguments = mapOf())
        assertEquals(1, app.launchCalled)
        app.tearDown()
        app.launch(identifier = null, arguments = mapOf())
        assertEquals(2, app.launchCalled)
    }

    @Test
    fun test_find_view_fails_must_launch_app_first() {
        assertFailsWith<CMAppNotLaunchedYetException> {
            app.findView(tag = "test")
        }
    }
}