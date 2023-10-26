package com.splendo.cucumberplayground.di

import kotlinx.coroutines.newSingleThreadContext
import org.koin.core.scope.Scope
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.ModuleDeclaration
import kotlin.coroutines.CoroutineContext

// No multithreaded thread pools are supported at the moment
private val serviceContext by lazy { newSingleThreadContext("service_coroutine_context") }

internal actual object PlatformModuleFactory : BasePlatformModuleFactory() {
    override val declaration: ModuleDeclaration = {
        // iOS specific dependencies, for example an actual impl
        // of a service that depends on NSBundle.mainBundle
    }

    override fun platformDefinitions(): PlatformDefinitions = object : PlatformDefinitions {
        override val Scope.serviceCoroutineContext: CoroutineContext
            get() = serviceContext
    }
}

internal actual val appDeclaration: KoinAppDeclaration = { }