package com.gdsc_vitbhopal.notegem.data.repository

import com.gdsc_vitbhopal.notegem.data.local.dao.GroceryDao
import com.gdsc_vitbhopal.notegem.domain.repository.GroceryRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class GroceryRepositoryImpl (
    private val groceryDao: GroceryDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : GroceryRepository