package com.gdsc_vitbhopal.notegem.domain.useCase.notes

import com.gdsc_vitbhopal.notegem.domain.model.Note
import com.gdsc_vitbhopal.notegem.domain.repository.NoteRepository
import com.gdsc_vitbhopal.notegem.util.settings.Order
import com.gdsc_vitbhopal.notegem.util.settings.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAllNotesUseCase @Inject constructor(
    private val notesRepository: NoteRepository
) {
//    suspend operator fun invoke() = notesRepository.getAllNotes()

    operator fun invoke(order: Order) : Flow<List<Note>> {
        return notesRepository.getAllNotes().map { list ->
            when (order.orderType) {
                is OrderType.ASC -> {
                    when (order) {
                        is Order.Alphabetical -> list.sortedWith(compareBy({!it.pinned}, { it.title }, ))
                        is Order.DateCreated -> list.sortedWith(compareBy({!it.pinned}, { it.createdDate }))
                        is Order.DateModified -> list.sortedWith(compareBy({!it.pinned}, { it.updatedDate }))
                        else -> list.sortedWith(compareBy({!it.pinned}, { it.updatedDate }))
                    }
                }
                is OrderType.DESC -> {
                    when (order) {
                        is Order.Alphabetical -> list.sortedWith(compareBy({it.pinned}, { it.title })).reversed()
                        is Order.DateCreated -> list.sortedWith(compareBy({it.pinned}, { it.createdDate })).reversed()
                        is Order.DateModified -> list.sortedWith(compareBy({it.pinned}, { it.updatedDate })).reversed()
                        else -> list.sortedWith(compareBy({it.pinned}, { it.updatedDate })).reversed()
                    }
                }
            }
        }
    }

}