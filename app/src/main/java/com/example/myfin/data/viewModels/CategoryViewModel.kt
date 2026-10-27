package com.example.myfin.data.viewModels

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfin.data.entity.CategoryEntity
import com.example.myfin.data.repositoryes.CategoryRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CategoryViewModel(
    private val categoryRepository: CategoryRepository
): ViewModel() {

    val categories = categoryRepository.categoriesFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    /**
     * Новая категория. Иконка приходит как ключ из реестра [AppIcons] — так UI
     * создаёт категорию без зависимости от compose-иконок.
     */
    fun createNewCategory(
        name: String,
        color: Color,
        iconKey: String
    ) {
        viewModelScope.launch {
            categoryRepository.addCategory(
                name = name,
                color = color.toArgb(),
                icon = iconKey
            )
        }
    }

    fun updateCategory(category: CategoryEntity) {
        viewModelScope.launch {
            categoryRepository.updateCategory(category)
        }
    }

    fun deleteCategory(category: CategoryEntity) {
        viewModelScope.launch {
            categoryRepository.deleteCategory(category)
        }
    }
}