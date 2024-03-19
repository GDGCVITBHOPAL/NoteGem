package com.gdsc_vitbhopal.notegem.controller.grocery

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gdsc_vitbhopal.notegem.domain.model.GroceryEntry
import com.gdsc_vitbhopal.notegem.domain.useCase.grocery.AddGroceryEntryUseCase
import com.gdsc_vitbhopal.notegem.domain.useCase.grocery.DeleteGroceryEntryUseCase
import com.gdsc_vitbhopal.notegem.domain.useCase.grocery.GetAllEntriesUseCase
import com.gdsc_vitbhopal.notegem.domain.useCase.grocery.GetGroceryEntryUseCase
import com.gdsc_vitbhopal.notegem.domain.useCase.grocery.GetGroceryForChartUseCase
import com.gdsc_vitbhopal.notegem.domain.useCase.grocery.SearchEntriesUseCase
import com.gdsc_vitbhopal.notegem.domain.useCase.grocery.UpdateGroceryEntryUseCase
import com.gdsc_vitbhopal.notegem.domain.useCase.settings.GetSettingsUseCase
import com.gdsc_vitbhopal.notegem.domain.useCase.settings.SaveSettingsUseCase
import com.gdsc_vitbhopal.notegem.util.Constants
import com.gdsc_vitbhopal.notegem.util.settings.Order
import com.gdsc_vitbhopal.notegem.util.settings.OrderType
import com.gdsc_vitbhopal.notegem.util.settings.toInt
import com.gdsc_vitbhopal.notegem.util.settings.toOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroceryViewModel @Inject constructor(
    private val addEntry: AddGroceryEntryUseCase,
    private val updateEntry: UpdateGroceryEntryUseCase,
    private val deleteEntry: DeleteGroceryEntryUseCase,
    private val getAlEntries: GetAllEntriesUseCase,
    private val searchEntries: SearchEntriesUseCase,
    private val getEntry: GetGroceryEntryUseCase,
    private val getSettings: GetSettingsUseCase,
    private val saveSettings: SaveSettingsUseCase,
    private val getEntriesForChart: GetGroceryForChartUseCase
) : ViewModel() {

    var uiState by mutableStateOf(UiState())
        private set

    private var getEntriesJob: Job? = null

    init {
        viewModelScope.launch {
            getSettings(
                intPreferencesKey(Constants.GROCERY_ORDER_KEY),
                Order.DateModified(OrderType.ASC()).toInt()
            ).collect {
                getEntries(it.toOrder())
            }
        }
    }

    fun onEvent(event: GroceryEvent) {
        when (event) {
            is GroceryEvent.AddEntry -> viewModelScope.launch {
                addEntry(event.entry)
                uiState = uiState.copy(
                    navigateUp = true
                )
            }
            is GroceryEvent.DeleteEntry -> viewModelScope.launch {
                deleteEntry(event.entry)
                uiState = uiState.copy(
                    navigateUp = true
                )
            }
            is GroceryEvent.GetEntry -> viewModelScope.launch {
                val entry = getEntry(event.entryId)
                uiState = uiState.copy(
                    entry = entry
                )
            }
            is GroceryEvent.SearchEntries -> viewModelScope.launch {
                val entries = searchEntries(event.query)
                uiState = uiState.copy(
                    searchEntries = entries
                )
            }
            is GroceryEvent.UpdateEntry -> viewModelScope.launch {
                updateEntry(event.entry)
                uiState = uiState.copy(
                    navigateUp = true
                )
            }
            is GroceryEvent.UpdateOrder -> viewModelScope.launch {
                saveSettings(
                    intPreferencesKey(Constants.GROCERY_ORDER_KEY),
                    event.order.toInt()
                )
            }
            GroceryEvent.ErrorDisplayed -> uiState = uiState.copy(error = null)
            is GroceryEvent.ChangeChartEntriesRange -> viewModelScope.launch {
                uiState = uiState.copy(chartEntries = getEntriesForChart(event.monthly))
            }
        }
    }

    data class UiState(
        val entries: List<GroceryEntry> = emptyList(),
        val entriesOrder: Order = Order.DateModified(OrderType.ASC()),
        val entry: GroceryEntry? = null,
        val error: String? = null,
        val searchEntries: List<GroceryEntry> = emptyList(),
        val navigateUp: Boolean = false,
        val chartEntries : List<GroceryEntry> = emptyList()
    )

    private fun getEntries(order: Order) {
        getEntriesJob?.cancel()
        getEntriesJob = getAlEntries(order)
            .onEach { entries ->
                uiState = uiState.copy(
                    entries = entries,
                    entriesOrder = order
                )
            }.launchIn(viewModelScope)
    }

}