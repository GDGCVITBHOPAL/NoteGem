package com.gdsc_vitbhopal.notegem.domain.useCase.notes

import com.gdsc_vitbhopal.notegem.domain.repository.NoteRepository
import javax.inject.Inject

class GetNoteUseCase @Inject constructor(
    private val notesRepository: NoteRepository
) {
    suspend operator fun invoke(id: Int) = notesRepository.getNote(id)
}