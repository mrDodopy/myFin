package com.example.myfin.data.viewModels

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfin.data.repositoryes.CategoryRepository
import com.example.myfin.utils.converseIconToString
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

    fun createNewCategory(
        name: String,
        color: Color,
        icon: ImageVector
    ) {
        viewModelScope.launch {
            categoryRepository.addCategory(
                name = name,
                color = color.toArgb(),
                icon = converseIconToString(icon)
            )
        }
    }
}