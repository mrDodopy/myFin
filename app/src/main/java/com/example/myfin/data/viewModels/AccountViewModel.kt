package com.example.myfin.data.viewModels

import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfin.data.entity.AccountEntity
import com.example.myfin.data.repositoryes.AccountRepository
import com.example.myfin.utils.converseIconToString
import kotlinx.coroutines.flow.Flow
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

/**
 *      План: живая сумма по счетам в ViewModel
 *
 *      Ключевая идея. Раз валюты разные — «одна общая сумма» бессмысленна. Правильная модель: сумма по каждой валюте отдельно — например, Map<String, Long> (валюта → итог в копейках).
 *
 *      Шаг 1 — логика группировки. Подумай, как одним выражением свернуть List<AccountEntity> в Map<String, Long>: у стандартной библиотеки Kotlin есть функция для «сгруппировать и
 *      просуммировать» (groupingBy {...}.fold или связка groupBy + mapValues). Суммируй account.funds, ключ — account.currency.
 *
 *      Шаг 2 — вывод производительского потока. В AccountViewModel рядом с accounts сделай свойство totalFundsByCurrency: возьми существующий accounts (он уже
 *      StateFlow<List<AccountEntity>>), примени оператор map: accounts.map { список -> <Шаг 1> }, а потом заверни в stateIn(...) с теми же параметрами, что у accounts (viewModelScope,
 *      SharingStarted.WhileSubscribed(5000), начальное значение). Подсказка на ловушку: начальное значение должно совпадать по типу с результатом map — не emptyList(), а аналог «пустой
 *      суммы» для Map.
 *
 *      Шаг 3 — зачем здесь вообще map. Это главная концепция, которую ты сейчас закрышь: map у Flow — «превращать каждое приходящее значение списка счетов в значение другого вида (сумму)
 *      , не выходя из потока». Никаких launch внутри функции не нужно — Room сам переэмитит список при любом INSERT/UPDATE/DELETE, map пересчитает, stateIn раздаст UI.
 *
 *      Шаг 4 — убрать старое. Текущую calculateTotalFundsOnAccounts() (AccountViewModel.kt:50-57) она в любом случае не компилируется: val нельзя перезаписывать +=, функция без return —
 *      смело удаляем целиком, путь А её не требует.
 *
 *      Шаг 5 — проверка. Сборка .\gradlew.bat assembleDebug; в UI на дашборде подключи как обычно: totalFundsByCurrency.collectAsState() и отрисуй пару «валюта — сумма» ( представителей
 *      Map можно итерировать напрямую).
 *
 *      Оставшийся открытый вопрос для реализации: где отображать — в DashBoardScreen или передавать вниз как параметр (экран получает уже операции/аккаунты как параметры — посмотри как
 *      MyFinApp.kt:160-163 передаёт ViewModels). Подсказка: детали отображения лучше не тащить в ViewModel, суммы по валютам — допустимо.
 *
 *
 *      Да, это правильное направление, и хорошая новость: оно не отменяет план — группировка по валютам (Map<String, Long>) остаётся базой, а конвертация становится ещё одним слоем
 *      поверх неё. Подсказки, как это обычно раскладывается:
 *
 *      Подсказка 1 — где живёт конвертация. Держи её подальше от Room и от ViewModel'а счетов. Логичное место — отдельный класс-конвертер (например, в utils/ или свой мини-репозиторий
 *      CurrencyRepository), который принимает Map<String, Long> + курс и отдаёт Map<String, Long> уже в целевой валюте. Потоковая цепочка тогда станет: accounts → map { сгруппировать }
 *      → (опционально ещё один map { конвертировать }) → stateIn. Суть: конвертация — это просто ещё один map над потоком, ничего концептуально нового не нужно.
 *
 *      Подсказка 2 — откуда брать курсы. Зависимость OkHttp (4.12.0) в build.gradle.kts у тебя уже есть — похоже, она как раз для этого. Ту два принципиальных выбора:
 *
 *      - Статичные курсы (захардкожены/в настройках) — просто, без сети, но сразу врёт при скачке курса;
 *      - Онлайн-источник — запрос к API курсов, а вот здесь ловушка: сетевой запрос — это suspend, и его нельзя дёргать «по каждому изменению списка счетов». Подумай: курсы и счета
 *        меняются с разной периодичностью, значит это два независимых потока, которые сходятся уже в точке отображения, а не подряд в одной цепочке (комбинировать потоки — оператор
 *        combine, запомни его на будущее).
 *
 *      Подсказка 3 — точность в копейках. funds у тебя Long (копейки). Умножение на курс (например, 0.33) в Long — потеря копеек при округлении. Подумай заранее, в чём считать курс:
 *      Double (проще, но с погрешностью), переведённый в «милликопейки» Long, или честный подход с масштабированием. Для учебного проекта Double + roundToLong() достаточно, просто
 *      сделай выбор осознанно.
 *
 *      Подсказка 4 — где живёт «целевая валюта». У пользователя должна быть настройка «показывать всё в X». Смотри: у вас уже есть SettingsViewModel и UserSettings — целевая валюта
 *      просится именно туда. Тогда конвертация и станет тем самым примером combine (поток счетов + поток настройки валюты).
 *
 *      Мой совет по масштабу на сейчас: не закладывать конвертацию в текущую задачу вообще — сначала шаги 1–5 из прошлого плана (группировка по валютам), она самодостаточна и визуально
 *      информативна. Конвертер — отдельная задача после, когда появится настройка «валюта отображения».
 *
 *      Куда двигаемся: внедрять группировку по валютам (шаги 1–5), или сначала хочешь, чтобы я расписал план конвертера валют подробнее?
 */