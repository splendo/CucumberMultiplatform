import SwiftUI
import shared

@main
struct iOSApp: App {
    
    init() {
        CucumberDependencyInjectionKt.doInitKoin()
    }
    
	var body: some Scene {
		WindowGroup {
			ContentView()
		}
	}
}
