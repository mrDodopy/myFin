package com.example.myfin.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.myfin.data.entity.AccountEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountDao{

    @Query("SELECT * FROM accounts ORDER BY id ASC")
    fun getAllAccounts(): Flow<List<AccountEntity>>

    // Видимые счета — для дашборда (скрытые через глазик не учитываются)
    @Query("SELECT * FROM accounts WHERE isHidden = 0 ORDER BY id ASC")
    fun getVisibleAccounts(): Flow<List<AccountEntity>>

    @Insert
    suspend fun insertAccount(account: AccountEntity): Long

    @Update
    suspend fun updateAccount(account: AccountEntity)

    @Delete
    suspend fun deleteAccount(account: AccountEntity)

    @Query("DELETE FROM accounts")
    suspend fun deleteAllAccounts()
}