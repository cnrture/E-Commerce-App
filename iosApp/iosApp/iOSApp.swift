import UIKit
import SwiftUI
import shared

@main
struct iosApp: App {
    init() {
        AppModuleKt.doInitKoin()
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
