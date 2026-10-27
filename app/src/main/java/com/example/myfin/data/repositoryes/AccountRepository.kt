package com.example.myfin.data.repositoryes

import androidx.compose.runtime.collectAsState
import com.example.myfin.data.dao.AccountDao
import com.example.myfin.data.entity.AccountEntity
import kotlinx.coroutines.flow.Flow

class AccountRepository(
    private val accountDao: AccountDao
) {

    val accountsFlow: Flow<List<AccountEntity>> =
        accountDao.getAllAccounts()

    suspend fun addAccount(
         name: String,
         funds: Long,
         currency: String,
         color: Int,
         icon: String
    ){
        accountDao.insertAccount(
            AccountEntity(
                name = name,
                funds = funds,
                currency = currency,
                color = color,
                icon = icon
            )
        )
    }

    suspend fun addAccount(
        account: AccountEntity
    ){
        accountDao.insertAccount(
            account
        )
    }

    suspend fun updateAccount(
        account: AccountEntity
    ){
        accountDao.updateAccount(account)
    }

    suspend fun deleteAccount(
        account: AccountEntity
    ){
        accountDao.deleteAccount(account)
    }
}