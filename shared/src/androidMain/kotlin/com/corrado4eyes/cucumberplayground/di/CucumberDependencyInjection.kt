package com.corrado4eyes.cucumberplayground.di

import com.corrado4eyes.cucumberplayground.viewModels.home.HomeViewModel
import com.corrado4eyes.cucumberplayground.viewModels.login.LoginViewModel
import com.corrado4eyes.cucumberplayground.viewModels.main.MainViewModel
import com.splendo.kaluga.base.ApplicationHolder
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.scope.Scope
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.ModuleDeclaration
import kotlin.coroutines.CoroutineContext

internal actual object PlatformModuleFactory : BasePlatformModuleFactory() {

    override val declaration: ModuleDeclaration = {
        viewModel {
            LoginViewModel()
        }
        viewModel {
            HomeViewModel()
        }
        viewModel {
            MainViewModel()
        }
    }

    override fun platformDefinitions(): PlatformDefinitions = object : PlatformDefinitions {
        override val Scope.serviceCoroutineContext: CoroutineContext
            get() = Dispatchers.Default
    }
}

internal actual val appDeclaration: KoinAppDeclaration = {
    androidContext(ApplicationHolder.applicationContext)
}
