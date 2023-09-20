//
//  LoginView.swift
//  ios
//
//  Created by Corrado Quattrocchi on 26/06/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct LoginView: SwiftUI.View {
    @ObservedObject var emailText: StringSubject
    @ObservedObject var passwordText: StringSubject
    
    @ObservedObject var emailErrorText: StringObservable
    @ObservedObject var passwordErrorText: StringObservable
    @ObservedObject var formFooterErrorText: StringObservable
    
    @ObservedObject var isLoading: BoolObservable
    @ObservedObject var isButtonEnabled: BoolObservable
    
    private let viewModel: LifecycleViewModel<LoginViewModel>
    
    init(authService: AuthService) {
        let loginViewModel = LoginViewModel()
        viewModel = LifecycleViewModel(loginViewModel)
        emailText = StringSubject(loginViewModel.emailText)
        passwordText = StringSubject(loginViewModel.passwordText)
        emailErrorText = StringObservable(loginViewModel.emailErrorText)
        passwordErrorText = StringObservable(loginViewModel.passwordErrorText)
        formFooterErrorText = StringObservable(loginViewModel.formFooterError)
        
        isLoading = BoolObservable(loginViewModel.isLoading)
        isButtonEnabled = BoolObservable(loginViewModel.isButtonEnabled)
    }
    
    var body: some View {
        viewModel.lifecycleView { viewModel in
            NavigationView {
                VStack {
                    TextField(viewModel.emailPlaceholder, text: $emailText.value)
                        .autocapitalization(.none)
                        .accessibilityLabel(Strings.TextFieldTag.shared.email)
                    Text(emailErrorText.value)
                        .foregroundColor(Color.red)
                    SecureField(viewModel.passwordPlaceholder, text: $passwordText.value)
                        .autocapitalization(.none)
                        .accessibilityLabel(Strings.TextFieldTag.shared.password)
                    Text(passwordErrorText.value)
                        .foregroundColor(Color.red)
                    
                    Text(formFooterErrorText.value)
                        .foregroundColor(Color.red)
                    
                    if isLoading.value {
                        ProgressView()
                    }
                    
                    Button(action: viewModel.login) {
                        Text(viewModel.buttonTitle)
                    }
                    .disabled(!isButtonEnabled.value)
                    .accessibilityLabel(Strings.ButtonTag.shared.login)
                    Spacer()
                }
                .toolbar {
                    ToolbarItem(placement: .principal) {
                        Text(viewModel.screenTitle)
                            .accessibilityLabel(Strings.ScreenTag.shared.login)
                    }
                }.navigationBarTitleDisplayMode(.inline)
            }
            
        }
    }
}
