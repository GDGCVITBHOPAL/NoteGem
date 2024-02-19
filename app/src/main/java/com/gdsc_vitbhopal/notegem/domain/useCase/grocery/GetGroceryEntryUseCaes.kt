package com.gdsc_vitbhopal.notegem.domain.useCase.grocery

import com.gdsc_vitbhopal.notegem.domain.repository.GroceryRepository
import javax.inject.Inject

class GetGroceryEntryUseCase @Inject constructor(
    private val groceryRepository: GroceryRepository
) {
    suspend operator fun invoke(id: Int) = groceryRepository.getEntry(id)
}