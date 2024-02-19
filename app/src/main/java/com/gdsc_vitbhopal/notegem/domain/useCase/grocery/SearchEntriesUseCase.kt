package com.gdsc_vitbhopal.notegem.domain.useCase.grocery

import com.gdsc_vitbhopal.notegem.domain.repository.GroceryRepository
import javax.inject.Inject

class SearchEntriesUseCase @Inject constructor(
    private val repository: GroceryRepository
) {
    suspend operator fun invoke(query: String) = repository.searchEntries(query)
}