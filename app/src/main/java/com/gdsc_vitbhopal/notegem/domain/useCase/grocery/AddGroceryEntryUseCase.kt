package com.gdsc_vitbhopal.notegem.domain.useCase.grocery

import com.gdsc_vitbhopal.notegem.domain.model.GroceryEntry
import com.gdsc_vitbhopal.notegem.domain.repository.GroceryRepository
import javax.inject.Inject

class AddGroceryEntryUseCase @Inject constructor(
    private val groceryRepository: GroceryRepository
) {
    suspend operator fun invoke(entry: GroceryEntry) = groceryRepository.addEntry(entry)
}