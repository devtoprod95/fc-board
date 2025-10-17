package com.fastcampus.fc_board.service.dto

import com.fastcampus.fc_board.domain.Post
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl

data class PostSummaryResponseDto(
    val id: Long,
    val title: String,
    val createdBy: String,
    val createdAt: String,
    val comments: List<CommentResponseDto>,
)

fun Page<Post>.toSummaryResponseDto() = PageImpl(
    content.map { it.toSummaryResponseDto() },
    pageable,
    totalElements
)

fun Post.toSummaryResponseDto() = PostSummaryResponseDto(
    id = id,
    title = title,
    createdBy = createdBy,
    createdAt = createdAt.toString(),
    comments = comments.map { it.toResponseDto() }
)
