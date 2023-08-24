package com.corrado4eyes.cucumberplayground.di

import com.corrado4eyes.cucumberplayground.services.AuthService
import com.corrado4eyes.cucumberplayground.services.AuthServiceImpl
import com.corrado4eyes.cucumberplayground.services.previewMocks.AuthServicePreviewMock
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.ModuleDeclaration
import org.koin.dsl.module
import kotlin.coroutines.CoroutineContext

private val SERVICE_COROUTINE_CONTEXT = named("service_context")

internal abstract class BasePlatformModuleFactory {
    abstract val declaration: ModuleDeclaration
    abstract fun platformDefinitions(): PlatformDefinitions

    fun createModule(): Module = module {
        declaration()
        platformDefinitions().apply {
            single(SERVICE_COROUTINE_CONTEXT) {
                serviceCoroutineContext
            }
        }
    }
}

interface PlatformDefinitions {
    val Scope.serviceCoroutineContext: CoroutineContext
}

internal expect object PlatformModuleFactory : BasePlatformModuleFactory

private fun sharedModule() = module {
    single<AuthService> {
        AuthServiceImpl()
    }
}

private val previewModule = module {
    single<AuthService> { AuthServicePreviewMock() }
}

fun initKoin() = startKoin {
    appDeclaration()
    modules(
        PlatformModuleFactory.createModule() + sharedModule(),
    )
}

fun initKoinPreview() = loadKoinModules(previewModule)

internal expect val appDeclaration: KoinAppDeclaration
