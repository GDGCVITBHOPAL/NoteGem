package com.gdsc_vitbhopal.notegem.controller.notes

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gdsc_vitbhopal.notegem.R
import com.gdsc_vitbhopal.notegem.app.getString
import com.gdsc_vitbhopal.notegem.domain.model.Note
import com.gdsc_vitbhopal.notegem.domain.useCase.notes.AddNoteUseCase
import com.gdsc_vitbhopal.notegem.domain.useCase.notes.DeleteNoteUseCase
import com.gdsc_vitbhopal.notegem.domain.useCase.notes.GetAllNotesUseCase
import com.gdsc_vitbhopal.notegem.domain.useCase.notes.GetNoteUseCase
import com.gdsc_vitbhopal.notegem.domain.useCase.notes.SearchNotesUseCase
import com.gdsc_vitbhopal.notegem.domain.useCase.notes.UpdateNoteUseCase
import com.gdsc_vitbhopal.notegem.domain.useCase.settings.GetSettingsUseCase
import com.gdsc_vitbhopal.notegem.domain.useCase.settings.SaveSettingsUseCase
import com.gdsc_vitbhopal.notegem.util.settings.ItemView
import com.gdsc_vitbhopal.notegem.util.settings.Order
import com.gdsc_vitbhopal.notegem.util.settings.OrderType
import com.gdsc_vitbhopal.notegem.util.settings.toInt
import com.gdsc_vitbhopal.notegem.util.settings.toNotesView
import com.gdsc_vitbhopal.notegem.util.settings.toOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val allNotes: GetAllNotesUseCase,
    private val getNote: GetNoteUseCase,
    private val updateNote: UpdateNoteUseCase,
    private val addNote: AddNoteUseCase,
    private val deleteNote: DeleteNoteUseCase,
    private val searchNotes: SearchNotesUseCase,
    getSettings: GetSettingsUseCase,
    private val saveSettings: SaveSettingsUseCase
) : ViewModel() {

    var notesUiState by mutableStateOf((UiState()))
        private set

    private var getNotesJob: Job? = null

    init {
        viewModelScope.launch {
            combine(
                getSettings(
                    intPreferencesKey(com.gdsc_vitbhopal.notegem.util.Constants.NOTES_ORDER_KEY),
                    Order.DateModified(OrderType.ASC()).toInt()
                ),
                getSettings(
                    intPreferencesKey(com.gdsc_vitbhopal.notegem.util.Constants.NOTE_VIEW_KEY),
                    ItemView.LIST.value
                )
            ) { order, view ->
                notesUiState = notesUiState.copy(notesOrder = order.toOrder())
                getNotes(order.toOrder())
                if (notesUiState.noteView.value != view) {
                    notesUiState = notesUiState.copy(noteView = view.toNotesView())
                }
            }.collect()
        }
    }

    fun onEvent(event: NoteEvent) {
        when (event) {
            is NoteEvent.AddNote -> viewModelScope.launch {
                notesUiState = if (event.note.title.isBlank() && event.note.content.isBlank()) {
                    notesUiState.copy(navigateUp = true)
                } else {
                    addNote(
                        event.note.copy(
                            createdDate = System.currentTimeMillis(),
                            updatedDate = System.currentTimeMillis()
                        )
                    )
                    notesUiState.copy(navigateUp = true)
                }
            }
            is NoteEvent.DeleteNote -> viewModelScope.launch {
                deleteNote(event.note)
                notesUiState = notesUiState.copy(navigateUp = true)
            }
            is NoteEvent.GetNote -> viewModelScope.launch {
                val note = getNote(event.noteId)
                notesUiState = notesUiState.copy(note = note)
            }
            is NoteEvent.SearchNotes -> viewModelScope.launch {
                val notes = searchNotes(event.query)
                notesUiState = notesUiState.copy(searchNotes = notes)
            }
            is NoteEvent.UpdateNote -> viewModelScope.launch {
                notesUiState = if (event.note.title.isBlank() && event.note.content.isBlank())
                    notesUiState.copy(error = getString(R.string.error_empty_note))
                else {
                    updateNote(event.note.copy(updatedDate = System.currentTimeMillis()))
                    notesUiState.copy(navigateUp = true)
                }
            }
            is NoteEvent.UpdateOrder -> viewModelScope.launch {
                saveSettings(
                    intPreferencesKey(com.gdsc_vitbhopal.notegem.util.Constants.NOTES_ORDER_KEY),
                    event.order.toInt()
                )
            }
            is NoteEvent.ErrorDisplayed -> notesUiState = notesUiState.copy(error = null)
            NoteEvent.ToggleReadingMode -> notesUiState =
                notesUiState.copy(readingMode = !notesUiState.readingMode)
            is NoteEvent.PinNote -> viewModelScope.launch {
                updateNote(notesUiState.note?.copy(pinned = !notesUiState.note?.pinned!!)!!)
            }
            is NoteEvent.UpdateView -> viewModelScope.launch {
                saveSettings(
                    intPreferencesKey(com.gdsc_vitbhopal.notegem.util.Constants.NOTE_VIEW_KEY),
                    event.view.value
                )
            }
        }
    }

    data class UiState(
        val notes: List<Note> = emptyList(),
        val note: Note? = null,
        val notesOrder: Order = Order.DateModified(OrderType.ASC()),
        val error: String? = null,
        val noteView: ItemView = ItemView.LIST,
        val navigateUp: Boolean = false,
        val readingMode: Boolean = true,
        val searchNotes: List<Note> = emptyList()
    )

    private fun getNotes(order: Order) {
        getNotesJob?.cancel()
        getNotesJob = allNotes(order)
            .onEach { tasks ->
                notesUiState = notesUiState.copy(
                    notes = tasks,
                    notesOrder = order
                )
            }.launchIn(viewModelScope)
    }
}