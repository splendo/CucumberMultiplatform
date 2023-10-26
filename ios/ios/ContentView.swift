import SwiftUI
import shared

extension AppNavigator : HasDefaultValue {
    static func `default`() -> Self {
        return AppNavigator.Loading() as! Self
    }
}

struct ContentView: View {
    private let viewModel: LifecycleViewModel<MainViewModel>
    
    private let authService = AuthServiceImpl()
    @ObservedObject var navState: ObjectObservable<AppNavigator>
    
    init() {
        let testConfiguration: TestConfiguration? = {
            let arguments = CommandLine.arguments
            let tc = DefaultTestConfiguration(configuration: ProcessInfo.processInfo.environment)
            return tc
        }()
        let mainViewModel = MainViewModel(testConfiguration: testConfiguration)
        viewModel = LifecycleViewModel(mainViewModel)
        navState = ObjectObservable(mainViewModel.navState)
    }
    var body: some View {
        NavigationView {
            if navState.value is AppNavigator.Home {
                HomeView(user: (navState.value as! AppNavigator.Home).user, authService: authService)
            } else if navState.value is AppNavigator.Login{
                LoginView(authService: authService)
            } else {
                ProgressView()
            }
        }
    }
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}
