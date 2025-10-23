package com.fastcampus.fc_board.service.dto

import com.fastcampus.fc_board.domain.Tag
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl

data class TagResponseDto(
    val id: Long,
    val name: String,
    val createdBy: String,
    val createdAt: String,
)

fun Tag.toResponseDto() = TagResponseDto(
    id = id,
    name = name,
    createdBy = createdBy,
    createdAt = createdAt.toString()
)

fun Tag.toSummaryResponseDto(likeCount: (Long) -> Long) = PostSummaryResponseDto(
    id = post.id,
    title = post.title,
    createdBy = post.createdBy,
    createdAt = post.createdAt.toString(),
    tags = post.tags.map { it.name },
    firstTag = name,
    comments = post.comments.map { it.toResponseDto() },
    likeCount = likeCount(post.id)
)

fun Page<Tag>.toSummaryResponseDto(likeCount: (Long) -> Long) = PageImpl(
    content.map { it.toSummaryResponseDto(likeCount) },
    pageable,
    totalElements
)
