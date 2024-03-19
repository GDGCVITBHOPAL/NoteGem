package com.gdsc_vitbhopal.notegem.domain.useCase.grocery

import com.gdsc_vitbhopal.notegem.domain.model.GroceryEntry
import com.gdsc_vitbhopal.notegem.domain.repository.GroceryRepository
import com.gdsc_vitbhopal.notegem.util.date.inTheLast30Days
import com.gdsc_vitbhopal.notegem.util.date.inTheLastYear
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetGroceryForChartUseCase @Inject constructor(
    private val groceryRepository: GroceryRepository
) {
    suspend operator fun invoke(monthly: Boolean) : List<GroceryEntry>{
        return groceryRepository
            .getAllEntries()
            .first()
            .filter {
                if (monthly) it.createdDate.inTheLast30Days()
                else it.createdDate.inTheLastYear()
            }
            .sortedBy { it.createdDate }
    }
}