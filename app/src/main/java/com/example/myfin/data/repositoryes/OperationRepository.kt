package com.example.myfin.data.repositoryes

import com.example.myfin.data.dao.OperationDao
import com.example.myfin.data.entity.OperationEntity
import kotlinx.coroutines.flow.Flow

class OperationRepository(
    private val operationDao: OperationDao
) {

    val operationsFlow: Flow<List<OperationEntity>> =
        operationDao.getAllOperations()

    suspend fun addOperation(
        operation: OperationEntity
    ){
        operationDao.insertOperation(
            operation
        )
    }

    suspend fun updateOperation(
        operation: OperationEntity
    ){
        operationDao.updateOperation(
            operation
        )
    }

    suspend fun deleteOperation(
        operation: OperationEntity
    ) {
        operationDao.deleteOperation(
            operation
        )
    }
}