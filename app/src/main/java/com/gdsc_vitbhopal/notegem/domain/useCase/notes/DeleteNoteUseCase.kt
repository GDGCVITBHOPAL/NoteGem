package com.gdsc_vitbhopal.notegem.domain.useCase.notes

import com.gdsc_vitbhopal.notegem.domain.model.Note
import com.gdsc_vitbhopal.notegem.domain.repository.NoteRepository
import javax.inject.Inject

class DeleteNoteUseCase @Inject constructor(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(note: Note) = repository.deleteNote(note)
}