@file:JvmName("AndroidDependencyInjection")

package com.splendo.cucumberplayground.di

import com.splendo.cucumberplayground.models.TestConfiguration
import com.splendo.cucumberplayground.viewModels.home.HomeViewModel
import com.splendo.cucumberplayground.viewModels.login.LoginViewModel
import com.splendo.cucumberplayground.viewModels.main.MainViewModel
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
        viewModel { (testConfig: TestConfiguration?) ->
            MainViewModel(testConfig)
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
