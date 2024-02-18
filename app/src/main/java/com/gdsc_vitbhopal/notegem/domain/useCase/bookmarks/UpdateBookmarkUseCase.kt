package com.gdsc_vitbhopal.notegem.domain.useCase.bookmarks

import com.gdsc_vitbhopal.notegem.domain.model.Bookmark
import com.gdsc_vitbhopal.notegem.domain.repository.BookmarkRepository
import javax.inject.Inject

class UpdateBookmarkUseCase @Inject constructor(
    private val bookmarkRepository: BookmarkRepository
) {
    suspend operator fun invoke(bookmark: Bookmark) = bookmarkRepository.updateBookmark(bookmark)
}