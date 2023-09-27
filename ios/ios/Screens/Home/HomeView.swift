//
//  HomeView.swift
//  ios
//
//  Created by Corrado Quattrocchi on 27/06/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct HomeView: SwiftUI.View {
    private let viewModel: LifecycleViewModel<HomeViewModel>
    
    init(user: User, authService: AuthService) {
        viewModel = LifecycleViewModel(HomeViewModel())
    }
 
    var body: some View {
        viewModel.lifecycleView { viewModel in
            NavigationView {
                VStack {
                    Text(viewModel.user.email)
                        .accessibilityLabel(viewModel.user.email)
                    Button(action: viewModel.logout) {
                        Text(viewModel.buttonTitle)
                    }
                    .accessibilityLabel(Strings.ButtonTag.shared.logout)
                    ScrollView(.vertical) {
                        ForEach(viewModel.scrollableItems, id: \.intValue) { index in
                            Text("\(index)")
                        }
                    }.frame(height: 200)
                    .accessibilityLabel(Strings.ScrollViewTag.shared.homeScrollView)
                }.toolbar {
                    ToolbarItem(placement: .principal) {
                        Text(viewModel.screenTitle)
                            .accessibilityLabel(Strings.ScreenTag.shared.home)
                    }
                }.navigationBarTitleDisplayMode(.inline)
            }
        }
    }
}
