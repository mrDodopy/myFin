package com.example.myfin.data.viewModels

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfin.data.entity.AccountEntity
import com.example.myfin.data.repositoryes.AccountRepository
import com.example.myfin.utils.converseIconToString
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AccountViewModel(
    private val accountRepository: AccountRepository
): ViewModel(){

    val accounts = accountRepository.accountsFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun createNewAccount(
        name: String,
        funds: Long,
        currency: String,
        color: Int,
        icon: ImageVector
    ) {
        viewModelScope.launch {
            accountRepository.addAccount(
                name = name,
                funds = funds,
                currency = currency,
                color = color,
                icon = converseIconToString(icon)
            )
        }
    }

    fun deleteAccount(account: AccountEntity) {
        viewModelScope.launch {
            accountRepository.deleteAccount(account)
        }
    }
}