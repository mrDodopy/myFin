package com.example.myfin.screens.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

private data class BottomNavItem(
    val title: String,
    val icon: ImageVector,
    val index: Int
)

@Composable
fun CustomBottomBar(
    numberOfPagesState: PagerState,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    var scrollJob by remember { mutableStateOf<Job?>(null) }
    var isDragging by remember { mutableStateOf(false) }

    val items = remember {
        listOf(
            BottomNavItem("Главная", Icons.Default.Home, 0),
            BottomNavItem("История", Icons.Default.List, 1),
            BottomNavItem("Долги", Icons.Default.Person, 2),
            BottomNavItem("Настройки", Icons.Default.Settings, 3)
        )
    }

    // Вычисляем точную дробную позицию прокрутки (от 0.0 до 3.0)
    val continuousPage by remember(numberOfPagesState) {
        derivedStateOf {
            (numberOfPagesState.currentPage + numberOfPagesState.currentPageOffsetFraction)
                .coerceIn(0f, (items.size - 1).toFloat())
        }
    }

    // Пружинная анимация сжатия при зажатии/перетаскивании
    val pillScaleY by animateFloatAsState(
        targetValue = if (isDragging) 0.88f else 1.0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "pillScaleY"
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 20.dp, start = 16.dp, end = 16.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        GlassCard(
            cornerRadius = 32.dp,
            alpha = 0.12f,
            modifier = Modifier
                .fillMaxWidth()
                .height(68.dp)
                .border(
                    width = 1.dp,
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.White.copy(alpha = 0.45f),
                            Color.White.copy(alpha = 0.10f)
                        )
                    ),
                    shape = RoundedCornerShape(32.dp)
                )
        ) {
            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 6.dp, vertical = 6.dp)
                    // Обработка жестов перетаскивания ползунка
                    .pointerInput(items.size) {
                        detectHorizontalDragGestures(
                            onDragStart = { offset ->
                                isDragging = true
                                val tabWidth = size.width.toFloat() / items.size
                                val targetFraction = (offset.x / tabWidth).coerceIn(0f, items.size - 1f)
                                val page = targetFraction.roundToInt().coerceIn(0, items.size - 1)
                                val pageOffset = (targetFraction - page).coerceIn(-0.5f, 0.5f)

                                scrollJob?.cancel()
                                scrollJob = coroutineScope.launch {
                                    numberOfPagesState.scrollToPage(page, pageOffset)
                                }
                            },
                            onHorizontalDrag = { change, _ ->
                                change.consume()
                                val tabWidth = size.width.toFloat() / items.size
                                val targetFraction = (change.position.x / tabWidth).coerceIn(0f, items.size - 1f)
                                val page = targetFraction.roundToInt().coerceIn(0, items.size - 1)
                                val pageOffset = (targetFraction - page).coerceIn(-0.5f, 0.5f)

                                scrollJob?.cancel()
                                scrollJob = coroutineScope.launch {
                                    numberOfPagesState.scrollToPage(page, pageOffset)
                                }
                            },
                            onDragEnd = {
                                isDragging = false
                                scrollJob?.cancel()
                                coroutineScope.launch {
                                    numberOfPagesState.animateScrollToPage(numberOfPagesState.currentPage)
                                }
                            },
                            onDragCancel = {
                                isDragging = false
                                scrollJob?.cancel()
                                coroutineScope.launch {
                                    numberOfPagesState.animateScrollToPage(numberOfPagesState.currentPage)
                                }
                            }
                        )
                    }
            ) {
                val totalWidth = maxWidth
                val tabWidth = totalWidth / items.size

                // 1. Плавающий стеклянный ползунок (Selection Pill)
                val pillOffset = tabWidth * continuousPage

                Box(
                    modifier = Modifier
                        .offset(x = pillOffset)
                        .width(tabWidth)
                        .fillMaxHeight()
                        .graphicsLayer {
                            scaleY = pillScaleY
                        }
                        .clip(RoundedCornerShape(24.dp))
                        .background(
                            if (isDragging) Color.White.copy(alpha = 0.32f)
                            else Color.White.copy(alpha = 0.22f)
                        )
                        .border(
                            width = 1.dp,
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color.White.copy(alpha = if (isDragging) 0.85f else 0.60f),
                                    Color.White.copy(alpha = 0.15f)
                                )
                            ),
                            shape = RoundedCornerShape(24.dp)
                        )
                )

                // 2. Иконки и подписи табов
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    items.forEachIndexed { index, item ->
                        val distance = (continuousPage - index).absoluteValue
                        val selectionFraction = (1f - distance).coerceIn(0f, 1f)

                        // Плавный градиент цвета иконки и текста (от полупрозрачного к системному синему iOS)
                        val animatedColor = lerp(
                            Color.White.copy(alpha = 0.55f),
                            Color(0xFF94AFE3),
                            selectionFraction
                        )

                        // Небольшое увеличение активной иконки
                        val iconScale = 1f + 0.10f * selectionFraction

                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = null
                                ) {
                                    coroutineScope.launch {
                                        numberOfPagesState.animateScrollToPage(item.index)
                                    }
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                                modifier = Modifier.graphicsLayer {
                                    scaleX = iconScale
                                    scaleY = iconScale
                                }
                            ) {
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = item.title,
                                    tint = animatedColor,
                                    modifier = Modifier.size(22.dp)
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = item.title,
                                    color = animatedColor,
                                    fontSize = 10.sp,
                                    fontWeight = if (selectionFraction > 0.5f) FontWeight.SemiBold else FontWeight.Normal
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}