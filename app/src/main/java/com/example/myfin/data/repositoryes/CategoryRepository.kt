package com.example.myfin.data.repositoryes

import com.example.myfin.data.dao.CategoryDao
import com.example.myfin.data.entity.CategoryEntity
import kotlinx.coroutines.flow.Flow

class CategoryRepository(
    private val categoryDao: CategoryDao
) {

    val categoriesFlow: Flow<List<CategoryEntity>> =
        categoryDao.getAllCategories()

    suspend fun addCategory(
        name: String,
        color: Int,
        icon: String
    ) {
        categoryDao.insertCategory(
            CategoryEntity(
                name = name,
                color = color,
                icon = icon
            )
        )
    }

    suspend fun updateCategory(
        category: CategoryEntity
    ) {
        categoryDao.updateCategory(category)
    }

    suspend fun deleteCategory(
        category: CategoryEntity
    ) {
        categoryDao.deleteCategory(category)
    }
}