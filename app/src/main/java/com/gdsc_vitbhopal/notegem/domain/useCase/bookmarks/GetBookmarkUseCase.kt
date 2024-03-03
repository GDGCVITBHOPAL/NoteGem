package com.gdsc_vitbhopal.notegem.domain.useCase.bookmarks

import com.gdsc_vitbhopal.notegem.domain.repository.BookmarkRepository
import javax.inject.Inject

class GetBookmarkUseCase @Inject constructor(
    private val bookmarkRepository: BookmarkRepository
) {
    suspend operator fun invoke(id: Int) = bookmarkRepository.getBookmark(id)
}