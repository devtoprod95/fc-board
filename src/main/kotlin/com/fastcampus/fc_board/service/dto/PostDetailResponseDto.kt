package com.fastcampus.fc_board.service.dto

import com.fastcampus.fc_board.domain.Post

data class PostDetailResponseDto(
    val id: Long,
    val title: String,
    val content: String,
    val createdBy: String,
    val createdAt: String,
    val comments: List<CommentResponseDto>,
    val tags: List<String>,
    val likeCount: Long = 0,
)

fun Post.toDetailResponseDto(likeCount: (Long) -> Long) = PostDetailResponseDto(
    id = id,
    title = title,
    content = content,
    createdBy = createdBy,
    createdAt = createdAt.toString(),
    comments = comments.map { it.toResponseDto() },
    tags = tags.map { it.name },
    likeCount = likeCount(id)
)
