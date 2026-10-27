package com.example.myfin.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.ChildCare
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Flight
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Handshake
import androidx.compose.material.icons.filled.Handyman
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalCafe
import androidx.compose.material.icons.filled.LocalGasStation
import androidx.compose.material.icons.filled.LocalPharmacy
import androidx.compose.material.icons.filled.Luggage
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.Policy
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Savings
import androidx.compose.material.icons.filled.Smartphone
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Subway
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material.icons.filled.Work
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Реестр иконок приложения.
 *
 * В БД иконка хранится строкой (её ключ), а в UI используется ImageVector.
 * Добавляя новую иконку, дописывайте её сюда же — тогда она автоматически
 * появится в диалогах выбора иконок.
 */
val AppIcons: Map<String, ImageVector> = mapOf(
    "build" to Icons.Default.Build,             // базовая/отладочная
    "home" to Icons.Default.Home,               // дом, аренда
    "food" to Icons.Default.Restaurant,         // еда, кафе
    "cart" to Icons.Default.ShoppingCart,       // покупки
    "car" to Icons.Default.DirectionsCar,       // авто
    "transport" to Icons.Default.Subway,        // транспорт
    "wallet" to Icons.Default.AccountBalanceWallet, // кошелёк, наличные
    "card" to Icons.Default.CreditCard,         // карта
    "savings" to Icons.Default.Savings,         // копилка
    "health" to Icons.Default.Favorite,         // здоровье
    "school" to Icons.Default.School,           // образование
    "travel" to Icons.Default.Flight,           // путешествия
    "pet" to Icons.Default.Pets,                // питомцы
    "gift" to Icons.Default.CardGiftcard,       // подарки
    "work" to Icons.Default.Work,               // работа
    "entertainment" to Icons.Default.Movie,     // развлечения
    "games" to Icons.Default.SportsEsports,     // игры
    "bills" to Icons.Default.Receipt,           // счета, коммуналка
    "star" to Icons.Default.Star,               // разное/важное
    // Дополнительные
    "cafe" to Icons.Default.LocalCafe,          // кофе, фастфуд
    "shopping" to Icons.Default.ShoppingBag,    // шопинг
    "gas" to Icons.Default.LocalGasStation,     // заправка
    "fitness" to Icons.Default.FitnessCenter,   // спорт
    "pharmacy" to Icons.Default.LocalPharmacy,  // аптека
    "child" to Icons.Default.ChildCare,         // дети
    "music" to Icons.Default.MusicNote,         // музыка
    "photo" to Icons.Default.CameraAlt,         // фото, мультимедиа
    "pc" to Icons.Default.Computer,             // техника
    "phone" to Icons.Default.Smartphone,        // связь, подписки
    "luggage" to Icons.Default.Luggage,         // поездки
    "sunny" to Icons.Default.WbSunny,           // отпуск
    "water" to Icons.Default.WaterDrop,         // ЖКХ, вода
    "bolt" to Icons.Default.Bolt,               // электричество
    "spa" to Icons.Default.Spa,                 // красота, уход
    "people" to Icons.Default.Groups,           // компания, друзья
    "insurance" to Icons.Default.Policy,        // страховки
    "loan" to Icons.Default.Handshake,          // кредиты, долги
    "repair" to Icons.Default.Handyman,         // ремонт
    "tax" to Icons.Default.AccountBalance       // налоги, банк
)

/** Список иконок, доступных для выбора в диалогах (порядок = порядок в реестре). */
val AppIconList: List<Pair<String, ImageVector>> = AppIcons.entries.map { it.key to it.value }

/**
 * Функция для преобразования иконки из категорий в строку для сохранения в БД
 *
 *  -> иконку в формате imageVector
 *
 *  <- название иконки
 */
fun converseIconToString (icon: ImageVector): String{
    return AppIcons.entries.firstOrNull { it.value == icon }?.key ?: "build"
}


/**
 * Функция для преобразования названия иконки из категорий из БД в imageVector для отображения
 *
 *  -> название иконки
 *
 *  <- иконку в формате imageVector
 */
fun converseIconToString(nameIcon: String): ImageVector {
    return AppIcons[nameIcon] ?: Icons.Default.Build
}

//Решил сделать через перегрузку функции и не создавать 2 разные функции
