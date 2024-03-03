package com.gdsc_vitbhopal.notegem.controller.notes

import com.gdsc_vitbhopal.notegem.domain.model.Note
import com.gdsc_vitbhopal.notegem.util.settings.ItemView
import com.gdsc_vitbhopal.notegem.util.settings.Order

sealed class NoteEvent {
    data class GetNote(val noteId: Int) : NoteEvent()
    data class AddNote(val note: Note) : NoteEvent()
    data class SearchNotes(val query: String) : NoteEvent()
    data class UpdateOrder(val order: Order) : NoteEvent()
//    data class UpdateView(val view: NotesView) : NoteEvent()
    data class UpdateView(val view: ItemView) : NoteEvent()
    data class UpdateNote(val note: Note) : NoteEvent()
    data class DeleteNote(val note: Note) : NoteEvent()
    object PinNote : NoteEvent()
    object ToggleReadingMode : NoteEvent()
    object ErrorDisplayed: NoteEvent()
}