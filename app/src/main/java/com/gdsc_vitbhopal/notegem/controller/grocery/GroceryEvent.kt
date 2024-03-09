package com.gdsc_vitbhopal.notegem.controller.grocery

import com.gdsc_vitbhopal.notegem.domain.model.GroceryEntry
import com.gdsc_vitbhopal.notegem.util.settings.Order


sealed class GroceryEvent {
    data class AddEntry(val entry: GroceryEntry) : GroceryEvent()
    data class GetEntry(val entryId: Int) : GroceryEvent()
    data class SearchEntries(val query: String) : GroceryEvent()
    data class UpdateOrder(val order: Order) : GroceryEvent()
    data class UpdateEntry(val entry: GroceryEntry) : GroceryEvent()
    data class DeleteEntry(val entry: GroceryEntry) : GroceryEvent()
    object ErrorDisplayed: GroceryEvent()
}