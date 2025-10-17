package com.fastcampus.fc_board.controller.dto

import com.fastcampus.fc_board.service.dto.PostSummaryResponseDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl

data class PostSummaryResponse(
    val id: Long,
    val title: String,
    val createdBy: String,
    val createdAt: String,
    val comments: List<CommentResponse>,
    val tags: List<String> = emptyList(),
    val tag: String? = null,
)

fun Page<PostSummaryResponseDto>.toResponse() = PageImpl(
    content.map { it.toResponse() },
    pageable,
    totalElements
)

fun PostSummaryResponseDto.toResponse() = PostSummaryResponse(
    id = id,
    title = title,
    createdBy = createdBy,
    createdAt = createdAt,
    comments = comments.map { it.toResponse() },
    tags = tags,
    tag = firstTag
)
