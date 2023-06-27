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
        viewModel = LifecycleViewModel(HomeViewModel(user: user, authService: authService))
    }
 
    var body: some View {
        viewModel.lifecycleView { viewModel in
            VStack {
                Text(viewModel.user.email)
                
                Button(action: viewModel.logout) {
                    Text("Logout")
                }
            }
        }
    }
}