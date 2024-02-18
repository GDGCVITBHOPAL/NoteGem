package com.gdsc_vitbhopal.notegem.domain.useCase.bookmarks

import com.gdsc_vitbhopal.notegem.domain.repository.BookmarkRepository
import javax.inject.Inject

class GetAllBookmarksUseCase @Inject constructor(
    private val bookmarksRepository: BookmarkRepository
) {
    suspend operator fun invoke() = bookmarksRepository.getAllBookmarks()
}