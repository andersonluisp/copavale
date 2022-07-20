package com.andersonpimentel.faceitdata.login.presentation.loginActivity.state

import com.andersonpimentel.faceitdata.baseviewmodel.state.ViewState

data class LoginActivityState(
    val isLoading: Boolean = false,
    val showContent: Boolean = false,
) : ViewState {

    fun setLoadingState() = this.copy(
        isLoading = true,
        showContent = false
    )

    fun setShowContentState() = this.copy(
        isLoading = false,
        showContent = true
    )
}