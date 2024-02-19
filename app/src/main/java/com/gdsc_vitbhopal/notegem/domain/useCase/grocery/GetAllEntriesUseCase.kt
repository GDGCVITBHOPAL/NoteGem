package com.gdsc_vitbhopal.notegem.domain.useCase.grocery

import com.gdsc_vitbhopal.notegem.domain.repository.GroceryRepository
import javax.inject.Inject

class GetAllEntriesUseCase @Inject constructor(
    private val groceryRepository: GroceryRepository
) {
    suspend operator fun invoke() = groceryRepository.getAllEntries()
}