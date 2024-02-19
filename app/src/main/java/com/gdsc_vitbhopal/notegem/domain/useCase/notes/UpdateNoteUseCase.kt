package com.gdsc_vitbhopal.notegem.domain.useCase.notes

import com.gdsc_vitbhopal.notegem.domain.model.Note
import com.gdsc_vitbhopal.notegem.domain.repository.NoteRepository
import javax.inject.Inject

class UpdateNoteUseCase @Inject constructor(
    private val notesRepository: NoteRepository
) {
    suspend operator fun invoke(note: Note) = notesRepository.updateNote(note)
}